package com.kevin.online.eduservice.service.impl;

import com.kevin.online.eduservice.entity.EduCourseDescription;
import com.kevin.online.eduservice.mapper.EduCourseDescriptionMapper;
import com.kevin.online.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void deleteDescriptionByCourseId(String id) {
        baseMapper.deleteById(id);
    }
}
