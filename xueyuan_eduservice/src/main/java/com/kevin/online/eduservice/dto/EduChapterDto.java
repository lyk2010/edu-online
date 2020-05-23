package com.kevin.online.eduservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin
 * 章节
 */
@Data
public class EduChapterDto {

    private String id;

    private String title;

    private List<EduVideoDto> children = new ArrayList<>();


}
