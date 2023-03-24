package com.neu.nodulesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.Nodule;
import com.neu.nodulesystem.entity.Scan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IScanService extends IService<Scan> {
    public Result getListByPatientId(Long id);

    Result addWithFile(MultipartFile file, Scan study);

    Result getInfoAndUrl(Long id);

    Result updateWithFile(MultipartFile file, String scanString);

    public Result deleteScanById(Long id);

    Result deleteScanWithFile(Long id);
}
