package com.findjobbe.findjobbe.payload.response;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AbstractResponse {
  String message;
  Optional<Object> data;
}
