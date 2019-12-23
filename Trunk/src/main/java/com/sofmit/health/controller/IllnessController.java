package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.IllnessConverter;
import com.sofmit.health.dto.IllnessDto;
import com.sofmit.health.entity.Illness;
import com.sofmit.health.service.IllnessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("illnesses")
@Api(tags = { "疾病管理" })
public class IllnessController {

    @Autowired
    private IllnessService illnessService;

    @Autowired
    private IllnessConverter illnessConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public IllnessDto save(@RequestBody IllnessDto dto) {
        return illnessConverter.toDto(illnessService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public IllnessDto update(@PathVariable("id") Long id, @RequestBody IllnessDto dto) {
        return illnessConverter.toDto(illnessService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id) {
        illnessService.delete(id);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public IllnessDto findById(@PathVariable("id") Long id) {
        return illnessConverter.toDto(illnessService.findById(id)).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<IllnessDto> list(@RequestParam Map<String, String> map) {
        return illnessConverter.toDto(illnessService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<IllnessDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<Illness> result = illnessService.list(map, pageable);
        return result.map(illnessConverter::toDto).map(Optional::get);
    }

    @GetMapping(params = { "lab" })
    @ApiOperation(value = "获取疾病的身体部位", notes = "list")
    public List<Map<String, String>> map() {
        return illnessService.map();
    }

    @GetMapping(params = { "keyword" })
    @ApiOperation(value = "根据关键字获取症状", notes = "list")
    public RangePage<IllnessDto> map(
            @RequestParam(name = "maxId", required = false) Long maxId,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault Pageable pageable) {

        RangePage<Illness> result = illnessService.list(maxId, keyword, pageable);
        return result.map(illnessConverter::toDto).map(Optional::get);
    }

}
