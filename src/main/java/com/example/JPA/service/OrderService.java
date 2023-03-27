package com.example.JPA.service;

import com.example.JPA.dto.user.UserPersonalDetailsDto;
import com.example.JPA.dto.OrdersDto;
import com.example.JPA.model.Orders;

import java.util.List;

public interface OrderService {

    public void deleteOrderById(Long id);
    public void createOrder(UserPersonalDetailsDto userPersonalDetailsDto);
    public List<Orders> getAllOrders();
    public Orders getOrderById(Long id);
    public List<OrdersDto> getOrdersByDates(String startDate,String endDate);

}
