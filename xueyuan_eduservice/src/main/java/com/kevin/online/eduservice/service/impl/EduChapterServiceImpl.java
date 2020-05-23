package com.kevin.online.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.online.eduservice.dto.EduChapterDto;
import com.kevin.online.eduservice.dto.EduVideoDto;
import com.kevin.online.eduservice.entity.EduChapter;
import com.kevin.online.eduservice.entity.EduVideo;
import com.kevin.online.eduservice.handler.EduException;
import com.kevin.online.eduservice.mapper.EduChapterMapper;
import com.kevin.online.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kevin.online.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;


    @Override
    public void deleteChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<EduChapterDto> getChapterVideoList(String courseId) {
        //1.根据课程id查询章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        //2.根据课程id查询小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(queryWrapper);
        //用于存储章节和小节的数据
        List<EduChapterDto> chapterDtoList = new ArrayList<>();
        //3.遍历课程下所有章节，对象转换到dto中
        for (int i = 0; i < eduChapters.size(); i++) {
            //获取每个章节
            EduChapter chapter = eduChapters.get(i);
            //复制值到dto对象中去
            EduChapterDto chapterDto = new EduChapterDto();
            BeanUtils.copyProperties(chapter, chapterDto);
            //dto对象放到list集合中去
            chapterDtoList.add(chapterDto);
            //创建集合用于存储小节集合
            List<EduVideoDto> eduVideoDtos = new ArrayList<>();
            //构建小节数据
            //遍历小节
            for (int m = 0; m < eduVideos.size(); m++) {
                //获取每个小节
                EduVideo video = eduVideos.get(m);
                //判断小节chapterId和章节id
                if (video.getChapterId().equals(chapter.getId())) {
                    //转化成dto对象
                    EduVideoDto videoDto = new EduVideoDto();
                    BeanUtils.copyProperties(video, videoDto);
                    //dto对象放入
                    eduVideoDtos.add(videoDto);
                }
            }
            //把小节最终放入到章节中去
            chapterDto.setChildren(eduVideoDtos);
        }
        return chapterDtoList;
    }

    @Override
    public boolean removeChapterId(String chapterId) {
        //判断章节里有无小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //有小节不进行删除
        if (count>0) {
            throw new EduException(20001,"该章节下存在视频课程，请先删除视频课程！");
        }
        //没有小节进行删除
        int result = baseMapper.deleteById(chapterId);
        return result>0;
    }
}