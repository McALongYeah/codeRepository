package com.yc.biz;


import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class FastDFSBizImpl implements FastDFSBiz{

    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadFile(MultipartFile file) {
        String path = "group1/M00/00/00/rBIAA2VLbI2AApsBAABDpihVrE0424.jpg";
        try{
            InputStream iis= file.getInputStream();
            //从流中取出图片数据,以byte[] 形式返回
            StorePath storePath = storageClient.uploadFile(IOUtils.toByteArray(iis),
                    FilenameUtils.getExtension(file.getOriginalFilename()));
            log.info("上传图片成功，路径信息:" + storePath);
            log.info("fullPath:" + storePath.getFullPath());
            log.info("groupd:" + storePath.getGroup());
            log.info("path:" + storePath.getPath());
            //TODO
            path = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return path;
    }
}
