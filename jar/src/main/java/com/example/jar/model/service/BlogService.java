package com.example.jar.model.service;

import com.example.jar.model.domain.Article;
import com.example.jar.model.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자자동생성(부분)
public class BlogService{

    private final BlogRepository blogRepository; // 리포지토리선언

    public List<Article> findAll() { // 게시판전체목록조회
        return blogRepository.findAll();
    }

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}