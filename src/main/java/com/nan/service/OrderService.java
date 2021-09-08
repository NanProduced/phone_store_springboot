package com.nan.service;

import com.nan.dto.OrderDTO;
import com.nan.vo.OrderDetailVO;

public interface OrderService {
    public OrderDTO create(OrderDTO orderDTO);
    public OrderDetailVO findOrderDetailByOrderId(String orderId);
    public String pay(String orderId);

}
