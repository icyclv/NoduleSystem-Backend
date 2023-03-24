package com.neu.nodulesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.nodulesystem.entity.Scan;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScanMapper extends BaseMapper<Scan> {

    @Select("select * from tb_scan where patient_id=#{id}")
    @Results({         @Result(column = "id",property = "id"),               // <1>
            @Result(column = "id" , property = "noduleList" ,many=@Many(
                    select = "com.neu.nodulesystem.mapper.NoduleMapper.getListByStudyId"
            ))
    })
    List<Scan> getStudyWithPatientId(Long patientId);

    @Select("select * from tb_scan where id=#{id}")
    @Results({         @Result(column = "id",property = "id"),               // <1>
            @Result(column = "id" , property = "noduleList" ,many=@Many(
                    select = "com.neu.nodulesystem.mapper.NoduleMapper.getListByStudyId"
            ))
    })
    Scan getScanById(Long id);

}
