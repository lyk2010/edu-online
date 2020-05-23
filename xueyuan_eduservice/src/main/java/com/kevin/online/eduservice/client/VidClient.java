package com.kevin.online.eduservice.client;

import com.kevin.online.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author kevin
 */
@FeignClient("xueyuan-videoservice")
@Component
public interface VidClient {

    /**
     * 定义调用的方法
     * @param videoId
     * @return
     */
    @DeleteMapping("/videoService/vod/deleteVideo/{videoId}")
    public R removeVideoAliyunId(@PathVariable("videoId") String videoId);


    /**
     * 批量删除多个阿里云端课程视频
     * @param videoList
     * @return
     */
    @DeleteMapping("/videoService/vod/deleteMoreVideos")
    public R deleteMoreVideos(@RequestParam("videoList") List<String> videoList);

}
