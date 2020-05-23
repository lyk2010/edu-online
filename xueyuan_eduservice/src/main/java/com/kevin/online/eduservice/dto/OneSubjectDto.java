package com.kevin.online.eduservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keivn
 */
@Data
public class OneSubjectDto {

    /**
     * 一级分类id
     */
    private String id;

    /**
     * 一级分类名称
     */
    private String title;

    /**
     * 一级分类的所有二级分类
     */
    private List<TwoSubjectDto> children = new ArrayList<>();

}
