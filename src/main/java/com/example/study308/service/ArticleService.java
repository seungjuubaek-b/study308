// src/main/java/com/example/study308/service/ArticleService.java
package com.example.study308.service;

import java.util.List;

import com.example.study308.dto.*;
// 🚀 최신순 정렬을 위해 꼭 필요한 재료입니다!
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.study308.domain.Article;
import com.example.study308.repository.ArticleRepository; // 기존 JPA 문지기
import com.example.study308.mapper.ArticleMapper; // 새로운 MyBatis 문지기

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    // 1. 기존에 쓰던 JPA 창고 문지기 (단순 반복 업무 담당)
    private final ArticleRepository articleRepository;

    // 2. 우리가 새로 고용한 MyBatis 창고 문지기 (복잡한 검색 담당)
    private final ArticleMapper articleMapper;

    // ==========================================
    // ▼ 여기서부터는 JPA가 담당하는 기본 요리법들 ▼
    // ==========================================

    // 전체 목록 조회 (🚀 최신순 정렬 적용 완료!)
    public List<ArticleResponse> findAll() {
        // 번호(id)를 기준으로 내림차순(DESC) 정렬해서 가져옵니다.
        return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
            .map(ArticleResponse::of)
            .toList();
    }

    // 게시글 1개 찾기
    public ArticleResponse findById(long id) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        return ArticleResponse.of(article);
    }

    // 새 게시글 작성
    public ArticleResponse create(ArticleForm request) {
        Article savedArticle = articleRepository.save(Article.of(request));
        return ArticleResponse.of(savedArticle);
    }

    // 게시글 수정
    public ArticleResponse update(long id, ArticleForm form) {
        Article article = articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));

        article.update(form); // 값만 바꿔주면 JPA가 알아서 업데이트 처리함
        return ArticleResponse.of(article);
    }

    // 게시글 삭제 (🚀 댓글 먼저 지우는 안전한 방식으로 복구 완료!)
    public void delete(long id) {
        // 1. MyBatis 문지기를 시켜서 엮여있는 댓글을 확실하게 먼저 싹 지웁니다.
        articleMapper.deleteCommentsByArticleId(id);

        // 2. 그 다음 JPA 로봇에게 게시글 본체를 지우라고 명령합니다.
        articleRepository.deleteById(id);
    }

    // ==============================================
    // ▼ 여기서부터는 MyBatis가 담당하는 고급 검색/댓글 요리법 ▼
    // ==============================================

    // 🚀 다이나믹 검색 로직 (MyBatis Mapper 호출)
    public List<ArticleResponse> searchArticles(SearchDto searchDto) {
        return articleMapper.searchArticles(searchDto);
    }

    // 🚀 댓글 저장 로직
    public void addComment(long articleId, CommentRequest request) {
        articleMapper.insertComment(articleId, request.getWriter(), request.getContent());
    }

    // 🚀 댓글 가져오기 로직
    public List<CommentResponse> getComments(long articleId) {
        return articleMapper.getCommentsByArticleId(articleId);
    }

    // 🚀 댓글 수정 로직
    public void updateComment(long commentId, String content) {
        articleMapper.updateComment(commentId, content);
    }

    // 🚀 댓글 삭제 로직
    public void deleteComment(long commentId) {
        articleMapper.deleteComment(commentId);
    }
}