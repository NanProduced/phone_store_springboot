package com.nan.service.Impl;

import com.nan.entity.BuyerAddress;
import com.nan.form.AddressForm;
import com.nan.repository.BuyerAddressRepository;
import com.nan.service.AddressService;
import com.nan.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private BuyerAddressRepository buyerAddressRepository;

    @Override
    public List<AddressVO> findAll() {
        List<AddressVO> AddressVOList = buyerAddressRepository.findAll().stream()
                .map(e -> new AddressVO(
                        e.getAddressId(),
                        e.getAreaCode(),
                        e.getBuyerName(),
                        e.getBuyerPhone(),
                        e.getBuyerAddress()
                )).collect(Collectors.toList());

        return AddressVOList;
    }

    @Override
    public void saveOrUpdate(AddressForm addressForm) {
        BuyerAddress buyerAddress;
        if (addressForm.getId() == null){
            buyerAddress = new BuyerAddress();
        }else {
            buyerAddress = buyerAddressRepository.findById(addressForm.getId()).get();
        }
        buyerAddress.setBuyerName(addressForm.getName());
        buyerAddress.setBuyerPhone(addressForm.getTel());
        StringBuffer stringBuffer = new StringBuffer();
        //拼接省、市、区为详细地址
        stringBuffer.append(addressForm.getProvince())
                .append(addressForm.getCity())
                .append(addressForm.getCounty())
                .append(addressForm.getAddressDetail());
        buyerAddress.setBuyerAddress(stringBuffer.toString());
        buyerAddress.setAreaCode(addressForm.getAreaCode());

        buyerAddressRepository.save(buyerAddress);
    }
}
