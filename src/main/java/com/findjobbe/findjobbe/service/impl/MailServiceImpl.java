package com.findjobbe.findjobbe.service.impl;

import com.findjobbe.findjobbe.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements IMailService {
  @Autowired private JavaMailSender mailSender;
  @Autowired private TemplateEngine templateEngine;

  @Override
  public void sendEmail(String to, String subject, String content) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(content, true);

    mailSender.send(message);
  }

  @Override
  public void sendVerifyCode(String to, String code) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    Context context = new Context();
    context.setVariable("code", code);
    String htmlContent = templateEngine.process("SendVerifyCodeTemplate", context);

    helper.setTo(to);
    helper.setSubject("Your Verification Code");
    helper.setText(htmlContent, true);

    mailSender.send(message);
  }
}
