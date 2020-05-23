package com.kevin.online.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.online.ucenter.entity.EduUcenter;
import com.kevin.online.ucenter.mapper.EduUcenterMapper;
import com.kevin.online.ucenter.service.EduUcenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
@Service
public class EduUcenterServiceImpl extends ServiceImpl<EduUcenterMapper, EduUcenter> implements EduUcenterService {

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterNum(String day) {
        return baseMapper.countRegisterNum(day);
    }

    /**
     * 根据微信openID判断用户是否存在
     * @param openid
     * @return
     */
    @Override
    public EduUcenter existWxUserByOpenid(String openid) {
        QueryWrapper<EduUcenter> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        EduUcenter ucenter = baseMapper.selectOne(wrapper);
        return ucenter;
    }
}
