package com.kevin.online.eduservice.controller;


import com.kevin.online.common.R;
import com.kevin.online.eduservice.entity.EduVideo;
import com.kevin.online.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
@Api(description = "课程小节视频接口api")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation(value = "添加小节",notes = "添加小节")
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean result = eduVideoService.save(eduVideo);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询",notes = "根据id查询")
    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }


    @ApiOperation(value = "修改",notes = "修改")
    @PostMapping("/updateVideoInfo")
    public R updateVideoInfo(@RequestBody EduVideo eduVideo) {
        boolean b = eduVideoService.updateById(eduVideo);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id删除小节",notes = "根据id删除小节")
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideoById(@PathVariable("videoId") String videoId) {
        Boolean flag = eduVideoService.removeVideo(videoId);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }





}

