package com.example.jar.model.domain;
import lombok.*; // 어노테이션자동생성
import jakarta.persistence.*; // 기존javax후속버전
@Getter // setter는없음(무분별한변경x)
@Entity // 아래객체와DB 테이블을매핑. JPA가관리
@Table(name = "article") // 테이블이름을지정. 없는경우클래스이름으로설정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부생성자접근방지
public class Article {
@Id // 기본키
@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키1씩증가
@Column(name = "id", updatable = false) // 수정x
private Long id;
@Column(name = "title", nullable = false) // null x
private String title = "";
@Column(name = "content", nullable = false)
private String content = "";
@Builder // 생성자에빌더패턴적용(불변성)
public Article(String title, String content){
this.title= title;
this.content= content;
}
public void update(String title, String content) {
this.title = title;
this.content = content;
}
}