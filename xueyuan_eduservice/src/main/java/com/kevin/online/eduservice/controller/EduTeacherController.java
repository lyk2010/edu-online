package com.kevin.online.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.online.common.R;
import com.kevin.online.eduservice.entity.EduTeacher;
import com.kevin.online.eduservice.query.QueryTeacher;
import com.kevin.online.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-04-29
 */
@Api(description = "讲师接口api")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    @GetMapping("/getTeacherById/{id}")
    @ApiOperation(value = "根据ID查询讲师", notes = "根据ID查询讲师,参数为id")
    public R getTeacherInfoById(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("item", eduTeacher);
    }


    @PutMapping("/updateTeacher")
    @ApiOperation(value = "更新讲师", notes = "更新指定讲师")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师", notes = "添加讲师,参数为eduTeacher对象")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.save(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    @PostMapping("/moreCondtionPageList/{page}/{limit}")
    @ApiOperation(value = "分页条件获取指定讲师列表", notes = "分页条件获取指定讲师列表,可不传参数")
    public R getMoreCondtionPageList(@PathVariable("page") Long page,
                                     @PathVariable("limit") Long limit,
                                     @RequestBody(required = false) QueryTeacher queryTeacher) {

        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        eduTeacherService.pageListCondition(pageTeacher, queryTeacher);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("items", records);

    }

    @GetMapping("/pageList/{page}/{limit}")
    @ApiOperation(value = "分页获取所有讲师列表", notes = "分页获取所有讲师列表,可不传参数")
    public R getPageInfoAllTeacher(@PathVariable("page") Long page,
                                   @PathVariable("limit") Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("items", records);
    }

    /**
     * 查询所有讲师
     *
     * @return
     */
    @GetMapping("/allTeacher")
    @ApiOperation(value = "获取所有讲师列表", notes = "获取所有讲师列表,无参数")
    public R getAllTeachers() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }


    /**
     * 根据主键ID逻辑删除
     *
     * @param id
     */
    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "根据主键ID逻辑删除对应讲师", notes = "根据主键ID逻辑删除对应讲师,ID为讲师ID")
    public R deleteById(@PathVariable("id") String id) {
        Boolean flag = eduTeacherService.deleteTeacherById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

