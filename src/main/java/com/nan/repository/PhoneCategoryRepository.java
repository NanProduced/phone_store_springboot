package com.nan.repository;

import com.nan.entity.PhoneCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneCategoryRepository extends JpaRepository<PhoneCategory,Integer> {

    public PhoneCategory findByCategoryType(Integer categoryType);

}
