package com.kevin.online.ucenter.mapper;

import com.kevin.online.ucenter.entity.EduUcenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
public interface EduUcenterMapper extends BaseMapper<EduUcenter> {

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterNum(String day);

}
