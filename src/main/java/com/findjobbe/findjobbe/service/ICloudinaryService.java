package com.findjobbe.findjobbe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
  String uploadFile(MultipartFile file) throws Exception;

  String deleteFile(String imageId) throws Exception;
}
