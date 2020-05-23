package com.kevin.online.eduservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author kevin
 * 发布课程参数接收模型类
 */
@ApiModel(value = "发布课程参数接收模型类",description = "")
@Data
public class CourseInfoFormDto implements Serializable {

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程一级分类id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程售价，设置为0，则免费")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;



}
