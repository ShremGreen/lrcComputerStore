package com.lrc.store.mapper;

import com.lrc.store.entity.Cart;
import com.lrc.store.entity.Order;
import com.lrc.store.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(5);
        order.setRecvName("jkl");
        order.setRecvPhone("123456789");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000004);
        orderItem.setTitle("笔记本");
        orderMapper.insertOrderItem(orderItem);
    }
}
