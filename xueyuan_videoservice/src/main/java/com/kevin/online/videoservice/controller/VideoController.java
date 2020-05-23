package com.kevin.online.videoservice.controller;

import com.kevin.online.common.R;
import com.kevin.online.videoservice.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author kevin
 */
@Api(description = "课程视频上传接口api")
@RestController
@RequestMapping("/videoService/vod")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "批量删除多个阿里云端课程视频", notes = "批量删除多个阿里云端课程视频")
    @DeleteMapping("/deleteMoreVideos")
    public R deleteMoreVideos(@RequestParam("videoList") List<String> videoList) {
        videoService.deleteMoreVideos(videoList);
        return R.ok();

    }


    @ApiOperation(value = "删除阿里云端课程视频", notes = "删除阿里云端课程视频")
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideoById(@PathVariable("videoId") String videoId) {
        videoService.deleteVideoById(videoId);
        return R.ok();

    }


    @ApiOperation(value = "上传课程视频到阿里云服务器", notes = "上传课程视频到阿里云服务器")
    @PostMapping("/uploadVideos")
    public R uploadVideosToAliyun(@RequestParam("file") MultipartFile file) {
        String videoId = videoService.uploadVideosToAliyun(file);
        return R.ok().data("videoId", videoId);
    }
}
