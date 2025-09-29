package com.example.jar.model.service;

import org.springframework.stereotype.Service;
import com.example.jar.model.domain.TestDB;
import com.example.jar.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public TestDB findByName(String name) {
        return testRepository.findByName(name);
    }

    public java.util.List<TestDB> findAll() {
        return testRepository.findAll();
    }
}