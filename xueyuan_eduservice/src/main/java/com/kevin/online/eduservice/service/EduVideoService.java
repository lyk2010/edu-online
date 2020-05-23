package com.kevin.online.eduservice.service;

import com.kevin.online.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param id
     */
    void deleteVideoByCourseId(String id);

    /**
     * 根据id删除
     * @param xiaoJieId
     * @return
     */
    Boolean removeVideo(String xiaoJieId);
}
