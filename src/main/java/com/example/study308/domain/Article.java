// src/main/java/com/example/study308/domain/Article.java
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
// 🚀 List와 ArrayList를 사용하기 위한 필수 재료(import)입니다!
import java.util.ArrayList;
import java.util.List;

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

	// 🚀 [여기가 원인!] 이 부분이 들어가야 getComments()를 쓸 수 있습니다.
	// 게시글 지울 때 댓글도 같이 지워주는 폭포수(Cascade) 마법 기능
	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

	public static Article of(ArticleForm form) {
		return Article.builder()
				.title(form.title())
				.content(form.content())
				.writer(form.writer())
				.build();
	}

	public void update(ArticleForm form) {
		this.title = form.title();
		this.content = form.content();
		this.writer = form.writer();
	}
}