package com.kevin.online.staservice.client;

import com.kevin.online.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("xueyuan-ucservice")
@Component
public interface UcenterClient {

    /**
     * 统计某一天的注册人数
     * @param day
     * @return
     */
    @GetMapping("/ucenter/member/countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable("day") String day);
}
