package com.example.jar.controller;

import com.example.jar.model.domain.Article;
import com.example.jar.model.service.AddArticleRequest;
import com.example.jar.model.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BlogRestController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Void> addArticle(@ModelAttribute AddArticleRequest request) {
        blogService.save(request);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/article_list"))
                .build();
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

}
