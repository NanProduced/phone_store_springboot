package com.nan.repository;

import com.nan.entity.PhoneInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneInfoRepository extends JpaRepository<PhoneInfo,Integer> {

    public List<PhoneInfo> findAllByCategoryType(Integer categoryType);
}
