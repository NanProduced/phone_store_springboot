package com.nan.service;

import com.nan.vo.DataVO;
import com.nan.vo.PhoneInfoVO;
import com.nan.vo.SpecsPackageVO;

import java.util.List;

public interface PhoneService {
    public DataVO findDataVO();
    public List<PhoneInfoVO> findPhoneInfoVOByCategoryType(Integer categoryType);
    public SpecsPackageVO findSpecsByPhoneId(Integer phoneID);
    public void subStock(Integer specsId, Integer quantity);
}
