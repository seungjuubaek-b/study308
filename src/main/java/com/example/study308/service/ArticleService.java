package com.example.study308.service;

import java.util.List;

import com.example.study308.dto.*;
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

    // 전체 목록 조회 (에러 났던 부분 부활!)
    public List<ArticleResponse> findAll() {
        return articleRepository.findAll().stream()
            .map(ArticleResponse::of)
            .toList();
    }

    // 게시글 1개 찾기
    public ArticleResponse findById(long id) {
//        return ArticleResponse.of(
//            articleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found: " + id))
//        );
        // 🚀 [새로운 MyBatis 코드] - 문지기야, 네가 직접 DB 가서 1개 찾아와!
        return articleMapper.getArticleById(id);
    }

    // 새 게시글 작성
    public ArticleResponse create(ArticleForm request) {
//        return ArticleResponse.of(articleRepository.save(Article.of(request)));
        // 🚀 [새로운 MyBatis 코드]
        Article article = Article.of(request); // 1. 프론트에서 온 데이터를 Article 모양으로 바꿈
        articleMapper.insertArticle(article);  // 2. MyBatis 문지기에게 "이거 DB에 넣어!" 하고 시킴
        return ArticleResponse.of(article);    // 3. 안내데스크에 결과물 보고
    }

    // 게시글 수정
    public ArticleResponse update(long id, ArticleForm form) {
        // [기존 JPA 코드 - 이제 안 씀!]
        // Article article = articleRepository.findById(id)
        //     .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        // article.update(form);
        // return ArticleResponse.of(article);

        // 🚀 [새로운 MyBatis 코드] - 문지기야, 네가 직접 DB 가서 수정하고 결과 가져와!
        articleMapper.updateArticle(id, form.title(), form.content());
        return articleMapper.getArticleById(id);
    }

    // 게시글 삭제
    public void delete(long id) {
        // [기존 JPA 코드 - 이제 안 씀!]
        // articleRepository.deleteById(id);

        // 🚀 [새로운 MyBatis 코드] - 문지기야, 이 번호 글 삭제해!
        articleMapper.deleteArticle(id);
    }

    // ==============================================
    // ▼ 여기서부터는 MyBatis가 담당하는 고급 검색 요리법 ▼
    // ==============================================

    // 🚀 다이나믹 검색 로직 (MyBatis Mapper 호출) 🚀
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
}