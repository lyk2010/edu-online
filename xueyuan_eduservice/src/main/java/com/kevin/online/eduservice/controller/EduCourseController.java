package com.kevin.online.eduservice.controller;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kevin.online.common.R;
import com.kevin.online.eduservice.dto.CourseInfoDto;
import com.kevin.online.eduservice.dto.CourseInfoFormDto;
import com.kevin.online.eduservice.entity.EduCourse;
import com.kevin.online.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
@Api(description = "课程接口api")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "根据课程id修改课程发布状态",notes = "根据课程id修改课程发布状态")
    @PutMapping("/publishCourse/{courseId}")
    public R updatePublishCourse(@PathVariable("courseId") String courseId) {
        Boolean flag = eduCourseService.updatePublishCourse(courseId);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }




    @ApiOperation(value = "根据课程id查询课程相关的详细信息",notes = "根据课程id查询课程相关的详细信息")
    @GetMapping("/getCourseAllInfo/{courseId}")
    public R getCourseAllInfo(@PathVariable("courseId") String courseId) {
        CourseInfoDto courseAllInfo = eduCourseService.getCourseAllInfo(courseId);
        return R.ok().data("courseAllInfo",courseAllInfo);
    }


    @ApiOperation(value = "根据id删除课程",notes = "根据id删除课程")
    @DeleteMapping("/deleteCourse/{id}")
    public R deleteCourseById(@PathVariable("id") String id) {
        Boolean flag = eduCourseService.removeCourseId(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }

    }


    @ApiOperation(value = "获取课程列表",notes = "获取课程列表")
    @GetMapping("/courseList")
    public R getCourseList() {
        List<EduCourse> courseList = eduCourseService.list(null);
        return R.ok().data("items",courseList);
    }


    @ApiOperation(value = "添加课程", notes = "添加课程")
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoFormDto courseInfoFormDto) {
        String courseId = eduCourseService.addCourse(courseInfoFormDto);
        return R.ok().data("courseId", courseId);
    }


    @ApiOperation(value = "根据课程id查询课程信息", notes = "根据课程id查询课程信息")
    @GetMapping("/getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable("id") String id) {
        CourseInfoFormDto courseInfoFormDto = eduCourseService.getCourseInfoById(id);
        if (courseInfoFormDto != null) {
            return R.ok().data("courseInfoFormDto", courseInfoFormDto);
        } else {
            return R.error();
        }
    }


    @ApiOperation(value = "根据课程id查询课程信息", notes = "根据课程id查询课程信息")
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoFormDto courseInfoFormDto) {
        Boolean flag = eduCourseService.updateCourseInfo(courseInfoFormDto);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

