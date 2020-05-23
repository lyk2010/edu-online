package com.kevin.online.eduservice.mapper;

import com.kevin.online.eduservice.dto.CourseInfoDto;
import com.kevin.online.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程id查询课程相关的详细信息
     * @param courseId
     * @return
     */
    CourseInfoDto getCourseAllInfo(String courseId);


    /**
     * 根据课程id修改课程发布状态
     * @param id
     * @return
     */
    Integer updateCourseStatusById(String id);
}
