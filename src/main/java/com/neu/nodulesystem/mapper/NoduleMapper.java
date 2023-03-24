package com.neu.nodulesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neu.nodulesystem.entity.Nodule;
import com.neu.nodulesystem.entity.Patient;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoduleMapper  extends BaseMapper<Nodule> {

    @Select("select * from tb_nodule where scan_id=#{scanId} and deleted=0")
    List<Nodule> getListByStudyId(Long scanId);
}

