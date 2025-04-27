package com.findjobbe.findjobbe.controller;

import com.findjobbe.findjobbe.mapper.request.ChatRequest;
import com.findjobbe.findjobbe.mapper.response.AbstractResponse;
import com.findjobbe.findjobbe.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  @Autowired private IChatService chatService;

  public ChatController(IChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/")
  public ResponseEntity<?> chat(@RequestBody ChatRequest request) {
    String reply = chatService.reply(request.getMessage());
    return ResponseEntity.ok(new AbstractResponse("success", reply));
  }
}
