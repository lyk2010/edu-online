package com.kevin.online.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 定义过滤器的类型
     *
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 设置过滤器执行顺序
     * 值越小越先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 决定是否执行下面的run方法
     * 如果返回false 表示过滤器放行操作，不执行run方法
     * 如果返回true 表示没有放行，run方法会执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //判断：当访问的路径包含/videoService/vod/uploadVideos 进行登录校验
        //1.获取请求路径
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String requestURI = request.getRequestURI();
        //2.根据请求路径判断是否包含/videoService/vod/uploadVideos
        String url = "/videoService/vod/uploadVideos";
        if (StringUtils.isEmpty(requestURI) && requestURI.contains(url)) {
            //3.如果包含，进行验证操作 return true
            return true;
        }
        return false;
    }

    /**
     * 过滤器具体逻辑方法
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            //不能访问接口 不会向后执行
            currentContext.setSendZuulResponse(false);
            //设置不能访问状态码
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

        }
        return null;
    }
}
