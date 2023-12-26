package com.itheima.controller;


import com.itheima.pojo.Result;
import com.itheima.utils.AliOSSUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        //1.获取文件内容的输入流，写入到本地磁盘文件
        String originalFilename = file.getOriginalFilename();

        //2.保证文件名不相同 (添加UUID)
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));  // 随机UUID信息 + 原始图片名字(.之后的都是图片格式)

        //3.图片存到我本地位置
        //file.transferTo(new File("F:\\JAVA路线课程资料\\springboot3+vue3\\files\\"+filename));

        //3.图片上传到阿里云
        String url = AliOSSUtils.uploadFile(filename, file.getInputStream());

        //返回正确状态和图片地址string
        return Result.success(url);
    }

}
