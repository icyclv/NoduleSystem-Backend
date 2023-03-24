package com.neu.nodulesystem.service.impl;


import com.neu.nodulesystem.config.bean.MinioPro;
import com.neu.nodulesystem.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioPro minioPro;

    @Autowired
    private MinioClient minioClient;




    @Override
    public void upload(MultipartFile file,String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {




        PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioPro.getBucketName()).object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1).build();
        //文件名称相同会覆盖
        minioClient.putObject(objectArgs);

    }

    /**
     * 删除文件
     */
    @Override
    public void delete(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(minioPro.getBucketName()).object(fileName).build();
        minioClient.removeObject(removeObjectArgs);

    }

    @Override
    public void deleteDir(String dirName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(minioPro.getBucketName()).prefix(dirName).recursive(true).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(minioPro.getBucketName()).object(item.objectName()).build();
            minioClient.removeObject(removeObjectArgs);
        }

    }

    /**
     * 获取文件url预览
     */
    @Override
    public String getFileUrl(String objectName,int expire) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(minioPro.getBucketName())
                    .object(objectName)
                    .expiry(24 * 60 * 60)
                    .method(Method.GET)
                    .build();

        return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);

    }




}
