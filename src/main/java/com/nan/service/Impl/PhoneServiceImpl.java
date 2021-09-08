package com.nan.service.Impl;

import com.nan.entity.PhoneCategory;
import com.nan.entity.PhoneInfo;
import com.nan.entity.PhoneSpecs;
import com.nan.enums.ResultEnum;
import com.nan.exception.PhoneException;
import com.nan.repository.PhoneCategoryRepository;
import com.nan.repository.PhoneInfoRepository;
import com.nan.repository.PhoneSpecsRepository;
import com.nan.service.PhoneService;
import com.nan.utils.PhoneUtils;
import com.nan.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    /**
     * 获取首页数据
     * @return
     */
    @Override
    public DataVO findDataVO() {

        DataVO dataVO = new DataVO();

        //类型
        List<PhoneCategory> phoneCategoryList = phoneCategoryRepository.findAll();

        //java8新特性 stream流
        List<PhoneCategoryVO> phoneCategoryVOList = phoneCategoryList.stream()
                .map(e -> new PhoneCategoryVO(
                        e.getCategoryName(),
                        e.getCategoryType()
                )).collect(Collectors.toList());

        dataVO.setCategories(phoneCategoryVOList);

        //手机
        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(phoneCategoryList.get(0).getCategoryType());

        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVO(
                        e.getPhoneId(),
                        e.getPhoneName(),
                        e.getPhonePrice()+".00",
                        e.getPhoneDescription(),
                        PhoneUtils.createTag(e.getPhoneTag()),
                        e.getPhoneIcon()
                )).collect(Collectors.toList());

        dataVO.setPhones(phoneInfoVOList);

        return dataVO;
    }

    /**
     * 查询手机
     * @param categoryType
     * @return
     */
    @Override
    public List<PhoneInfoVO> findPhoneInfoVOByCategoryType(Integer categoryType) {
        List<PhoneInfo> phoneInfoList = phoneInfoRepository.findAllByCategoryType(categoryType);
        List<PhoneInfoVO> phoneInfoVOList = phoneInfoList.stream()
                .map(e -> new PhoneInfoVO(
                        e.getPhoneId(),
                        e.getPhoneName(),
                        e.getPhonePrice()+".00",
                        e.getPhoneDescription(),
                        PhoneUtils.createTag(e.getPhoneTag()),
                        e.getPhoneIcon()
                )).collect(Collectors.toList());
        return phoneInfoVOList;
    }

    /**
     * 查询手机规格
     * @param phoneID
     * @return
     */
    @Override
    public SpecsPackageVO findSpecsByPhoneId(Integer phoneID) {
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneID).get();
        List<PhoneSpecs> phoneSpecsList = phoneSpecsRepository.findAllByPhoneId(phoneID);

        //tree
        List<PhoneSpecsVO> phoneSpecsVOList = new ArrayList<>();
        List<PhoneSpecsCasVO> phoneSpecsCasVOList = new ArrayList<>();
        PhoneSpecsVO phoneSpecsVO;
        PhoneSpecsCasVO phoneSpecsCasVO;
        for (PhoneSpecs phoneSpecs : phoneSpecsList) {
            phoneSpecsVO = new PhoneSpecsVO();
            phoneSpecsCasVO = new PhoneSpecsCasVO();
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsVO);
            BeanUtils.copyProperties(phoneSpecs,phoneSpecsCasVO);
            phoneSpecsVOList.add(phoneSpecsVO);
            phoneSpecsCasVOList.add(phoneSpecsCasVO);
        }
        TreeVO treeVO = new TreeVO();
        treeVO.setV(phoneSpecsVOList);
        List<TreeVO> treeVOList = new ArrayList<>();
        treeVOList.add(treeVO);

        SkuVO skuVO = new SkuVO();
        Integer price = phoneInfo.getPhonePrice().intValue();
        skuVO.setPrice(price+".00");
        skuVO.setStock_num(phoneInfo.getPhoneStock());
        skuVO.setTree(treeVOList);
        skuVO.setList(phoneSpecsCasVOList);

        SpecsPackageVO specsPackageVO = new SpecsPackageVO();
        specsPackageVO.setSku(skuVO);
        Map<String,String> goods = new HashMap<>();
        goods.put("picture",phoneInfo.getPhoneIcon());
        specsPackageVO.setGoods(goods);

        return specsPackageVO;
    }

    /**
     * 减库存
     * @param specsId
     * @param quantity
     */
    @Override
    public void subStock(Integer specsId, Integer quantity) {
        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(specsId).get();
        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        Integer result = phoneSpecs.getSpecsStock() - quantity;
        if (result < 0){
            log.error("【减库存】库存不足");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }

        phoneSpecs.setSpecsStock(result);
        phoneSpecsRepository.save(phoneSpecs);

        result = phoneInfo.getPhoneStock() - quantity;
        if (result < 0){
            log.error("【减库存】库存不足");
            throw new PhoneException(ResultEnum.PHONE_STOCK_ERROR);
        }

        phoneInfo.setPhoneStock(result);
        phoneInfoRepository.save(phoneInfo);

    }
}
