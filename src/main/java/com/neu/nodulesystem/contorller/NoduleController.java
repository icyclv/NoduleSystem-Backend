package com.neu.nodulesystem.contorller;


import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.Nodule;
import com.neu.nodulesystem.service.INoduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nodule")
public class NoduleController {

    @Autowired
    private INoduleService noduleService;

    @PutMapping
    public Result update(Nodule nodule) {
       return noduleService.updateById(nodule)? Result.ok() : Result.fail("更新失败");
    }

    @PutMapping("/batch")
    public Result batchUpdate(List<Nodule> noduleList) {
        return noduleService.updateBatchById(noduleList)? Result.ok() : Result.fail("更新失败");
    }
}
