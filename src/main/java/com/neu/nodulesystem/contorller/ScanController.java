package com.neu.nodulesystem.contorller;



import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.entity.Scan;
import com.neu.nodulesystem.service.IScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/scan")
public class ScanController {


    @Autowired
    private IScanService studyService;

    @GetMapping("/getByPatientId")
    public Result getListByPatientId(Long id) {

        return studyService.getListByPatientId(id);
    }

    @GetMapping("/getInfoAndUrl")
    public Result getInfoAndUrl(Long id) {
        return studyService.getInfoAndUrl(id);
    }

    @PutMapping
    public Result update(Scan scan) {

        return studyService.updateById(scan) ? Result.ok() : Result.fail("更新失败");
    }

    @PostMapping
    public Result addStudy(MultipartFile file, Scan scan) {
        return studyService.addWithFile(file, scan);
    }

    @PostMapping("/updateWithFile")
    public Result updateWithFile( MultipartFile file,String scan) {
        System.out.println(1);
        return studyService.updateWithFile(file, scan);
    }

    @DeleteMapping
    public Result deleteById(Long id) {
        return studyService.deleteScanWithFile(id);
    }




}
