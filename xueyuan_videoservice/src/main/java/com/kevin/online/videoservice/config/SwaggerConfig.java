package com.kevin.online.videoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Guli_edu_videoUpload_webApi")
                .apiInfo(apiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }


    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网站-阿里云视频上传API文档")
                .description("本文档描述了课程视频上传微服务接口定义")
                .version("1.0")
                .contact(new Contact("kevin","http://www.baidu.com","kevinliu2010@qq.com"))
                .build();
    }
}
