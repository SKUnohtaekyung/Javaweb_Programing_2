package com.example.jar.model.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 파일 업로드 및 저장을 처리하는 서비스입니다.
 */
@Service
@Slf4j
public class FileStorageService {

    private final Path rootLocation;

    /**
     * 파일 저장 위치를 설정합니다.
     * application.properties의 file.upload-dir 값을 사용하며, 기본값은 "uploads"입니다.
     * 
     * @param uploadDir 파일 저장 디렉토리 경로
     */
    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    /**
     * 파일을 저장소에 저장합니다.
     * 파일명이 중복될 경우 숫자를 붙여 고유한 이름을 생성합니다.
     * 
     * @param file 업로드할 파일 객체
     * @return 저장된 파일의 고유한 이름
     * @throws IOException 파일 저장 중 오류 발생 시
     * @throws IllegalArgumentException 파일이 비어있거나 이름이 없을 경우
     */
    public String store(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일이 비어 있습니다.");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new IllegalArgumentException("파일 이름이 없습니다.");
        }

        // 저장 디렉토리가 없으면 생성합니다.
        Files.createDirectories(rootLocation);

        // 중복되지 않는 파일명을 생성합니다.
        String uniqueName = resolveUniqueName(originalName);
        Path target = rootLocation.resolve(uniqueName);

        // 파일을 지정된 경로로 이동(저장)합니다.
        file.transferTo(target);
        log.info("Saved file {} as {}", originalName, uniqueName);
        return uniqueName;
    }

    /**
     * 파일명 중복 시 (1), (2)와 같은 숫자를 붙여 고유한 이름을 만듭니다.
     * 
     * @param originalName 원본 파일명
     * @return 중복되지 않는 고유한 파일명
     * @throws IOException 파일 시스템 접근 중 오류 발생 시
     */
    private String resolveUniqueName(String originalName) throws IOException {
        String baseName = originalName;
        String extension = "";

        // 확장자 분리
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex != -1) {
            baseName = originalName.substring(0, dotIndex);
            extension = originalName.substring(dotIndex);
        }

        String candidate = originalName;
        Path candidatePath = rootLocation.resolve(candidate);
        int counter = 1;

        // 같은 이름이 존재하면 숫자를 증가시키며 고유한 이름을 찾습니다.
        while (Files.exists(candidatePath)) {
            candidate = baseName + "(" + counter + ")" + extension;
            candidatePath = rootLocation.resolve(candidate);
            counter++;
        }
        return candidate;
    }
}
