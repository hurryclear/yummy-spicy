package com.yummy.controller.admin;

import com.yummy.constant.MessageConstant;
import com.yummy.result.Result;
import com.yummy.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "Common API")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("upload")
    @ApiOperation("Upload files")
    public Result<String> upload(MultipartFile file) {
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
}
