package com.findjobbe.findjobbe.service.impl;

import com.cloudinary.Cloudinary;
import com.findjobbe.findjobbe.service.ICloudinaryService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryServiceImpl implements ICloudinaryService {
  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String uploadFile(MultipartFile file) throws Exception {
    Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
    String url = (String) data.get("url");

    return url;
  }

  @Override
  public String deleteFile(String imageId) throws Exception {
    return "";
  }
}
