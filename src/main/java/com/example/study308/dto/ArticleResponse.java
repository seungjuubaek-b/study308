// src/main/java/com/example/study308/dto/ArticleResponse.java

package com.example.study308.dto;

import com.example.study308.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
	private Long id;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// 화면에 보여줄 댓글 개수
	private int commentCount;

	public static ArticleResponse of(Article article){
		// 🚀 만약 게시글에 댓글(comments)이 달려있으면 그 개수를 세고, 없으면 0으로 계산합니다.
		int count = (article.getComments() != null) ? article.getComments().size() : 0;

		return ArticleResponse.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.writer(article.getWriter())
				.createdAt(article.getCreatedAt())
				.updatedAt(article.getUpdatedAt())
				.commentCount(count) // 🚀 [여기가 핵심!] 포장할 때 아까 센 댓글 개수도 같이 넣어줍니다!
				.build();
	}
}