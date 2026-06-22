package com.example.study308.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {

  @Id // 이 표의 주민번호(고유 키)
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 1, 2, 3... 번호 자동 생성
  private long id;

  @Column(nullable = false) // 빈칸 절대 금지
  private String content; // 댓글 내용

  @Column(nullable = false)
  private String writer; // 댓글 작성자

  // 🌟 핵심: 게시글(Article)과의 연결 고리!
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id") // DB에 만들어질 연결 기둥(컬럼) 이름
  private Article article;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt; // 작성 시간

  @LastModifiedDate
  private LocalDateTime updatedAt; // 수정 시간
}