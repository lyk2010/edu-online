package com.kevin.online.eduservice.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.kevin.online.common.R;
import com.kevin.online.eduservice.handler.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 资源上传下载
 *
 * @author kevin
 */

@Api(description = "资源上传下载接口api")
@RestController
@RequestMapping("/eduservice/oss")
@CrossOrigin
public class FileUploadController {

    /**
     * 上传讲师图像
     */
    @PostMapping("/upload")
    @ApiOperation(value = "讲师头像上传",notes = "新增讲师时的头像上传")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "host",required = false) String host) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESSKEYID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESSKEYSECRET;
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;
        try {
            //1.获取上传文件 注意：@RequestParam("file")中的参数名必须和表单中name属性的名一致
            String filename = file.getOriginalFilename();
            //在文件名称之前增加UUID，防止文件名称不重复
            String uuid = UUID.randomUUID().toString();
            filename = uuid + filename;
            //获取当前日期 格式为：2020/5/1
            String filepath = new DateTime().toString("yyyy/MM/dd");
            //拼接文件完成名称(按照日期存储，到每天) 最终格式为：2020/05/01/.....
            filename = filepath + "/" + filename;

            String hostName = ConstantPropertiesUtil.HOST;
            //如果上传头像，host值应为空，如果上传课程封面图片，host有值
            if (!StringUtils.isEmpty(host)){
                hostName = host;
            }

            filename = filepath + "/" + hostName + "/" + filename;

            //2.获取上传文件名称和输入流
            InputStream inputStream = file.getInputStream();
            //3.上传
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();


            //返回存储成功后的OSS地址
            String imgPath = "http://" + bucketName + "." + endpoint + "/" + filename;
            return R.ok().data("imgUrl",imgPath);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error();
        }

    }


}
