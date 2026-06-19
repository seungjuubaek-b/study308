package com.example.study308.repository;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study308.domain.Article;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

  List<Article> findByTitleContainingOrContentContainingOrWriterContaining(
      String title, String content, String writer
  );
}
