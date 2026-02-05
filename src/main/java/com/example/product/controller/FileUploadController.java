package com.example.product.controller;

import com.example.product.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传控制器
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传图片
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择要上传的文件");
        }

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return Result.error("文件名不能为空");
        }

        // 检查文件类型
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!".jpg".equals(suffix) && !".jpeg".equals(suffix) && !".png".equals(suffix) && !".gif".equals(suffix)) {
            return Result.error("只支持 jpg、jpeg、png、gif 格式的图片");
        }

        // 生成新文件名
        String newFilename = UUID.randomUUID().toString().replace("-", "") + suffix;

        // 按日期创建目录
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dirPath = uploadPath + datePath;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存文件
        try {
            File destFile = new File(dirPath + "/" + newFilename);
            file.transferTo(destFile);
            // 返回访问路径
            String accessUrl = "/upload/" + datePath + "/" + newFilename;
            return Result.success("上传成功", accessUrl);
        } catch (IOException e) {
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
}
