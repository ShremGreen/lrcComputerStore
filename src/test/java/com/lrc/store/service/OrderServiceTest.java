package com.lrc.store.service;

import com.lrc.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {
    @Autowired
    private IOrderService orderService;

    @Test
    public void createOrder() {
        Integer[] cids = {1,2,4};
        Order order = orderService.createOrder(15, 5, "李荣春", cids);
        System.out.println(order);
    }
}
