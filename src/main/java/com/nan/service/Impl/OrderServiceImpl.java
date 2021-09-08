package com.nan.service.Impl;

import com.nan.dto.OrderDTO;
import com.nan.entity.OrderMaster;
import com.nan.entity.PhoneInfo;
import com.nan.entity.PhoneSpecs;
import com.nan.enums.PayStatusEnum;
import com.nan.enums.ResultEnum;
import com.nan.exception.PhoneException;
import com.nan.repository.OrderMasterRepository;
import com.nan.repository.PhoneInfoRepository;
import com.nan.repository.PhoneSpecsRepository;
import com.nan.service.OrderService;
import com.nan.service.PhoneService;
import com.nan.utils.KeyUtil;
import com.nan.vo.OrderDetailVO;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PhoneService phoneService;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //将前端信息分别创建对象存入数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(orderDTO.getSpecsId()).get();
        if(phoneSpecs == null){
            log.error("【创建订单】规格不存在,phoneSpecs={}",phoneSpecs);
            throw new PhoneException(ResultEnum.SPECS_NOT_EXIST);
        }

        BeanUtils.copyProperties(phoneSpecs,orderMaster);

        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        if(phoneInfo == null){
            log.error("【创建订单】手机不存在,phoneInfo={}",phoneInfo);
            throw new PhoneException(ResultEnum.PHONE_NOT_EXIST);
        }

        BeanUtils.copyProperties(phoneInfo,orderMaster);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(0);
        orderAmount = phoneSpecs.getSpecsPrice().divide(new BigDecimal(100))
                .multiply(new BigDecimal(orderDTO.getPhoneQuantity()))
                .add(orderAmount)
                .add(new BigDecimal(10));
        orderMaster.setOrderAmount(orderAmount);

        //orderId
        orderMaster.setOrderId(KeyUtil.createUniqueKey());
        orderDTO.setOrderId(orderMaster.getOrderId());

        //payStatus
        orderMaster.setPayStatus(PayStatusEnum.UNPAID.getCode());

        orderMasterRepository.save(orderMaster);

        phoneService.subStock(orderDTO.getSpecsId(),orderDTO.getPhoneQuantity());

        return orderDTO;
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @Override
    public OrderDetailVO findOrderDetailByOrderId(String orderId) {
        OrderDetailVO orderDetailVO = new OrderDetailVO();

        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();

        if(orderMaster == null){
            log.error("【查询订单】订单不存在,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }

        BeanUtils.copyProperties(orderMaster,orderDetailVO);
        orderDetailVO.setSpecsPrice(orderMaster.getSpecsPrice().divide(new BigDecimal(100))+".00");

        return orderDetailVO;
    }

    /**
     * 订单支付
     * @param orderId
     * @return
     */
    @Override
    public String pay(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();

        if(orderMaster == null){
            log.error("【支付订单】订单不存在,orderMaster={}",orderMaster);
            throw new PhoneException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(orderMaster.getPayStatus().equals(PayStatusEnum.UNPAID.getCode())){
            orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
            orderMasterRepository.save(orderMaster);
        } else {
            log.error("【支付订单】订单已支付,orderMaster={}",orderMaster);
        }
        return orderId;
    }
}
