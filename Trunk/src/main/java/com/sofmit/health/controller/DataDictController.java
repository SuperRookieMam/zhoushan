package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.DataDictConverter;
import com.sofmit.health.dto.DataDictDto;
import com.sofmit.health.entity.DataDict;
import com.sofmit.health.service.DataDictService;
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
@RequestMapping("dataDicts")
@Api(tags = { "字典" })
public class DataDictController {

    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private DataDictConverter dataDictConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public DataDictDto save(@RequestBody DataDictDto dto) {
        return dataDictConverter.toDto(dataDictService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public DataDictDto update(@PathVariable("id") Long id, @RequestBody DataDictDto dto) {
        return dataDictConverter.toDto(dataDictService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        dataDictService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public DataDictDto findById(@PathVariable("id") Long id) {
        return dataDictConverter.toDto(dataDictService.findById(id)).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<DataDictDto> list(
            @RequestParam Map<String, String> map) {
        return dataDictConverter.toDto(dataDictService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<DataDictDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<DataDict> result = dataDictService.list(map, pageable);
        return result.map(dataDictConverter::toDto).map(Optional::get);
    }
}
