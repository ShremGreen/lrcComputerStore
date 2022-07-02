package com.lrc.store.mapper;

import com.lrc.store.entity.Cart;
import com.lrc.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(5);
        cart.setPid(10000001);
        cart.setPrice(115L);
        cart.setNum(5);

        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumByCid(1,10,"老李头",new Date());
    }

    @Test
    public void findById() {
        Cart cart = cartMapper.findById(5, 10000001);
        System.out.println(cart);
    }

    @Test
    public void findVOByUid() {
        System.out.println(cartMapper.findVOByUid(5));
    }

    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(5));
    }

    @Test
    public void findVOByCid() {
        Integer[] cid = {1,3,4};
        System.out.println(cartMapper.findVOByCid(cid));
    }
}
