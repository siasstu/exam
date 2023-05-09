package com.sias.admin.controller;

import com.sias.commons.base.RestResponse;
import com.sias.system.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-13 22:19:11
 */
@RestController
@RequestMapping("/api/user")
public class ExamImageController {

    private final String examImagesFilePath="D:\\exam\\exam\\";

    @RequestMapping("/imageUpload")
    public RestResponse updateAvatar(MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            String originalFilename = file.getOriginalFilename();
            String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName= DateUtil.getCurrentDateStr()+suffixName;
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(examImagesFilePath+newFileName));
            return RestResponse.ok(newFileName);
        }else {
            return RestResponse.fail(230,"失败");
        }
    }

}