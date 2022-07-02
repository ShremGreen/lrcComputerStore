package com.lrc.store.mapper;

import com.lrc.store.entity.Product;

import java.util.List;

public interface ProductMapper {
    /**
     * 热销商品显示
     * @return 商品list集合
     */
    List<Product> findHotList();

    Product findById(Integer id);
}
