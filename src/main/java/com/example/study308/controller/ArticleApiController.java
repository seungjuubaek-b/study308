package com.example.study308.controller;

import java.util.List;

import com.example.study308.dto.SearchDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.study308.dto.ArticleForm;
import com.example.study308.dto.ArticleResponse;
import com.example.study308.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> findAllArticles() {
        return articleService.findAll();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleResponse findArticle(@PathVariable long id) {
        return articleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse addArticle(@RequestBody ArticleForm request) {
        return articleService.create(request);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArticleResponse updateArticle(@PathVariable long id,
        @RequestBody ArticleForm request) {
        return articleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable long id) {
        articleService.delete(id);
    }
//    @GetMapping("/search")
//    @ResponseStatus(HttpStatus.OK)
//    public List<ArticleResponse> searchArticles(@RequestParam String keyword) {
//        return articleService.searchAll(keyword);
//    }
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleResponse> searchArticles(@ModelAttribute SearchDto searchDto) {
        return articleService.searchArticles(searchDto);
    }

/// ==========================================
// ▼ 여기서부터는 댓글(Comment) 관련 API 창구 ▼
// ==========================================

// 🚀 1. 댓글 작성 창구 (앞부분 기본 주소 제외!)
@PostMapping("/{articleId}/comments")
public org.springframework.http.ResponseEntity<Void> addComment(
    @PathVariable long articleId,
    @RequestBody com.example.study308.dto.CommentRequest request) {
    articleService.addComment(articleId, request);
    return org.springframework.http.ResponseEntity.ok().build();
}

    // 🚀 2. 댓글 목록 조회 창구 (앞부분 기본 주소 제외!)
    @GetMapping("/{articleId}/comments")
    public org.springframework.http.ResponseEntity<java.util.List<com.example.study308.dto.CommentResponse>> getComments(
        @PathVariable long articleId) {
        java.util.List<com.example.study308.dto.CommentResponse> comments = articleService.getComments(articleId);
        return org.springframework.http.ResponseEntity.ok(comments);
    }
}
