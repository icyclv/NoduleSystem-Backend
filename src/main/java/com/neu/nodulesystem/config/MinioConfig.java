package com.neu.nodulesystem.config;


import com.neu.nodulesystem.config.bean.MinioPro;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Component
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Value("${minio.bucketName}")
    private String bucketName;





    /**
     * 注入minio 客户端
     * @return
     */
    @Bean
    public MinioClient minioClient(){

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public MinioPro minioPro(MinioClient minioClient){

        MinioPro minioPro = new MinioPro();
        minioPro.setUrl(endpoint);
        minioPro.setAccesskey(accessKey);
        minioPro.setSecretKey(secretKey);
        minioPro.setBucketName(bucketName);

        try {
            if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())){
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return minioPro;
    }

}
