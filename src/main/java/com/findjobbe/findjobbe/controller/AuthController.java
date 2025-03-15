package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.exception.MessageConstants;
import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.service.IAuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import java.util.Optional;
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
    AccountDto accountDto = authService.register(registerRequest);
    return ResponseEntity.ok(
        new AbstractResponse("Register successfully", Optional.of(accountDto)));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    String token = authService.login(loginRequest);
    return ResponseEntity.ok(new AbstractResponse("Login successfully", Optional.of(token)));
  }

  @PutMapping("/verify/{accountId}")
  public ResponseEntity<?> verifyCode(
      @PathVariable String accountId, @Valid @RequestBody VerifyCodeRequest verifyCodeRequest) {
    authService.verifyCode(accountId, verifyCodeRequest);
    return ResponseEntity.ok(new AbstractResponse("Verify code successfully", Optional.empty()));
  }
}
