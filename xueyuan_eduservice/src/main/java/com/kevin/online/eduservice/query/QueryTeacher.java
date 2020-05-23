package com.kevin.online.eduservice.query;

import lombok.Data;

/**
 * @author keivn
 * 用于封装查询条件
 */
@Data
public class QueryTeacher {

    private String name;

    private String level;

    private String beginTime;

    private String endTime;
}
