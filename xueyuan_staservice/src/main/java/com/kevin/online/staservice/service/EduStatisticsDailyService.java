package com.kevin.online.staservice.service;

import com.kevin.online.staservice.entity.EduStatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
public interface EduStatisticsDailyService extends IService<EduStatisticsDaily> {

    /**
     *获取某一天的注册人数
     * @param day
     */
    void getCountRegisterNum(String day);

    /**
     * 根据查询因子及起止时间获取图表数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String, Object> getChartsData(String type, String begin, String end);
}
