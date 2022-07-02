package com.lrc.store.service;

import com.lrc.store.entity.Order;

public interface IOrderService {
    Order createOrder(Integer aid, Integer uid, String username, Integer[] cids);
}
