package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.payload.request.RegisterRequest;
import com.findjobbe.findjobbe.payload.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.service.IAuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final MessageConstants messageConstants;
  private final IAuthService authService;

  @Autowired
  public AuthController(MessageConstants messageConstants, IAuthService authService) {
    this.messageConstants = messageConstants;
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest)
      throws MessagingException {
    return ResponseEntity.ok(authService.register(registerRequest));
  }

  @PutMapping("/verify/{accountId}")
  public ResponseEntity<?> verifyCode(
      @PathVariable String accountId, @Valid @RequestBody VerifyCodeRequest verifyCodeRequest) {
    return ResponseEntity.ok(authService.verifyCode(accountId, verifyCodeRequest));
  }
}
