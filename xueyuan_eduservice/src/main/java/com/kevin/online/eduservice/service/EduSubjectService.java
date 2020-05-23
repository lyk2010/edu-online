package com.kevin.online.eduservice.service;

import com.kevin.online.eduservice.dto.OneSubjectDto;
import com.kevin.online.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 读取Excel,添加到分类表
     * @param file Excel文件
     * @return
     */
    List<String> importSubject(MultipartFile file);


    /**
     * 判断是否存在一级分类
     * @param title  一级分类名称
     * @return EduSubject
     */
    EduSubject existOneEduSubjectByTitle(String title);


    /**
     * 判断是否存在二级分类
     * @param title 二级分类名称
     * @param parentId 二级分类对应的一级分类id
     * @return EduSubject
     */
    EduSubject existTwoEduSubjectByTitle(String title,String parentId);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Boolean deleteSubjectById(String id);


    /**
     * 获取指定格式的所有分类（用于树形结构展示）
     * @return
     */
    List<OneSubjectDto> getSubjectList();

    /**
     * 添加一级分类
     * @param eduSubject
     * @return
     */
    Boolean saveOneLevel(EduSubject eduSubject);

    /**
     * 添加二级分类
     * @param eduSubject
     * @return
     */
    Boolean saveTwoLevel(EduSubject eduSubject);
}
