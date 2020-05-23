package com.kevin.online.eduservice.service;

import com.kevin.online.eduservice.dto.CourseInfoDto;
import com.kevin.online.eduservice.dto.CourseInfoFormDto;
import com.kevin.online.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程
     * @param courseInfoFormDto
     * @return
     */
    String addCourse(CourseInfoFormDto courseInfoFormDto);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CourseInfoFormDto getCourseInfoById(String id);

    /**
     * 更新课程信息
     * @param courseInfoFormDto
     * @return
     */
    Boolean updateCourseInfo(CourseInfoFormDto courseInfoFormDto);

    /**
     * 根据id删除课程
     * @param id
     * @return
     */
    Boolean removeCourseId(String id);

    /**
     * 根据课程id查询课程相关的详细信息
     * @param courseId
     * @return
     */
    CourseInfoDto getCourseAllInfo(String courseId);

    /**
     * 根据课程id修改课程发布状态
     * @param courseId
     * @return
     */
    Boolean updatePublishCourse(String courseId);
}
