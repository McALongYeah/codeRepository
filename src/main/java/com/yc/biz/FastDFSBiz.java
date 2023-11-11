package com.yc.biz;

import org.springframework.web.multipart.MultipartFile;

public interface FastDFSBiz {
    public String uploadFile(MultipartFile file);
}
