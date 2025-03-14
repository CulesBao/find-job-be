package com.findjobbe.findjobbe.service;

import jakarta.mail.MessagingException;

public interface IMailService {
  void sendEmail(String to, String subject, String content) throws MessagingException;

  void sendVerifyCode(String to, String code) throws MessagingException;
}
