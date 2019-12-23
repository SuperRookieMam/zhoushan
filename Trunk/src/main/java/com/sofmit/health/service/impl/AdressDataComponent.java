package com.sofmit.health.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.sofmit.health.entity.*;
import com.sofmit.health.repository.CommunityHospitalRepository;
import com.sofmit.health.repository.DrugRepository;
import com.sofmit.health.repository.DrugstoreRepository;
import com.sofmit.health.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

/**
 * Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 * @Author: 温东山
 * @Description: 类作用描述
 * @CreateDate: 2019/12/17 0017$ 09:14$
 * @UpdateUser: moon
 * @UpdateDate: 2019/12/17 0017$ 09:14$
 * @history: 修改记录
 * @Version: 1.0
 */
@Component
public class AdressDataComponent {

    private static final String URL="https://restapi.amap.com/v3/geocode/geo?key=af5b938060b249c264d8bfc684337a50&city=%s&address=%s";

    @Autowired
    private CommunityHospitalRepository communityHospitalRepository;

    @Autowired
    private DrugstoreRepository drugstoreRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DrugRepository drugRepository;

    private QCommunityHospital qCommunityHospital = QCommunityHospital.communityHospital;
    private QDrugstore qDrugstore = QDrugstore.drugstore;
    private QHospital qHospital = QHospital.hospital;
    private QDrug qDrug = QDrug.drug;
    private static RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "0 30 3 1,10,20 * ?")
    public void communityHospitalCoordinate() {
        getCommunityHospitalCoordinate();
    }

    @Scheduled(cron = "0 30 4 1,10,20 * ?")
    public void drugstoreCoordinate() {
        getDrugstoreCoordinate();
    }

    @Scheduled(cron = "0 30 5 1,10,20 * ?")
    public void hospitalCoordinate() {
        getHospitalCoordinate();
    }
    /**
     * 抓取数据坐标,获取数据然后解析
     */
    public void getCommunityHospitalCoordinate(){
        // 社保中心
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qCommunityHospital.latitude.isNull().or(qCommunityHospital.longitude.isNull()));
        Iterable<CommunityHospital> iterable =communityHospitalRepository.findAll(booleanBuilder);
        for (CommunityHospital policeStation:iterable){
            String fanwei ="舟山市";
            String adress = policeStation.getAddress();
            if (ObjectUtils.isEmpty(policeStation.getAddress())) {
                fanwei="全国";
                adress = policeStation.getName();
            }
            String getUrl= String.format(URL,fanwei,adress);
            Map<String, Object> forObject = restTemplate.getForObject(getUrl, Map.class);
            ArrayList<Map> arrayList =(ArrayList<Map>)forObject.get("geocodes");
            if (arrayList.isEmpty()) {
                continue;
            }else{
                Map<String,String> data = (Map<String,String>) arrayList.get(0);
                String[] location = data.get("location").split(",");
                policeStation.setLongitude(Double.valueOf(location[0]));
                policeStation.setLatitude(Double.valueOf(location[1]));
                communityHospitalRepository.save(policeStation);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }

    /**
     * 药店
     */
    public void getDrugstoreCoordinate(){
        // 药店
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qDrugstore.latitude.isNull().or(qDrugstore.longitude.isNull()));
        Iterable<Drugstore> iterable =drugstoreRepository.findAll(booleanBuilder);
        for (Drugstore policeStation:iterable){
            String fanwei ="舟山市";
            String adress = policeStation.getAddress();
            if (ObjectUtils.isEmpty(policeStation.getAddress())) {
                fanwei="全国";
                adress = policeStation.getName();
            }
            String getUrl= String.format(URL,fanwei,adress);
            Map<String, Object> forObject = restTemplate.getForObject(getUrl, Map.class);
            ArrayList<Map> arrayList =(ArrayList<Map>)forObject.get("geocodes");
            if (arrayList.isEmpty()) {
                continue;
            }else{
                Map<String,String> data = (Map<String,String>) arrayList.get(0);
                String[] location = data.get("location").split(",");
                policeStation.setLongitude(Double.valueOf(location[0]));
                policeStation.setLatitude(Double.valueOf(location[1]));
                drugstoreRepository.save(policeStation);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }


    public void getHospitalCoordinate(){
        // 药店
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qHospital.latitude.isNull().or(qHospital.longitude.isNull()));
        Iterable<Hospital> iterable =hospitalRepository.findAll(booleanBuilder);
        for (Hospital policeStation:iterable){
            String fanwei ="舟山市";
            String adress = policeStation.getAddress();
            if (ObjectUtils.isEmpty(policeStation.getAddress())) {
                fanwei="全国";
                adress = policeStation.getName();
            }
            String getUrl= String.format(URL,fanwei,adress);
            Map<String, Object> forObject = restTemplate.getForObject(getUrl, Map.class);
            ArrayList<Map> arrayList =(ArrayList<Map>)forObject.get("geocodes");
            if (arrayList.isEmpty()) {
                continue;
            }else{
                Map<String,String> data = (Map<String,String>) arrayList.get(0);
                String[] location = data.get("location").split(",");
                policeStation.setLongitude(Double.valueOf(location[0]));
                policeStation.setLatitude(Double.valueOf(location[1]));
                hospitalRepository.save(policeStation);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }


}
