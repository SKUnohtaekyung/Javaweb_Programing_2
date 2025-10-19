package com.example.jar.controller;

import com.example.jar.model.domain.Article;
import com.example.jar.model.service.BlogService;
import com.example.jar.model.service.UpdateArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/article_list")
    public String getArticles(Model model) {
        List<Article> articles = blogService.findAll();
        model.addAttribute("articles", articles);

        return "article_list";
    }

    @GetMapping("/article_edit/{id}") // 게시판링크지정
    public String articleEdit(Model model, @PathVariable Long id) {
        Article article = blogService.findById(id);
        model.addAttribute("article", article);
        model.addAttribute("updateRequest", new UpdateArticleRequest(article.getTitle(), article.getContent()));
        return "article_edit"; // .HTML 연결
    }

    // 게시글 수정을 처리하는 POST 메서드
    @PostMapping("/api/articles/update/{id}")
    public String updateArticle(@PathVariable long id, @ModelAttribute UpdateArticleRequest request) {
        blogService.update(id, request);
        return "redirect:/article_list"; // 수정 후 목록 페이지로 리다이렉트
    }
}