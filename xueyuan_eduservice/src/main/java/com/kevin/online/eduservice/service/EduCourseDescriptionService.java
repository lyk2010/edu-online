package com.kevin.online.eduservice.service;

import com.kevin.online.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    /**
     * 根据课程id删除描述
     * @param id
     */
    void deleteDescriptionByCourseId(String id);
}
