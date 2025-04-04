package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.config.CurrentUser;
import com.findjobbe.findjobbe.mapper.dto.AccountDto;
import com.findjobbe.findjobbe.mapper.request.ResetPasswordRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.model.CustomAccountDetails;
import com.findjobbe.findjobbe.service.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
  private final IAccountService accountService;

  @Autowired
  public AccountController(IAccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/")
  public ResponseEntity<?> getMyAccount(@CurrentUser CustomAccountDetails currentUser) {
    return ResponseEntity.ok(
        new AbstractResponse(
            "Get my account successfully",
            new AccountDto(
                accountService.getAccountById(currentUser.getAccount().getId().toString()))));
  }

  @PutMapping("/reset-password")
  public ResponseEntity<?> resetPassword(
      @CurrentUser CustomAccountDetails currentUser,
      @RequestBody @Valid ResetPasswordRequest resetPasswordDto) {
    accountService.resetPassword(
        currentUser.getAccount().getId().toString(),
        resetPasswordDto.getNewPassword(),
        resetPasswordDto.getConfirmPassword());
    return ResponseEntity.ok(new AbstractResponse("Reset password successfully", null));
  }
}
