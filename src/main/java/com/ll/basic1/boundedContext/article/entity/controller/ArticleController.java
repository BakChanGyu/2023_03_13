package com.ll.basic1.boundedContext.article.entity.controller;

import com.ll.basic1.base.RsData;
import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.entity.repository.ArticleRepository;
import com.ll.basic1.boundedContext.article.entity.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor // 필드 중에서 final 붙은것에 대해서만 생성자 만들어줌.
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/write")
    @ResponseBody
    public RsData write(String title, String body) {
        if (title == null || title.trim().length() == 0) {
            return RsData.of("F-1", "title이 null입니다.");
        }
        if (body == null || body.trim().length() == 0) {
            return RsData.of("F-1", "body가 null입니다.");
        }
        Article createArticle = articleService.write(title, body);

        return RsData.of("S-1", "1번 글이 생성되었습니다.", createArticle);
    }
}
