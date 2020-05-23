package com.kevin.online.eduservice.controller;


import com.kevin.online.common.R;
import com.kevin.online.eduservice.dto.OneSubjectDto;
import com.kevin.online.eduservice.entity.EduSubject;
import com.kevin.online.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
@Api(description = "课程分类接口api")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     *   通过上传Excel文件获取文件内容
     *
     */
    @ApiOperation(value = "导入Excel入库", notes = "将课程分类Excel入库")
    @PostMapping("/import")
    public R importExcelSubject(@RequestParam("file") MultipartFile file) {
        List<String> msg = subjectService.importSubject(file);
        if (msg.size() == 0) {
            return R.ok();
        }else {
            return R.error().msg("部分数据导入失败").data("msgList",msg);
        }
    }


    /**
     * 获取所有分类信息，返回指定格式（树形结构）
     * @return
     */
    @GetMapping("/getAllSubject")
    @ApiOperation(value = "获取所有树形分类信息",notes = "获取所有分类信息，按照指定格式返回")
    public R getAllSubjectList() {
        List<OneSubjectDto> list = subjectService.getSubjectList();
        return R.ok().data("OneSubjectDto",list);
    }


    /**
     * 根据id删除一级分类
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除一级分类", notes = "根据id删除一级分类")
    @DeleteMapping("/{id}")
    public R deleteSubjectById(@PathVariable("id") String id) {
        Boolean flag = subjectService.deleteSubjectById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }


    @PostMapping("/addOneLevel")
    @ApiOperation(value = "添加一级分类",notes = "添加一级分类")
    public R addOneLevel(@RequestBody EduSubject eduSubject) {
        Boolean flag = subjectService.saveOneLevel(eduSubject);
        if (flag) {

            return R.ok();
        }else {
            return R.error();
        }
    }


    @PostMapping("/addTwoLevel")
    @ApiOperation(value = "添加二级分类",notes = "添加二级分类")
    public R addTwoLevel(@RequestBody EduSubject eduSubject) {
        Boolean flag = subjectService.saveTwoLevel(eduSubject);
        if (flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }



}

