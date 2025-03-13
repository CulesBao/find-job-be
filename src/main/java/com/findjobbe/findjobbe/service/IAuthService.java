package com.findjobbe.findjobbe.service;

import com.findjobbe.findjobbe.payload.request.RegisterRequest;
import com.findjobbe.findjobbe.payload.response.RegisterResponse;

public interface IAuthService {
  RegisterResponse register(RegisterRequest registerRequest);
}
