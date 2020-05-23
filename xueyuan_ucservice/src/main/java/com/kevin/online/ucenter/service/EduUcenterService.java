package com.kevin.online.ucenter.service;

import com.kevin.online.ucenter.entity.EduUcenter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
public interface EduUcenterService extends IService<EduUcenter> {

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    Integer countRegisterNum(String day);

    /**
     * 根据微信openID判断用户是否存在
     * @param openid
     * @return
     */
    EduUcenter existWxUserByOpenid(String openid);
}
