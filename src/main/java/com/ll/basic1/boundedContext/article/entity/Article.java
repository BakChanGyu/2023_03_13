package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/** 디비 DTO인듯 */
@Entity
public class Article {
    @Id // 메인 키라는 뜻
    private long id;
    private String title;
    private String body;
}
