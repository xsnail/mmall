package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public interface IFileService {
    String upload(MultipartFile file,String path);
}
