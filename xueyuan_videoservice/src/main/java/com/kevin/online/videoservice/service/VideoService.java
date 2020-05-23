package com.kevin.online.videoservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author kevin
 */
public interface VideoService {

    /**
     * 上传课程视频到阿里云服务器
     * @param file 需要上传的视频文件
     * @return 返回值是上传后的视频id
     */
    String uploadVideosToAliyun(MultipartFile file);

    /**
     * 删除阿里云端课程视频
     * @param videoId
     */
    void deleteVideoById(String videoId);

    /**
     * 批量删除多个阿里云端课程视频
     * @param videoList
     */
    void deleteMoreVideos(List<String> videoList);
}
