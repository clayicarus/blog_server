package io.blog.controller;

import io.blog.ResponseResult;
import io.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img)
    {
        return uploadService.uploadImg(img);
    }
    @Autowired
    private UploadService uploadService;
}
