package com.neu.nodulesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.Patient;


public interface IPatientService  extends IService<Patient> {

    public Result getListByDoctorId(Long id);

    public Result getPageByDoctorId(Long id, Integer page,String search);


    Result deleteByPatientId(Long id);
}