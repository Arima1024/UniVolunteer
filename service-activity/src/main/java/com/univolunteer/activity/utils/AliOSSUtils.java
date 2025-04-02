package com.univolunteer.activity.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSUtils {
    // 示例的OSS配置，建议通过配置文件来管理
    private String endpoint = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String bucketName = "";

    /**
     * 实现上传文件到OSS
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = multipartFile.getInputStream();

        // 避免文件覆盖
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));


        // 上传文件到OSS
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(bucketName, fileName, inputStream);

        // 构建文件的访问路径
        String url = buildFileUrl(endpoint, bucketName, fileName);

        // 关闭ossClient
        ossClient.shutdown();
        return url; // 返回上传到OSS的路径
    }

    /**
     * 构建文件的访问URL
     */
    private String buildFileUrl(String endpoint, String bucketName, String fileName) {
        // 判断endpoint是否包含 "://"
        if (endpoint.contains("//")) {
            String[] parts = endpoint.split("//");
            return parts[0] + "//" + bucketName + "." + parts[1] + "/" + fileName;
        } else {
            // 如果没有 "://", 直接拼接为一个简单的URL
            return "http://" + bucketName + "." + endpoint + "/" + fileName;
        }
    }
}
