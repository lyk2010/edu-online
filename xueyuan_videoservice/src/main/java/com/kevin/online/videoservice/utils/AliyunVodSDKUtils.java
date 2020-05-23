package com.kevin.online.videoservice.utils;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author kevin
 * 阿里云视频点播SDK初始化操作
 */
public class AliyunVodSDKUtils {

    public static DefaultAcsClient initVodClient(String accessKeyId,String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

}
