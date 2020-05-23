package com.kevin.online.eduservice.controller;


import com.kevin.online.common.R;
import com.kevin.online.eduservice.dto.EduChapterDto;
import com.kevin.online.eduservice.entity.EduChapter;
import com.kevin.online.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-05
 */
@Api(description = "章节接口api")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "删除章节",notes = "删除章节")
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId) {
        boolean result = eduChapterService.removeChapterId(chapterId);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "修改章节",notes = "修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean b = eduChapterService.updateById(eduChapter);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation(value = "根据章节id查询",notes = "根据章节id查询")
    @GetMapping("/getChapterById/{chapterId}")
    public R getChapterById(@PathVariable("chapterId") String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }


    @ApiOperation(value = "添加章节", notes = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean b = eduChapterService.save(eduChapter);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }


    @ApiOperation(value = "根据课程id查询章节和小节信息", notes = "根据课程id查询章节和小节信息")
    @GetMapping("/getChapterVideoList/{courseId}")
    public R getChapterVideoList(@PathVariable("courseId") String courseId) {
        List<EduChapterDto> list = eduChapterService.getChapterVideoList(courseId);
        return R.ok().data("items", list);
    }


}

