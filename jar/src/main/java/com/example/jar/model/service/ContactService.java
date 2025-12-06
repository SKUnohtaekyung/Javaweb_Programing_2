package com.example.jar.model.service;

import com.example.jar.model.domain.ContactMessage;
import com.example.jar.model.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 문의하기 관련 비즈니스 로직을 처리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;

    /**
     * 파일 첨부 없이 문의 내역을 저장합니다.
     * 
     * @param request 문의 내용 DTO
     * @return 저장된 문의 내역 엔티티
     */
    public ContactMessage save(AddContactRequest request) {
        ContactMessage entity = request.toEntity();
        return contactMessageRepository.save(entity);
    }

    /**
     * 파일 첨부와 함께 문의 내역을 저장합니다.
     * 
     * @param request                 문의 내용 DTO
     * @param attachmentOriginalName  첫 번째 파일 원본 이름
     * @param attachmentSavedName     첫 번째 파일 저장된 이름
     * @param attachmentOriginalName2 두 번째 파일 원본 이름
     * @param attachmentSavedName2    두 번째 파일 저장된 이름
     * @return 저장된 문의 내역 엔티티
     */
    public ContactMessage save(AddContactRequest request,
                               String attachmentOriginalName,
                               String attachmentSavedName,
                               String attachmentOriginalName2,
                               String attachmentSavedName2) {
        ContactMessage entity = request.toEntity(attachmentOriginalName, attachmentSavedName, attachmentOriginalName2, attachmentSavedName2);
        return contactMessageRepository.save(entity);
    }
}
