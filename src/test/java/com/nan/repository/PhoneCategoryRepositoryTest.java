package com.nan.repository;

import com.nan.entity.PhoneCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneCategoryRepositoryTest {

    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;

    @Test
    void test(){
        List<PhoneCategory> list = phoneCategoryRepository.findAll();
        list.forEach(System.out::println);
    }


}