package com.example.study308.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {
  private long id;
  private String writer;
  private String content;
  private String createdAt;
}