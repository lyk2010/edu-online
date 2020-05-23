package com.kevin.online.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.online.common.R;
import com.kevin.online.staservice.client.UcenterClient;
import com.kevin.online.staservice.entity.EduStatisticsDaily;
import com.kevin.online.staservice.mapper.EduStatisticsDailyMapper;
import com.kevin.online.staservice.service.EduStatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
@Service
public class EduStatisticsDailyServiceImpl extends ServiceImpl<EduStatisticsDailyMapper, EduStatisticsDaily> implements EduStatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void getCountRegisterNum(String day) {
        //判断统计分析表里是否存在添加的天数记录，如果存在，删除再添加
        QueryWrapper<EduStatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        R r = ucenterClient.countRegisterNum(day);
        Integer registerNum = (Integer) r.getData().get("countNum");
        //把获取的数据加入到统计分析表中
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        EduStatisticsDaily eduStatisticsDaily = new EduStatisticsDaily();
        eduStatisticsDaily.setDateCalculated(day);
        eduStatisticsDaily.setRegisterNum(registerNum);
        eduStatisticsDaily.setCourseNum(courseNum);
        eduStatisticsDaily.setLoginNum(loginNum);
        eduStatisticsDaily.setVideoViewNum(videoViewNum);
        baseMapper.insert(eduStatisticsDaily);


    }

    /**
     * 根据查询因子及起止时间获取图表数据
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */
    @Override
    public Map<String, Object> getChartsData(String type, String begin, String end) {

        QueryWrapper<EduStatisticsDaily> wrapper = new QueryWrapper<>();
        //大于等于开始时间 小于等于截止时间
//        wrapper.ge("date_calculated",begin);
//        wrapper.le("date_calculated",end);

        wrapper.between("date_calculated", begin, end);
        //查询指定的字段
        wrapper.select("date_calculated", type);
        List<EduStatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        List<String> timeList = new ArrayList<>();
        List<Integer> dateList = new ArrayList<>();
        for (int i = 0; i < dailyList.size(); i++) {
            EduStatisticsDaily daily = dailyList.get(i);
            String dateCalculated = daily.getDateCalculated();
            timeList.add(dateCalculated);
            switch (type) {
                case "login_num":
                    dateList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dateList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dateList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dateList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("timeList",timeList);
        map.put("dateList",dateList);
        return map;
    }
}
