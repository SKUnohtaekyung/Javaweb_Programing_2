package com.example.jar.model.service;

import com.example.jar.model.domain.ContactMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 문의 작성 요청을 전달하는 DTO 클래스입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddContactRequest {

    /**
     * 이름 (필수)
     */
    @NotBlank
    private String name;

    /**
     * 이메일 (필수, 이메일 형식)
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 제목 (필수)
     */
    @NotBlank
    private String subject;

    /**
     * 내용 (필수, 최대 2000자)
     */
    @NotBlank
    @Size(max = 2000)
    private String message;

    /**
     * DTO를 ContactMessage 엔티티로 변환합니다. (파일 없음)
     * 
     * @return ContactMessage 엔티티
     */
    public ContactMessage toEntity() {
        return ContactMessage.builder()
                .name(name)
                .email(email)
                .subject(subject)
                .message(message)
                .build();
    }

    /**
     * DTO를 ContactMessage 엔티티로 변환합니다. (파일 포함)
     * 
     * @param attachmentOriginalName  첫 번째 파일 원본 이름
     * @param attachmentSavedName     첫 번째 파일 저장된 이름
     * @param attachmentOriginalName2 두 번째 파일 원본 이름
     * @param attachmentSavedName2    두 번째 파일 저장된 이름
     * @return ContactMessage 엔티티
     */
    public ContactMessage toEntity(String attachmentOriginalName,
                                   String attachmentSavedName,
                                   String attachmentOriginalName2,
                                   String attachmentSavedName2) {
        return ContactMessage.builder()
                .name(name)
                .email(email)
                .subject(subject)
                .message(message)
                .attachmentOriginalName(attachmentOriginalName)
                .attachmentSavedName(attachmentSavedName)
                .attachmentOriginalName2(attachmentOriginalName2)
                .attachmentSavedName2(attachmentSavedName2)
                .build();
    }
}
