package com.lrc.store.service;

import com.lrc.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private IProductService productService;

    @Test
    public void getById() {
        Product product = productService.findById(10000017);
        System.out.println(product);
    }
}
