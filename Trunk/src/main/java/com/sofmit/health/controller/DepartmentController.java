package com.sofmit.health.controller;

import com.dm.common.dto.RangePage;
import com.dm.common.exception.DataNotExistException;
import com.sofmit.health.converter.DepartmentConverter;
import com.sofmit.health.dto.DepartmentDto;
import com.sofmit.health.entity.Department;
import com.sofmit.health.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController("hDepartmentController")
@RequestMapping("hDepartments")
@Api(tags = { "科室" })
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentConverter departmentConverter;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "新增", notes = "save")
    public DepartmentDto save(@RequestBody DepartmentDto dto) {
        return departmentConverter.toDto(departmentService.save(dto)).get();
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "修改", notes = "update")
    public DepartmentDto update(@PathVariable("id") Long id, @RequestBody DepartmentDto dto) {
        return departmentConverter.toDto(departmentService.update(id, dto)).get();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "删除", notes = "delete")
    public void delete(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        try {
            departmentService.delete(id);
        } catch (DataIntegrityViolationException e) {
            /**
             * 423 Locked 当前资源被锁定。（RFC 4918 WebDAV）
             */
            response.setStatus(423);
            response.getWriter().write("");
            response.getWriter().close();
        }
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据Id查找", notes = "findById")
    public DepartmentDto findById(@PathVariable("id") Long id) {
        return departmentConverter.toDto(departmentService.findById(id)).orElseThrow(DataNotExistException::new);
    }

    @GetMapping(params = { "list" })
    public Iterable<DepartmentDto> list(@RequestParam Map<String, String> map) {
        return departmentConverter.toDto(departmentService.list(map));
    }

    @GetMapping
    @ApiOperation(value = "根据条件分页查询", notes = "list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "参数实例", value = "\"address\": \"成都\", // 为字符串是模糊查询\n" +
                    "\"latitude\": 0  // 参数是数字或者时间时,一个参数时精确查询\n" +
                    "\"latitude\": 104.14,200.145  // 当参数是数字或者时间字符时,以','相隔的字符串时可以范围查询\n" +
                    "\"latitude\": \"\",200.145  // 当参数是数字或者时间字符时,如此查询<\n" +
                    "\"latitude\": 104.14,\"\"  // 当参数是数字或者时间字符时,如此查询>=\n") })
    public RangePage<DepartmentDto> list(
            @RequestParam Map<String, String> map,
            @PageableDefault Pageable pageable) {
        RangePage<Department> result = departmentService.list(map, pageable);
        return result.map(departmentConverter::toDto).map(Optional::get);
    }
}
