package com.kevin.online.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.online.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kevin.online.eduservice.query.QueryTeacher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-04-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 条件分页查询
     * @param pageTeacher
     * @param queryTeacher
     */
    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);


    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean deleteTeacherById(String id);
}
