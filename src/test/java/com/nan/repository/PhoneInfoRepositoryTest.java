package com.nan.repository;

import com.nan.entity.PhoneInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneInfoRepositoryTest {

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Test
    void test(){
//        List<PhoneInfo> list = phoneInfoRepository.findAll();
        List<PhoneInfo> list = phoneInfoRepository.findAllByCategoryType(1);

        list.forEach(System.out::println);
    }
}