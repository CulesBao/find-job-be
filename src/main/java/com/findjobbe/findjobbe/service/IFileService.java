package com.findjobbe.findjobbe.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
  String uploadImage(MultipartFile file) throws Exception;

  String deleteFile(String imageId) throws Exception;
}
