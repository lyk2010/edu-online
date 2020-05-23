package com.kevin.online.eduservice.service;

import com.kevin.online.eduservice.dto.EduChapterDto;
import com.kevin.online.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id删除章节
     * @param id
     */
    void deleteChapterByCourseId(String id);

    /**
     * 根据课程id查询章节和小节信息
     * @param courseId
     * @return
     */
    List<EduChapterDto> getChapterVideoList(String courseId);

    /**
     * 根据id删除
     * @param chapterId
     * @return
     */
    boolean removeChapterId(String chapterId);
}
