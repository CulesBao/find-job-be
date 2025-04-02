package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.exception.ForbiddenException;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
  String uploadImage(MultipartFile file) throws Exception;

  String uploadFile(MultipartFile file) throws ForbiddenException, IOException;

  String deleteFile(String imageId) throws Exception;
}
