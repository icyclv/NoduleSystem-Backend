package com.neu.nodulesystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.nodulesystem.entity.Scan;
import com.neu.nodulesystem.entity.User;
import com.neu.nodulesystem.mapper.ScanMapper;
import com.neu.nodulesystem.service.MinioService;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class NoduleSystemApplicationTests {

    @Autowired
    private ScanMapper studyMapper;
    @Autowired
    private MinioService minioService;

    @Test
    void studySelect() throws JsonProcessingException {
      String json = "{\"id\": 12,\n" +
              " \"noduleList\": [\n" +
              "     {\"id\": 6}, \n" +
              "     {\"id\": 7}\n" +
              "     ]\n" +
              "}";

        ObjectMapper objectMapper = new ObjectMapper();
        Scan scan1 = objectMapper.readValue(json, Scan.class);
        System.out.println(scan1.toString());

    }

    @Test
    void studySelect2() throws JsonProcessingException {
        String json2 = "{\"id\": 12" + "}";
        ObjectMapper objectMapper = new ObjectMapper();
        Scan scan1 = objectMapper.readValue(json2, Scan.class);
        System.out.println(scan1.toString());
    }

    @Test
    void removeDir() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.deleteDir("9/20230317/211113");
    }
}
