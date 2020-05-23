package com.kevin.online.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kevin.online.eduservice.entity.EduTeacher;
import com.kevin.online.eduservice.mapper.EduTeacherMapper;
import com.kevin.online.eduservice.query.QueryTeacher;
import com.kevin.online.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-04-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 条件查询带分页
     *
     * @param pageTeacher
     * @param queryTeacher
     */
    @Override
    public void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {
        //判断条件是否为空
        if (queryTeacher == null) {
            //条件如果为空，直接分页查询所有
            baseMapper.selectPage(pageTeacher, null);
            return;
        }

        //如果条件不为空,取值判断，拼接条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(queryTeacher.getName())) {
            wrapper.like("name", queryTeacher.getName());
        }
        if (!StringUtils.isEmpty(queryTeacher.getLevel())) {
            wrapper.eq("level", queryTeacher.getLevel());
        }
        if (!StringUtils.isEmpty(queryTeacher.getBeginTime())) {
            wrapper.ge("gmt_create", queryTeacher.getBeginTime());
        }
        if (!StringUtils.isEmpty(queryTeacher.getEndTime())) {
            wrapper.le("gmt_create", queryTeacher.getEndTime());
        }
        baseMapper.selectPage(pageTeacher, wrapper);

    }

    /**
     * 根据id删除讲师的方法
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteTeacherById(String id) {
        int result = baseMapper.deleteById(id);
        return result > 0;
    }
}
