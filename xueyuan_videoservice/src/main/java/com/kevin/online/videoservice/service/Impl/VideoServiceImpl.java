package com.kevin.online.videoservice.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.kevin.online.videoservice.exception.EduException;
import com.kevin.online.videoservice.service.VideoService;
import com.kevin.online.videoservice.utils.AliyunVodSDKUtils;
import com.kevin.online.videoservice.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author kevin
 */
@Service
public class VideoServiceImpl implements VideoService {

    /**
     * 上传课程视频到阿里云服务器
     * @param file 需要上传的视频文件
     * @return
     */
    @Override
    public String uploadVideosToAliyun(MultipartFile file) {
        try {
            //获取上传文件的名称
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, fileName, file.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除阿里云端课程视频
     * @param videoId
     */
    @Override
    public void deleteVideoById(String videoId) {
        try {
            //初始化
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new EduException(20001,"视频删除失败！");
        }
    }

    /**
     * 批量删除多个阿里云端课程视频
     * @param videoList
     */
    @Override
    public void deleteMoreVideos(List<String> videoList) {
        try {
            //初始化
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            request.setVideoIds(videoIds);
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new EduException(20001,"视频删除失败！");
        }
    }
}
