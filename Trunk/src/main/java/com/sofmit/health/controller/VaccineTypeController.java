package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.VaccineTypeConverter;
import com.sofmit.health.dto.VaccineTypeDto;
import com.sofmit.health.entity.VaccineType;
import com.sofmit.health.service.VaccineTypeService;
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
@RequestMapping("vaccineTypes")
@Api(tags = { "育苗类型" })
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private VaccineTypeConverter vaccineTypeConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public VaccineTypeDto save(@RequestBody VaccineTypeDto dto) {
        return vaccineTypeConverter.toDto(vaccineTypeService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public VaccineTypeDto update(@PathVariable("id") Long id, @RequestBody VaccineTypeDto dto) {
        return vaccineTypeConverter.toDto(vaccineTypeService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        vaccineTypeService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public VaccineTypeDto findById(@PathVariable("id") Long id) {
        return vaccineTypeConverter.toDto(vaccineTypeService.findById(id)).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<VaccineTypeDto> list(@RequestParam Map<String, String> map) {
        return vaccineTypeConverter.toDto(vaccineTypeService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<VaccineTypeDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<VaccineType> result = vaccineTypeService.list(map, pageable);
        return result.map(vaccineTypeConverter::toDto).map(Optional::get);
    }
}
