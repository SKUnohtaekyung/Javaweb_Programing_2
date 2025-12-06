package com.example.jar.controller;

import com.example.jar.model.service.AddContactRequest;
import com.example.jar.model.service.ContactService;
import com.example.jar.model.service.FileStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 문의하기 관련 기능을 처리하는 컨트롤러입니다.
 * 문의 내용 접수 및 파일 업로드를 담당합니다.
 */
@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final FileStorageService fileStorageService;

    /**
     * 문의 내용을 제출받아 저장합니다.
     * 파일은 최대 2개까지 첨부 가능하며, 파일 첨부 정책에 따라 검증을 수행합니다.
     * 
     * @param request            문의 내용 데이터 (이름, 이메일, 제목, 내용 등)
     * @param bindingResult      입력값 검증 결과
     * @param attachments        첨부파일 리스트 (선택 사항)
     * @param redirectAttributes 리다이렉트 시 메시지 전달을 위한 속성
     * @return 결과에 따라 메인 페이지(#board 앵커)로 리다이렉트
     */
    @PostMapping("/api/contacts")
    public String submitContact(@ModelAttribute("contactForm") @Valid AddContactRequest request,
                                BindingResult bindingResult,
                                @RequestParam(value = "attachments", required = false) List<MultipartFile> attachments,
                                RedirectAttributes redirectAttributes) {
        // 입력값 검증 실패 시 에러 메시지 전달 후 즉시 반환
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contactStatus", "error");
            redirectAttributes.addFlashAttribute("contactMessage", "입력값을 확인해 주세요.");
            return "redirect:/#board";
        }

        List<MultipartFile> files = attachments != null ? attachments : new ArrayList<>();
        
        // 파일 개수 검증: 2개 초과 시 에러
        if (files.size() > 2) {
            redirectAttributes.addFlashAttribute("contactStatus", "error");
            redirectAttributes.addFlashAttribute("contactMessage", "파일은 최대 2개까지만 첨부할 수 있습니다.");
            return "redirect:/#board";
        }
        // 파일 개수 검증: 1개만 첨부 시 에러 (정책상 0개 또는 2개여야 하는 경우)
        if (files.size() == 1) {
            redirectAttributes.addFlashAttribute("contactStatus", "error");
            redirectAttributes.addFlashAttribute("contactMessage", "파일은 2개를 모두 선택하거나 첨부하지 않아야 합니다.");
            return "redirect:/#board";
        }

        String savedName = null;
        String originalName = null;
        String savedName2 = null;
        String originalName2 = null;
        
        try {
            if (!files.isEmpty()) {
                MultipartFile first = files.get(0);
                MultipartFile second = files.get(1);
                
                // 두 파일 중 하나라도 비어 있으면 오류 처리
                if (first.isEmpty() || second.isEmpty()) {
                    redirectAttributes.addFlashAttribute("contactStatus", "error");
                    redirectAttributes.addFlashAttribute("contactMessage", "두 파일 모두 선택해 주세요.");
                    return "redirect:/#board";
                }
                
                // 파일 저장 및 원본/저장된 이름 획득
                originalName = first.getOriginalFilename();
                savedName = fileStorageService.store(first);
                originalName2 = second.getOriginalFilename();
                savedName2 = fileStorageService.store(second);
            }
            
            // 문의 내용과 파일 정보 저장
            contactService.save(request, originalName, savedName, originalName2, savedName2);
            
            redirectAttributes.addFlashAttribute("contactStatus", "success");
            redirectAttributes.addFlashAttribute("contactMessage", "문의가 접수되었습니다. 빠르게 답변드리겠습니다.");
            return "redirect:/#board";
            
        } catch (IllegalArgumentException | IOException e) {
            // 파일 업로드 실패 시 에러 처리
            redirectAttributes.addFlashAttribute("contactStatus", "error");
            redirectAttributes.addFlashAttribute("contactMessage", "파일 업로드에 실패했습니다: " + e.getMessage());
            return "redirect:/#board";
        }
    }
}
