package com.lrc.store.service;

import com.lrc.store.entity.Product;

import java.util.List;

//热销商品业务层接口
public interface IProductService {
    List<Product> findHotList();

    Product findById(Integer id);
}
