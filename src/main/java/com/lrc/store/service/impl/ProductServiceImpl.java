package com.lrc.store.service.impl;

import com.lrc.store.controller.ex.DeleteException;
import com.lrc.store.entity.Address;
import com.lrc.store.entity.Product;
import com.lrc.store.mapper.AddressMapper;
import com.lrc.store.mapper.ProductMapper;
import com.lrc.store.service.IAddressService;
import com.lrc.store.service.IDistrictService;
import com.lrc.store.service.IProductService;
import com.lrc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//收货地址模块 业务层 实现类
@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        //过滤掉非必需信息
        for(Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if(product == null) {
            throw new ProductNotFoundException("商品数据不存在");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }
}
