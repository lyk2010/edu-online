package com.kevin.online.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.online.eduservice.client.VidClient;
import com.kevin.online.eduservice.entity.EduVideo;
import com.kevin.online.eduservice.mapper.EduVideoMapper;
import com.kevin.online.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VidClient vidClient;

    /**
     * 根据课程id删除小节
     * @param id
     */
    @Override
    public void deleteVideoByCourseId(String id) {
        //把课程里的所有视频进行删除
        //1.获取课程里所有视频id(根据课程id查询课程所有小节中视频id)
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        //只是查询视频id字段值
        videoQueryWrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(videoQueryWrapper);
        List<String> videoIdList = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            videoIdList.add(videoSourceId);
        }
        //2.调用方法批量删除
        vidClient.deleteMoreVideos(videoIdList);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);

    }

    /**
     * 删除小节
     * @param xiaoJieId
     * @return
     */
    @Override
    public Boolean removeVideo(String xiaoJieId) {
        //获取视频id
        EduVideo eduVideo = baseMapper.selectById(xiaoJieId);
        //判断视频是否存在
        if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            vidClient.removeVideoAliyunId(eduVideo.getVideoSourceId());
        }
        int result = baseMapper.deleteById(xiaoJieId);
        return result>0;
    }
}
