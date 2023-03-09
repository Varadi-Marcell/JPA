package com.example.JPA.service;

import com.example.JPA.model.Orders;

import java.util.List;

public interface OrderService {

    public void deleteOrderById(Long id);
    public void createOrder(Orders orders);
    public List<Orders> getAllOrder();
    public Orders getOrderById(Long id);

}
