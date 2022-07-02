package com.lrc.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTest {
    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        //cartService.addProductToCart(5, 10000003, 6, "管理员");
        cartService.addProductToCart(5, 10000001, 20, "管理员");
    }
}
