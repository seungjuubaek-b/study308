package com.example.study308.domain;

import com.example.study308.dto.ArticleForm;

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
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String writer;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Article of (ArticleForm form){
		return Article.builder()
			.title(form.title())
			.content(form.content())
			.writer(form.writer())
			.build();
	}
//	public static Article update (ArticleForm form){
//		return Article.builder()
//			.title(form.title())
//			.content(form.content())
//			.writer(form.writer())
//			.build();
//	}
	public void update(ArticleForm form) {
		this.title = form.title();
		this.content = form.content();
		this.writer = form.writer();
}
}
