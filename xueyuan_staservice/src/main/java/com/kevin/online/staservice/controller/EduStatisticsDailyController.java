package com.kevin.online.staservice.controller;


import com.kevin.online.common.R;
import com.kevin.online.staservice.service.EduStatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kevin
 * @since 2020-05-18
 */
@Api(description = "数据统计相关接口api")
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
public class EduStatisticsDailyController {

    @Autowired
    private EduStatisticsDailyService dailyService;

    @ApiOperation(value = "获取某一天的注册人数",notes = "获取某一天的注册人数")
    @GetMapping("/getStatisticsDay/{day}")
    public R getStatisticsDay(@PathVariable("day") String day) {
        dailyService.getCountRegisterNum(day);
        return R.ok();
    }


    /**
     * type: 查询因子（查询什么数据，比如注册人数）
     * begin：查询的开始时间
     * end：查询的结束时间
     * @return
     */
    @ApiOperation(value = "返回图表显示的数据",notes = "返回图表显示的数据")
    @GetMapping("/getChartsData/{type}/{begin}/{end}")
    public R getChartsData(@PathVariable("type") String type,
                           @PathVariable("begin") String begin,
                           @PathVariable("end") String end) {
        Map<String,Object> map =  dailyService.getChartsData(type,begin,end);
        return R.ok().data("mapData",map);
    }

}

