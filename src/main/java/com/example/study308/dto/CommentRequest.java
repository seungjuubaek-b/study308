package com.example.study308.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
  private String writer;
  private String content;
}