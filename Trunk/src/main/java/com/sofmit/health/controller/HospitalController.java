package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.CommunityHospitalConverter;
import com.sofmit.health.converter.DrugstoreConverter;
import com.sofmit.health.converter.HospitalConverter;
import com.sofmit.health.dto.HospitalDto;
import com.sofmit.health.entity.Hospital;
import com.sofmit.health.service.CommunityHospitalService;
import com.sofmit.health.service.DrugstoreService;
import com.sofmit.health.service.HospitalService;
import com.sofmit.health.service.impl.AdressDataComponent;
import com.sofmit.health.service.impl.EmergencyDataComponent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * 医院信息管理
 */
@RestController
@RequestMapping("hospitals")
@Api(tags = { "医院信息管理" })
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalConverter hospitalConverter;

    @Autowired
    private DrugstoreService drugstoreService;

    @Autowired
    private DrugstoreConverter drugstoreConverter;

    @Autowired
    private CommunityHospitalService communityHospitalService;

    @Autowired
    private CommunityHospitalConverter communityHospitalConverter;

    @Autowired
    private EmergencyDataComponent emergencyDataComponent;
    @Autowired
    private AdressDataComponent adressDataComponent;
    /**
     * 新增医院信息
     *
     * @param dto HospitalDto
     */
    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增医院信息", notes = "save")
    public HospitalDto save(@RequestBody HospitalDto dto) {
        return hospitalConverter.toDto(hospitalService.save(dto)).get();
    }

    /**
     * 跟新医院信息
     */
    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "根据实体修改", notes = "update")
    public HospitalDto update(@PathVariable("id") Long id, @RequestBody HospitalDto dto) {
        return hospitalConverter.toDto(hospitalService.update(id, dto)).get();
    }

    /***
     * 根据ID删除医院信息
     */
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "根据Id删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        hospitalService.delete(id);
    }

    /**
     * 根据id查找医院信息
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据Id获取参数", notes = "findById")
    public HospitalDto findById(@PathVariable("id") Long id) {
        return hospitalConverter.toDto(hospitalService.findById(id)).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<HospitalDto> list(@RequestParam Map<String, String> map) {
        return hospitalConverter.toDto(hospitalService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<HospitalDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<Hospital> result = hospitalService.list(map, pageable);
        return result.map(hospitalConverter::toList);
    }

    @GetMapping("/hospitalAndDrugStore")
    @ApiOperation(value = "查询所有医院,药店", notes = "hospitalAndDrugStore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "医院名称"),
            @ApiImplicitParam(name = "type", value = "类型"), })
    public Map<String, Object> hospitalAndDrugStore(
            @RequestParam(name = "name", value = "", required = false) String name,
            @RequestParam(name = "type", value = "", required = false) String type) {
        Map<String, Object> returnMap = new HashMap<>();
        // 初始化数据对象
        returnMap.put("level2HospitalCount", hospitalService.count("二级甲等"));
        returnMap.put("level3HospitalCount", hospitalService.count("三级甲等"));
        returnMap.put("MCHHospitalCount", hospitalService.count("妇幼保健院"));
        returnMap.put("communityHospitalCount", communityHospitalService.count());
        returnMap.put("drugstoreCount", drugstoreService.count());
        returnMap.put("level2Hospital", Collections.emptyList());
        returnMap.put("level3Hospital", Collections.emptyList());
        returnMap.put("MCHHospital", Collections.emptyList());
        returnMap.put("communityHospital", Collections.emptyList());
        returnMap.put("drugstore", Collections.emptyList());
        Map<String, String> map = new HashMap<>();
        if (!StringUtils.isEmpty(name)) {
            map.put("name", name);
        }
        if (StringUtils.isEmpty(type)) {
            returnMap.put("drugstore", drugstoreConverter.toList(drugstoreService.list(map)));
            returnMap.put("communityHospital", communityHospitalConverter.toList(communityHospitalService.list(map)));
            map.put("level", "二级甲等");
            List<HospitalDto> list2 = new ArrayList<>();
            list2.addAll(hospitalConverter.toList(hospitalService.list(map)));
            map.put("level", "2");
            list2.addAll(hospitalConverter.toList(hospitalService.list(map)));
            returnMap.put("level2Hospital",list2 );
            map.put("level", "三级甲等");
            List<HospitalDto> list3= new ArrayList<>();
            list3.addAll(hospitalConverter.toList(hospitalService.list(map)));
            map.put("level", "3");
            list3.addAll(hospitalConverter.toList(hospitalService.list(map)));
            returnMap.put("level3Hospital", list3);
            map.put("level", "妇幼保健院");
            returnMap.put("MCHHospital", hospitalConverter.toList(hospitalService.list(map)));
        } else if (type.equals("level2Hospital")) {
            map.put("level", "二级甲等");
            List<HospitalDto> list2 = new ArrayList<>();
            list2.addAll(hospitalConverter.toList(hospitalService.list(map)));
            map.put("level", "2");
            list2.addAll(hospitalConverter.toList(hospitalService.list(map)));
            returnMap.put("level2Hospital", list2);
        } else if (type.equals("level3Hospital")) {
            map.put("level", "三级甲等");
            List<HospitalDto> list3= new ArrayList<>();
            list3.addAll(hospitalConverter.toList(hospitalService.list(map)));
            map.put("level", "3");
            list3.addAll(hospitalConverter.toList(hospitalService.list(map)));
            returnMap.put("level3Hospital", list3);
        } else if (type.equals("MCHHospital")) {
            map.put("level", "妇幼保健院");
            returnMap.put("MCHHospital", hospitalConverter.toList(hospitalService.list(map)));
        } else if (type.equals("communityHospital")) {
            returnMap.put("communityHospital", communityHospitalConverter.toList(communityHospitalService.list(map)));
        } else if (type.equals("drugstore")) {
            returnMap.put("drugstore", drugstoreConverter.toList(drugstoreService.list(map)));
        }
        return returnMap;
    }

    @GetMapping("{timer}/{pass}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增医院信息", notes = "save")
    public Object saveTimer(@PathVariable("timer") String timer, @PathVariable("pass") String pass) {
        if (!pass.equals("666")) {
            return null;
        }
        if ("drug".equals(timer)) {
            emergencyDataComponent.getDrug();
        } else if ("hospital".equals(timer)) {
            emergencyDataComponent.getHospital();
        } else if ("drugStore".equals(timer)) {
            emergencyDataComponent.getDrugstore();
        } else if ("communityHospital".equals(timer)) {
            emergencyDataComponent.getCommunityHospital();
        } else if ("all".equals(timer)) {
            emergencyDataComponent.getDrug();
            emergencyDataComponent.getHospital();
            emergencyDataComponent.getDrugstore();
            emergencyDataComponent.getCommunityHospital();
        }else if ("llc".equals(timer)) {
            adressDataComponent.getCommunityHospitalCoordinate();
        }else if ("lld".equals(timer)) {
            adressDataComponent.getDrugstoreCoordinate();
        }else if ("llh".equals(timer)) {
            adressDataComponent.getHospitalCoordinate();
        }
        return null;
    }
}
