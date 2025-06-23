package com.yummy.controller.admin;

import com.yummy.constant.MessageConstant;
import com.yummy.result.Result;
import com.yummy.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

// Frontend uses "/admin/common/upload" api
// there are two methods to store the uploaded images (locally and in cloud)
// if you want to use anyone of them, you need to change the mapping url to "upload"
// currently I use local storage

@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common API")
@Slf4j
public class CommonController {

    @Value("${yummy.file.upload-dir}")
    private String uploadDir;

    @Value("${yummy.file.access-url}")
    private String accessUrl;

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("ossupload")
    @ApiOperation("Upload files oss")
    // #TODO: MultipartFile?
    public Result<String> uploadOss(MultipartFile file) {
        log.info("Upload files: {}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), newFilename);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("upload fails");
//            throw new RuntimeException(e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    @PostMapping("/upload")
    @ApiOperation("Upload files locally")
    public Result<String> uploadLocally(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        File dest = new File(uploadDir + File.separator + newFileName);
        try {
            file.transferTo(dest);
            // 返回前端可访问的 URL
            String fileUrl = accessUrl + "/" + newFileName;
            return Result.success(fileUrl);
        } catch (IOException e) {
            log.error(MessageConstant.UPLOAD_FAILED, e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
