package com.kevin.online.eduservice.handler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author kevin
 * 在服务启动时，让这个类读取配置文件的内容 实现InitializingBean接口
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.file.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.file.host}")
    private String host;

    //定义常量
    public static String ENDPOINT;
    public static String ACCESSKEYID;
    public static String ACCESSKEYSECRET;
    public static String BUCKETNAME;
    public static String HOST;


    /**
     * 服务启动的时候，ConstantPropertiesUtil初始化，调用里面的afterPropertiesSet方法读取配置文件内容
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ACCESSKEYID = accessKeyId;
        ACCESSKEYSECRET = accessKeySecret;
        BUCKETNAME = bucketName;
        HOST = host;
    }
}
