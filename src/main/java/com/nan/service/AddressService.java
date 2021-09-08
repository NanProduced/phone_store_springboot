package com.nan.service;

import com.nan.form.AddressForm;
import com.nan.vo.AddressVO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    public List<AddressVO> findAll();
    public void saveOrUpdate(AddressForm addressForm);
}
