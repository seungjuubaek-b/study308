package com.example.study308.mapper;

import com.example.study308.dto.ArticleResponse;
import com.example.study308.dto.CommentResponse;
import com.example.study308.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ArticleMapper {
  // 다재다능한 검색 메서드입니다.
  List<ArticleResponse> searchArticles(SearchDto searchDto);

  // 🚀 새 메뉴: ID 번호를 주면 게시글 1개만 쏙 찾아오는 기능
  ArticleResponse getArticleById(long id);

  // 🚀 새 메뉴: 게시글 정보를 주면 DB에 '저장(Insert)' 해라!
  void insertArticle(com.example.study308.domain.Article article);

  // 🚀 댓글 저장 명령어
  void insertComment(@org.apache.ibatis.annotations.Param("articleId") long articleId,
                     @org.apache.ibatis.annotations.Param("writer") String writer,
                     @org.apache.ibatis.annotations.Param("content") String content);

  // 🚀 특정 게시글의 댓글들만 전부 가져오는 명령어
  List<CommentResponse> getCommentsByArticleId(long articleId);

  // 🚀 게시글 수정 명령어
  void updateArticle(@org.apache.ibatis.annotations.Param("id") long id,
                     @org.apache.ibatis.annotations.Param("title") String title,
                     @org.apache.ibatis.annotations.Param("content") String content);

  // 🚀 게시글 삭제 명령어
  void deleteArticle(long id);

  // 🚀 [에러 해결용] 게시글을 지우기 전에, 그 글에 달린 댓글부터 싹 지우는 명령어
  void deleteCommentsByArticleId(long articleId);

  // 🚀 [새 기능] 특정 댓글 1개 삭제 명령어
  void deleteComment(long id);

  // 🚀 [새 기능] 특정 댓글 1개 수정 명령어
  void updateComment(@org.apache.ibatis.annotations.Param("id") long id,
                     @org.apache.ibatis.annotations.Param("content") String content);
}