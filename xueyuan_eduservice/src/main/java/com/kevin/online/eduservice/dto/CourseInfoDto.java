package com.kevin.online.eduservice.dto;

import lombok.Data;


/**
 * @author kevin
 * 用于封装课程相关信息的实体类
 */
@Data
public class CourseInfoDto {
    private String id;
    private String title;
    private String cover;
    private String price;
    private String description;
    private String teacherName;
    /**
     * 一级分类名称
     */
    private String levelOne;
    /**
     * 二级分类名称
     */
    private String levelTwo;

}
