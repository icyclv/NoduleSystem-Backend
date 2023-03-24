package com.neu.nodulesystem.contorller;


import com.neu.nodulesystem.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String queryShopById(MultipartFile file,String user,String id) {

        System.out.println(user);
        System.out.println(id);
//        String fileName = minioService.upload(file);
//        return fileName;
        return "123";
    }

//    @GetMapping("/delete")
//    public String delete(String fileName, HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//
//                    System.out.println(cookie.getName()+" "+cookie.getValue());
//
//            }
//        }
//        return minioService.delete(fileName);
//    }

//    @GetMapping("/getFileUrl")
//    public String getFileUrl(String objectName, HttpServletResponse response) {
//
//        Cookie cookie = new Cookie("ticket","12341");
//        response.addCookie(cookie);
//        return minioService.getFileUrl(objectName);
//    }
}
