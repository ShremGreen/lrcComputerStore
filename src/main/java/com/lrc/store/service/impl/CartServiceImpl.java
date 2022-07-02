package com.lrc.store.service.impl;

import com.lrc.store.VO.CartVO;
import com.lrc.store.entity.Cart;
import com.lrc.store.mapper.CartMapper;
import com.lrc.store.mapper.ProductMapper;
import com.lrc.store.service.ICartService;
import com.lrc.store.service.ex.AccessDeniedException;
import com.lrc.store.service.ex.CartNotFoundException;
import com.lrc.store.service.ex.InsertException;
import com.lrc.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    /*
    由于购物车的业务依赖于购物车和商品的持久层，若通过session获取数据
     */
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProductToCart(Integer uid, Integer pid, Integer amount, String username) {
        //查询购物车是否已经存在
        Cart result = cartMapper.findById(uid, pid);
        Date date = new Date();
        if(result == null) {//新增商品
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setPrice(productMapper.findById(pid).getPrice());
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            Integer rows = cartMapper.insert(cart);

            if(rows != 1) {
                throw new InsertException("未知异常 在 插入数据");
            }
        } else {//更新num值
            Integer num = result.getNum() + amount;

            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);

            if(rows != 1) {
                throw new UpdateException("未知异常 在 更新数据");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        } else if(! result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }

        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());

        if(rows != 1) {
            throw new UpdateException("更新数据失败");
        }

        return num;
    }

    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        } else if(! result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }

        Integer num = result.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());

        if(rows != 1) {
            throw new UpdateException("更新数据失败");
        }

        return num;
    }

    @Override
    public List<CartVO> getVOByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCid(cids);
        Iterator<CartVO> it = list.iterator();
        while(it.hasNext()) {
            CartVO cart = it.next();
            if(! cart.getUid().equals(uid)) list.remove(cart);
        }
        return list;
    }
}
