package com.findjobbe.findjobbe.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.findjobbe.findjobbe.exception.ForbiddenException;
import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.service.IFileService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryServiceImpl implements IFileService {
  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String uploadImage(MultipartFile file) throws ForbiddenException, IOException {
    List<String> allowedFormats = List.of("image/png", "image/jpeg", "image/jpg");
    if (!allowedFormats.contains(file.getContentType())) {
      throw new ForbiddenException(MessageConstants.UNSUPPORTED_FILE_TYPE);
    }
    Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
    return (String) data.get("url");
  }

  @Override
  public String uploadFile(MultipartFile file) throws ForbiddenException, IOException {
    if (!file.getContentType().equals("application/pdf")) {
      throw new ForbiddenException(MessageConstants.UNSUPPORTED_FILE_TYPE);
    }
    Map data =
        cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "raw"));
    return (String) data.get("url");
  }

  @Override
  public String deleteFile(String imageId) throws Exception {
    return "";
  }
}
