package com.neu.nodulesystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.nodulesystem.dto.PageDto;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.Patient;
import com.neu.nodulesystem.mapper.PatientMapper;
import com.neu.nodulesystem.service.IPatientService;
import com.neu.nodulesystem.service.IScanService;
import com.neu.nodulesystem.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;

import static com.neu.nodulesystem.utils.Constants.MAX_PAGE_SIZE;

@Slf4j
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {


    @Autowired
    private IScanService scanService;

    @Autowired
    private MinioService minioService;

    private final static ExecutorService THREADPOOL=new ThreadPoolExecutor(0, 10,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());


    @Override
    public Result getListByDoctorId(Long id) {
        // 返回所有所属医生id为id的病人
        List<Patient> list = query().eq("doctor_id", id).list();
        return Result.ok(list);
    }

    @Override
    public Result getPageByDoctorId(Long id, Integer page,String search){
        // 返回所有所属医生id为id的病人
        Page<Patient> patientPage = query().eq("doctor_id", id).like("name", search).page(new Page<>(page, MAX_PAGE_SIZE));
        PageDto<Patient> pageDto = new PageDto<>();
        pageDto.setTotal(patientPage.getTotal());
        pageDto.setData(patientPage.getRecords());
        pageDto.setCurrent(patientPage.getCurrent());
        pageDto.setSize(patientPage.getSize());

        return Result.ok(pageDto);
    }


    @Override
    @Transactional
    public Result deleteByPatientId(Long id) {
        if(!removeById(id)){
            return Result.fail("删除失败");
        }
        // 删除扫描及结节
        scanService.query().eq("patient_id", id).list().forEach(scan -> {
            scanService.deleteScanById(scan.getId());
        });

        // 删除图像文件，异步执行
        THREADPOOL.execute(() -> {
            try {
                minioService.deleteDir(id.toString());
            } catch (Exception e) {
                log.error("删除图像文件失败"+id, e);
            }
        });



        return Result.ok();
    }
}
