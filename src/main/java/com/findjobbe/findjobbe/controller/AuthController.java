package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.LoginRequest;
import com.findjobbe.findjobbe.mapper.request.RegisterRequest;
import com.findjobbe.findjobbe.mapper.request.VerifyCodeRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.mapper.response.LoginResponse;
import com.findjobbe.findjobbe.service.IAccountService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final IAccountService authService;

  @Autowired
  public AuthController(IAccountService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest)
      throws MessagingException {
    AccountDto accountDto = authService.register(registerRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new AbstractResponse("Register successfully", accountDto));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    LoginResponse loginResponse = authService.login(loginRequest);
    return ResponseEntity.ok(new AbstractResponse("Login successfully", loginResponse));
  }

  @PutMapping("/verify/{accountId}")
  public ResponseEntity<?> verifyCode(
      @PathVariable String accountId, @Valid @RequestBody VerifyCodeRequest verifyCodeRequest) {
    authService.verifyCode(accountId, verifyCodeRequest);
    return ResponseEntity.ok(new AbstractResponse("Verify code successfully", null));
  }
}
