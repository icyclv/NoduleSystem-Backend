package com.neu.nodulesystem.contorller;


import com.neu.nodulesystem.dto.Result;
import com.neu.nodulesystem.dto.UserDTO;
import com.neu.nodulesystem.entity.Patient;
import com.neu.nodulesystem.service.IPatientService;
import com.neu.nodulesystem.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @GetMapping("/getAllPatient")
    public Result getAllPatient() {
        UserDTO userDTO = UserHolder.getUser();
        return patientService.getListByDoctorId(userDTO.getId());
    }

    @GetMapping("/getPageWithSearch")
    public Result getDoctorPatient(@RequestParam(value = "page", defaultValue = "1") Integer page,@RequestParam(value = "search", defaultValue = "") String search) {
        UserDTO userDTO = UserHolder.getUser();
        return patientService.getPageByDoctorId(userDTO.getId(),page,search);
    }


    @PostMapping
    public Result savePatient(@RequestBody Patient patient) {
        // 写入数据库

       patient.setDoctorId(UserHolder.getUser().getId());
       patientService.save(patient);
        // 返回店铺id
        return Result.ok();
    }

    @GetMapping
    public Result getPatientById(Long id) {
        return Result.ok(patientService.getById(id));
    }

    @PutMapping
    public Result updatePatient(@RequestBody Patient patient) {
        // 写入数据库
        if(patientService.updateById(patient)){
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @DeleteMapping
    public Result deleteByPatientId(Long id){
        return patientService.deleteByPatientId(id);
    }



}
