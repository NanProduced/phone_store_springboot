package com.nan.service.Impl;

import com.nan.entity.PhoneInfo;
import com.nan.service.PhoneService;
import com.nan.vo.DataVO;
import com.nan.vo.PhoneInfoVO;
import com.nan.vo.SpecsPackageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneServiceImplTest {

    @Autowired
    private PhoneService phoneService;

    @Test
    void Test(){
//        DataVO dataVO = phoneService.findDataVO();
//        List<PhoneInfoVO> list = phoneService.findPhoneInfoVOByCategoryType(1);
//        SpecsPackageVO specsPackageVO = phoneService.findSpecsByPhoneId(1);
        phoneService.subStock(2,5);
        int i =1;
    }




}