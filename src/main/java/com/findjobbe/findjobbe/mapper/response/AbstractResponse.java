package com.findjobbe.findjobbe.mapper.response;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AbstractResponse {
  String message;
  Object data;
}
