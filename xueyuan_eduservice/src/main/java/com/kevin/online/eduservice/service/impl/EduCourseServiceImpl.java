package com.kevin.online.eduservice.service.impl;

import com.kevin.online.eduservice.dto.CourseInfoDto;
import com.kevin.online.eduservice.dto.CourseInfoFormDto;
import com.kevin.online.eduservice.entity.EduCourse;
import com.kevin.online.eduservice.entity.EduCourseDescription;
import com.kevin.online.eduservice.handler.EduException;
import com.kevin.online.eduservice.mapper.EduCourseDescriptionMapper;
import com.kevin.online.eduservice.mapper.EduCourseMapper;
import com.kevin.online.eduservice.service.EduChapterService;
import com.kevin.online.eduservice.service.EduCourseDescriptionService;
import com.kevin.online.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.online.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Transactional(rollbackFor = EduException.class)
    @Override
    public String addCourse(CourseInfoFormDto courseInfoFormDto) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoFormDto, eduCourse);
        int result = baseMapper.insert(eduCourse);
        if (result == 0) {
            throw new EduException(20002, "添加课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获取描述信息
        String description = courseInfoFormDto.getDescription();
        eduCourseDescription.setDescription(description);
        //获取课程id
        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);
        boolean save = descriptionService.save(eduCourseDescription);
        if (save) {
            return courseId;
        } else {
            return null;
        }
    }

    /**
     * 根据id查询课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoFormDto getCourseInfoById(String id) {
        //查询两张表
        //根据id查询课程基本信息表
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            //没有该课程信息
            throw new EduException(20001, "没有该课程信息");
        }
        CourseInfoFormDto courseInfoFormDto = new CourseInfoFormDto();
        BeanUtils.copyProperties(eduCourse, courseInfoFormDto);
        //根据id查询课程描述信息表
        EduCourseDescription description = descriptionService.getById(id);
        courseInfoFormDto.setDescription(description.getDescription());
        return courseInfoFormDto;
    }

    /**
     * 更新课程信息
     *
     * @param courseInfoFormDto
     * @return
     */
    @Override
    public Boolean updateCourseInfo(CourseInfoFormDto courseInfoFormDto) {
        //修改课程信息基本表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoFormDto, eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result == 0) {
            throw new EduException(20001, "课程信息修改失败");
        }
        //修改课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoFormDto.getId());
        eduCourseDescription.setDescription(courseInfoFormDto.getDescription());
        return descriptionService.updateById(eduCourseDescription);
    }

    /**
     * 根据课程id删除课程
     * @param id
     * @return
     */
    @Override
    public Boolean removeCourseId(String id) {
        //1.根据课程id删除章节
        chapterService.deleteChapterByCourseId(id);
        //2.根据课程id删除课程小节
        videoService.deleteVideoByCourseId(id);
        //3.根据课程id删除课程描述
        descriptionService.deleteDescriptionByCourseId(id);
        //4.根据课程id删除课程自身
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    /**
     * 根据课程id查询课程相关的详细信息
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoDto getCourseAllInfo(String courseId) {
        return baseMapper.getCourseAllInfo(courseId);
    }

    @Override
    public Boolean updatePublishCourse(String courseId) {
        Integer flag = baseMapper.updateCourseStatusById(courseId);
        return flag>0;
    }
}
