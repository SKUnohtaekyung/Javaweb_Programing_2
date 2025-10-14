package com.example.jar.model.service;
import lombok.*; // 어노테이션자동생성
import com.example.jar.model.domain.Article;
@NoArgsConstructor // 기본생성자추가
@AllArgsConstructor // 모든필드값을파라미터로받는생성자추가
@Data// getter, setter, toString, equals등자동생성
public class AddArticleRequest{
private String title;
private String content;
public Article toEntity(){ // Article 객체생성
return Article.builder()
.title(title)
.content(content)
.build();
}
}