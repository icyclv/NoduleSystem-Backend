package com.neu.nodulesystem.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.nodulesystem.dto.NoduleDTO;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.dto.ScanDTO;
import com.neu.nodulesystem.entity.Nodule;
import com.neu.nodulesystem.entity.Scan;
import com.neu.nodulesystem.mapper.ScanMapper;
import com.neu.nodulesystem.service.INoduleService;
import com.neu.nodulesystem.service.IScanService;
import com.neu.nodulesystem.service.MinioService;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.neu.nodulesystem.utils.Constants.*;


@Slf4j
@Service
public class ScanServiceImpl extends ServiceImpl<ScanMapper, Scan> implements IScanService {

    @Autowired
    private MinioService minioService;

    @Autowired
    @Qualifier("myObjectMapper")
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private INoduleService noduleService;

    @Override
    public Result getListByPatientId(Long id) {
        List<Scan> list = baseMapper.getStudyWithPatientId(id);
        return Result.ok(list);
    }


    private final static ExecutorService THREADPOOL=new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());





    @Override
    @Transactional
    public Result addWithFile(MultipartFile file, Scan study) {

        //上传文件
        LocalDateTime now = LocalDateTime.now();
        String fileName = study.getPatientId()+"/"+now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"/"+now.format(DateTimeFormatter.ofPattern("HHmmss"));

        try {
            minioService.upload(file, fileName+ORIGIN_SUFFIX);
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }



        //保存数据库
        study.setFileName(fileName);
        study.setStatus(0);
        save(study);
        Long scanId = study.getId();

        //发送消息
        Map<String,Object> map=new HashMap<>();
        map.put("fileName",fileName);
        map.put("scanId",scanId);

        rabbitTemplate.convertAndSend("NoduleExchange", "NoduleInference", map);



        return Result.ok();
    }

    @Override
    public Result getInfoAndUrl(Long id) {
        Scan scan = baseMapper.getScanById(id);
        String fileName = scan.getFileName();
        String url = null;
        try {
            url = minioService.getFileUrl(fileName+RESULT_SUFFIX,MINIO_EXPIRE);
        } catch (Exception e) {
           return Result.fail("获取文件url失败");
        }
        ScanDTO scanDTO = new ScanDTO();
        scanDTO.setScan(scan);
        scanDTO.setUrl(url);
        return Result.ok(scanDTO);
    }

    @Override
    @Transactional
    public Result updateWithFile(MultipartFile file, String scanString) {

        Scan scan = null;
        try {
            scan = objectMapper.readValue(scanString, Scan.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Result.fail("数据转换失败");
        }
        if(file!=null){

            try {
                minioService.upload(file, scan.getFileName()+RESULT_SUFFIX);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail("上传失败");
            }


        }
        List<Nodule> noduleList = scan.getNoduleList();
        // 将noduleList分为两个list，一个是删除的，一个是修改的
        List<Nodule> deleteList = noduleList.stream().filter(Nodule::isDeleted).collect(Collectors.toList());
        List<Nodule> updateList = noduleList.stream().filter(nodule -> !nodule.isDeleted()).collect(Collectors.toList());

        noduleService.updateBatchById(updateList);
        noduleService.removeByIds(deleteList.stream().map(Nodule::getId).collect(Collectors.toList()));
        updateById(scan);

        return Result.ok();
    }


    @Override
    @Transactional
    public Result deleteScanById(Long id) {
        if(id==null){
            return Result.fail("删除失败");
        }
        removeById(id);
        //删除结节
        noduleService.removeByMap(new HashMap<String,Object>(){{
            put("scan_id",id);
        }});
        return Result.ok();
    }

    @Override
    @Transactional
    public Result deleteScanWithFile(Long id) {
        Scan scan = getById(id);
        if(id==null){
            return Result.fail("删除失败");
        }
        removeById(id);
        //删除结节
        noduleService.removeByMap(new HashMap<String,Object>(){{
            put("scan_id",id);
        }});

        //删除文件,异步
        THREADPOOL.execute(() -> {
            try {
                minioService.deleteDir(scan.getFileName());
            } catch (Exception e) {
                log.error("删除图像文件失败"+id, e);
            }
        });

        return Result.ok();
    }




    @RabbitHandler
    @RabbitListener(queues = "NoduleResultQueue", containerFactory="rabbitListenerContainerFactory",ackMode = "AUTO")
    public void process(NoduleDTO noduleDTO) {
        Long scanId = noduleDTO.getScanId();
        List<Nodule> noduleList = noduleDTO.getNoduleList();
        saveNoduleList(noduleDTO.isSuccess(),scanId,noduleList);
    }

    @Transactional
    public void saveNoduleList(boolean success, Long scanId,List<Nodule> noduleList){
        if(!success){
            update().set("status",2).eq("id",scanId).update();
            return;
        }
        update().set("status",1).eq("id",scanId).update();
        noduleService.saveBatch(noduleList);
    }


}
