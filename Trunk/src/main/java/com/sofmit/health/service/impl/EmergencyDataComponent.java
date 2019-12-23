package com.sofmit.health.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.entity.*;
import com.sofmit.health.repository.CommunityHospitalRepository;
import com.sofmit.health.repository.DrugRepository;
import com.sofmit.health.repository.DrugstoreRepository;
import com.sofmit.health.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 * @Author: 温东山
 * @Description: 类作用描述
 * @CreateDate: 2019/12/9 0009$ 09:28$
 * @UpdateUser: moon
 * @UpdateDate: 2019/12/9 0009$ 09:28$
 * @history: 修改记录
 * @Version: 1.0
 */
@EnableScheduling
@Component
public class EmergencyDataComponent {

    @Autowired
    private CommunityHospitalRepository communityHospitalRepository;

    @Autowired
    private DrugstoreRepository drugstoreRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    private QCommunityHospital qCommunityHospital = QCommunityHospital.communityHospital;
    private QDrugstore qDrugstore = QDrugstore.drugstore;
    private QHospital qHospital = QHospital.hospital;
    private QDrug qDrug = QDrug.drug;
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取token的url
     */
    @Value("${token_url}")
    private String tokenUrl;

    /**
     * 获取接口数据的url
     */
    @Value("${data_url}")
    private String dataUrl;

    private Map getAppSign() {
        HttpHeaders requestHeaders = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.setAccept(mediaTypes);
        Map forObject = restTemplate.getForObject(tokenUrl, Map.class);
        Map params = (Map) forObject.get("params");
        return params;
    }

    @Scheduled(cron = "0 0 1 1,10,20 * ?")
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void doGetDrug() {
        getDrug();
    }

    @Scheduled(cron = "0 0 2 1,10,20 * ?")
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void doGetDrugtore() {
        getDrugstore();
    }

    // @Scheduled(cron = "0 0 3 1,10,20 * ?")
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void doGetCommunityHospital() {
        getCommunityHospital();
    }

    @Scheduled(cron = "0 0 4 1,10,20 * ?")
    // @Scheduled(cron = "0 0/1 * * * ?")
    public void doGetgetHospital() {
        getHospital();
    }

    /**
     * 医保中心
     */
    public void getCommunityHospital() {
        Map params = getAppSign();
        String sign = params.get("sign").toString();
        Long accessTime = Long.parseLong(params.get("accessTime").toString());
        String baseUrl = dataUrl + "?appId=5&baseDataId=8582" + "&appSign=" + sign + "&accessTime=" + accessTime
                + "&pageSize=200&pageNumber=";
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String url = baseUrl + i;
            Map forObject = restTemplate.getForObject(url, Map.class);
            if (forObject.get("resultData").toString().equals("[]")) {
                return;
            }
            String resultData = forObject.get("resultData").toString();
            ArrayList arrayList = JSON.parseObject(resultData, ArrayList.class);
            if (arrayList.isEmpty()) {
                return;
            }
            /**
             * 去掉第一条数据,第一条是表头
             */
            CommunityHospital communityHospital = null;
            JSONArray head = (JSONArray) arrayList.get(0);
            int inIdIndex = head.indexOf("序号");
            int nameIndex = head.indexOf("医保中心单位名称");
            int addressIndex = head.indexOf("联系地址");
            int telIndex = head.indexOf("联系电话");
            for (int j = 1; j < arrayList.size(); j++) {
                JSONArray jsonArray = (JSONArray) arrayList.get(j);
                communityHospital = jpaQueryFactory.select(qCommunityHospital).from(qCommunityHospital)
                        .where(qCommunityHospital.inId.eq(jsonArray.getString(inIdIndex))).fetchFirst();
                if (ObjectUtils.isEmpty(communityHospital)) {
                    communityHospital = new CommunityHospital();
                }
                try {
                    communityHospital.setInId(jsonArray.getString(inIdIndex));
                    communityHospital.setName(jsonArray.getString(nameIndex));
                    communityHospital.setAddress(jsonArray.getString(addressIndex));
                    communityHospital.setTel(jsonArray.getString(telIndex));
                    if (ObjectUtils.isEmpty(communityHospital.getState())) {
                        communityHospital.setState("未发布");
                    }
                    communityHospitalRepository.saveAndFlush(communityHospital);
                }catch (Exception e) {

                }
            }
        }
    }

    /**
     * 药店
     */
    public void getDrugstore() {
        Map params = getAppSign();
        String sign = params.get("sign").toString();
        Long accessTime = Long.parseLong(params.get("accessTime").toString());
        String baseUrl = dataUrl + "?appId=5&baseDataId=8580" + "&appSign=" + sign + "&accessTime=" + accessTime
                + "&pageSize=200&pageNumber=";
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String url = baseUrl + i;
            Map forObject = restTemplate.getForObject(url, Map.class);
            if (forObject.get("resultData").toString().equals("[]")) {
                return;
            }
            String resultData = forObject.get("resultData").toString();
            ArrayList arrayList = JSON.parseObject(resultData, ArrayList.class);
            if (arrayList.isEmpty()) {
                return;
            }
            /**
             * 去掉第一条数据,第一条是表头
             */
            Drugstore drugstore = null;
            JSONArray head = (JSONArray) arrayList.get(0);
            int inIdIndex = head.indexOf("序号");
            int nameIndex = head.indexOf("药店名称");
            int addressIndex = head.indexOf("药店地址");
            int telIndex = head.indexOf("联系电话");
            for (int j = 1; j < arrayList.size(); j++) {
                JSONArray jsonArray = (JSONArray) arrayList.get(j);
                drugstore = jpaQueryFactory.select(qDrugstore).from(qDrugstore)
                        .where(qDrugstore.inId.eq(jsonArray.getString(inIdIndex))).fetchFirst();

                if (ObjectUtils.isEmpty(drugstore)) {
                    drugstore = new Drugstore();
                }
                try {
                    drugstore.setName(jsonArray.getString(nameIndex));
                    drugstore.setInId(jsonArray.getString(inIdIndex));
                    drugstore.setTel(jsonArray.getString(telIndex));
                    drugstore.setAddress(jsonArray.getString(addressIndex));
                    if (ObjectUtils.isEmpty(drugstore.getState())) {
                        drugstore.setState("未发布");
                    }
                    drugstoreRepository.saveAndFlush(drugstore);
                }catch (Exception e) {

                }
            }
        }
    }

    public void getHospital() {
        Map params = getAppSign();
        String sign = params.get("sign").toString();
        Long accessTime = Long.parseLong(params.get("accessTime").toString());
        String baseUrl = dataUrl + "?appId=5&baseDataId=8576" + "&appSign=" + sign + "&accessTime=" + accessTime
                + "&pageSize=200&pageNumber=";
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String url = baseUrl + i;
            Map forObject = restTemplate.getForObject(url, Map.class);
            if (forObject.get("resultData").toString().equals("[]")) {
                return;
            }
            String resultData = forObject.get("resultData").toString();
            ArrayList arrayList = JSON.parseObject(resultData, ArrayList.class);
            if (arrayList.isEmpty()) {
                return;
            }
            /**
             * 去掉第一条数据,第一条是表头
             */
            JSONArray head = (JSONArray) arrayList.get(0);
            int inIdIndex = head.indexOf("序号");
            int nameIndex = head.indexOf("医院名称");
            int addressIndex = head.indexOf("医院地址");
            int telIndex = head.indexOf("联系电话");
            int levelIndex = head.indexOf("医院等级");
            Hospital hospital = null;
            for (int j = 1; j < arrayList.size(); j++) {
                JSONArray jsonArray = (JSONArray) arrayList.get(j);
                hospital = jpaQueryFactory.select(qHospital).from(qHospital)
                        .where(qHospital.inId.eq(jsonArray.getString(inIdIndex))).fetchFirst();
                if (ObjectUtils.isEmpty(hospital)) {
                    hospital = new Hospital();
                }
                try {
                    hospital.setInId(jsonArray.getString(inIdIndex));
                    hospital.setName(jsonArray.getString(nameIndex));
                    hospital.setAddress(jsonArray.getString(addressIndex));
                    hospital.setMedicalInsuranceTel(jsonArray.getString(telIndex));
                    hospital.setLevel(jsonArray.getString(levelIndex));
                    if (ObjectUtils.isEmpty(hospital.getState())) {
                        hospital.setState("未发布");
                    }
                    hospitalRepository.saveAndFlush(hospital);
                }catch (Exception e) {

                }
            }
        }
    }

    /**
     * 药品
     */
    public void getDrug() {
        Map params = getAppSign();
        String sign = params.get("sign").toString();
        Long accessTime = Long.parseLong(params.get("accessTime").toString());
        /**
         * 舟山市医保药品目录
         */
        String baseUrl = dataUrl + "?appId=5&baseDataId=8560" + "&appSign=" + sign + "&accessTime=" + accessTime
                + "&pageSize=200&pageNumber=";
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String url = baseUrl + i;
            Map forObject = restTemplate.getForObject(url, Map.class);
            if (forObject.get("resultData").toString().equals("[]")) {
                return;
            }
            String resultData = forObject.get("resultData").toString();
            ArrayList arrayList = JSON.parseObject(resultData, ArrayList.class);
            if (arrayList.isEmpty()) {
                return;
            }
            /**
             * 去掉第一条数据,第一条是表头
             */
            Drug drug = null;
            JSONArray head = (JSONArray) arrayList.get(0);
            int nameIndex = head.indexOf("药品名称");
            int guiGeIndex = head.indexOf("规格");
            int dosageFormIndex = head.indexOf("剂型");
            int inJobSelfConceitIndex = head.indexOf("在职自负支付（比例）");
            int retireSelfConceitIndex = head.indexOf("退休自负支付（比例）");
            int dimissionSelfConceitIndex = head.indexOf("离休自负支付（比例）");
            int qualifiedToPayIndex = head.indexOf("限定支付");
            for (int j = 1; j < arrayList.size(); j++) {
                JSONArray jsonArray = (JSONArray) arrayList.get(j);
                drug = jpaQueryFactory.select(qDrug).from(qDrug).where(qDrug.name.eq(jsonArray.getString(nameIndex)))
                        .fetchFirst();
                if (ObjectUtils.isEmpty(drug)) {
                    drug = new Drug();
                }
                try {
                    drug.setName(jsonArray.getString(nameIndex));
                    drug.setStandard(jsonArray.getString(guiGeIndex));
                    drug.setDosageForm(jsonArray.getString(dosageFormIndex));
                    drug.setInJobSelfConceit(jsonArray.getDouble(inJobSelfConceitIndex));
                    drug.setRetireSelfConceit(jsonArray.getDouble(retireSelfConceitIndex));
                    drug.setDimissionSelfConceit(jsonArray.getDouble(dimissionSelfConceitIndex));
                    drug.setQualifiedToPay(jsonArray.getString(qualifiedToPayIndex));
                    drug.setSelfConceit("医保用药");
                    if (ObjectUtils.isEmpty(drug.getState())) {
                        drug.setState("未发布");
                    }
                    drugRepository.saveAndFlush(drug);
                }catch (Exception e) {

                }

            }
        }
        /**
         * 舟山市医保诊疗目录
         */
        /*baseUrl = dataUrl + "?appId=5&baseDataId=8556" + "&appSign=" + sign + "&accessTime=" + accessTime
                + "&pageSize=200&pageNumber=";
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String url = baseUrl + i;
            Map forObject = restTemplate.getForObject(url, Map.class);
            if (forObject.get("resultData").toString().equals("[]")) {
                return;
            }
            String resultData = forObject.get("resultData").toString();
            ArrayList arrayList = JSON.parseObject(resultData, ArrayList.class);
            if (arrayList.isEmpty()) {
                return;
            }
            *//**
             * 去掉第一条数据,第一条是表头 舟山市医保药品目录
             *//*
            Drug drug = null;
            JSONArray head = (JSONArray) arrayList.get(0);
            int nameIndex = head.indexOf("药品名称");
            int inJobSelfConceitIndex = head.indexOf("在职自负支付（比例）");
            int retireSelfConceitIndex = head.indexOf("退休自负支付（比例）");
            int dimissionSelfConceitIndex = head.indexOf("离休自负支付（比例）");
            int qualifiedToPayIndex = head.indexOf("限定支付");
            for (int j = 1; j < arrayList.size(); j++) {
                JSONArray jsonArray = (JSONArray) arrayList.get(j);
                drug = jpaQueryFactory.select(qDrug).from(qDrug).where(qDrug.name.eq(jsonArray.getString(nameIndex)))
                        .fetchFirst();
                if (ObjectUtils.isEmpty(drug)) {
                    drug = new Drug();
                }
                drug.setName(jsonArray.getString(nameIndex));
                drug.setInJobSelfConceit(jsonArray.getDouble(inJobSelfConceitIndex));
                drug.setRetireSelfConceit(jsonArray.getDouble(retireSelfConceitIndex));
                drug.setDimissionSelfConceit(jsonArray.getDouble(dimissionSelfConceitIndex));
                drug.setQualifiedToPay(jsonArray.getString(qualifiedToPayIndex));
                drugRepository.saveAndFlush(drug);
            }
        }*/
    }
}
