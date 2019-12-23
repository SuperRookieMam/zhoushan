package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.CommunityHospitalConverter;
import com.sofmit.health.dto.CommunityHospitalDto;
import com.sofmit.health.entity.CommunityHospital;
import com.sofmit.health.service.CommunityHospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * 社区医院
 *
 * @author 李东
 * @since
 * @Copyright (C) 2019 江苏云智网络科技股份有限公司版权所有
 *
 */
@RestController
@RequestMapping("communityHospitals")
@Api(tags = { "社区医院" })
public class CommunityHospitalController {

    @Autowired
    private CommunityHospitalService communityHospitalService;

    @Autowired
    private CommunityHospitalConverter communityHospitalConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public CommunityHospitalDto save(@RequestBody CommunityHospitalDto dto) {
        return communityHospitalConverter.toDto(communityHospitalService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public CommunityHospitalDto update(@PathVariable("id") Long id, @RequestBody CommunityHospitalDto dto) {
        return communityHospitalConverter.toDto(communityHospitalService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        communityHospitalService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public CommunityHospitalDto findById(@PathVariable("id") Long id) {
        return communityHospitalConverter.toDto(communityHospitalService.findById(id))
                .orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<CommunityHospitalDto> list(
            @RequestParam Map<String, String> map) {
        return communityHospitalConverter.toDto(communityHospitalService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<CommunityHospitalDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<CommunityHospital> result = communityHospitalService.list(map, pageable);
        return result.map(communityHospitalConverter::toList);
    }

    @GetMapping(params = { "two" })
    public Page<Map<String,Object>> searchByVacciName(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        return communityHospitalService.searchCdcAndCommunityHospitalForSql(map,pageable);
    }
}
