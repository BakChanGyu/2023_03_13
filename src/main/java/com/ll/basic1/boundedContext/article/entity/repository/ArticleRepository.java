package com.ll.basic1.boundedContext.article.entity.repository;

import com.ll.basic1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// Article Repo에서 얘를 다룬다라는 뜻. long은 이 테이블에 기본키 타입이 long이라는것
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // @Repo 안붙여도 상속받기때문에 ㄱㅊ
}
