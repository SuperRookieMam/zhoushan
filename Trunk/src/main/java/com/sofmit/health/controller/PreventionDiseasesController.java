package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.PreventionDiseasesConverter;
import com.sofmit.health.dto.PreventionDiseasesDto;
import com.sofmit.health.entity.PreventionDiseases;
import com.sofmit.health.service.PreventionDiseasesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("preventionDiseases")
@Api(tags = { "疾病预防" })
public class PreventionDiseasesController {

    @Autowired
    private PreventionDiseasesService preventionDiseasesService;

    @Autowired
    private PreventionDiseasesConverter preventionDiseasesConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public PreventionDiseasesDto save(@RequestBody PreventionDiseasesDto dto) {
        return preventionDiseasesConverter.toDto(preventionDiseasesService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public PreventionDiseasesDto update(@PathVariable("id") Long id, @RequestBody PreventionDiseasesDto dto) {
        return preventionDiseasesConverter.toDto(preventionDiseasesService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        preventionDiseasesService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public PreventionDiseasesDto findById(@PathVariable("id") Long id) {
        return preventionDiseasesConverter.toDto(preventionDiseasesService.findById(id))
                .orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<PreventionDiseasesDto> list(@RequestParam Map<String, String> map) {
        return preventionDiseasesConverter.toDto(preventionDiseasesService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<PreventionDiseasesDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<PreventionDiseases> result = preventionDiseasesService.list(map, pageable);
        return result.map(preventionDiseasesConverter::toDto).map(Optional::get);
    }
}
