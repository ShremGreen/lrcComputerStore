package com.lrc.store.service.impl;

import com.lrc.store.controller.ex.DeleteException;
import com.lrc.store.entity.Address;
import com.lrc.store.mapper.AddressMapper;
import com.lrc.store.service.IAddressService;
import com.lrc.store.service.IDistrictService;
import com.lrc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//收货地址模块 业务层 实现类
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer MaxAddress = 20;

    @Override
    public void addAddress(Integer uid, String username, Address address) {
        //查询收货地址信息是否大于20
        Integer count = addressMapper.countAddress(uid);
        if(count >= MaxAddress) {
            throw new AddressCountLimitException("收货地址不能超过20条");
        }

        //设置信息
        address.setUid(uid);
        Integer isDelete = count == 0 ? 1 : 0;
        address.setIsDefault(isDelete);
        address.setCreatedTime(new Date());
        address.setCreatedUser(username);
        address.setModifiedTime(new Date());
        address.setModifiedUser(username);

        //补全地址的省市区信息
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        //插入收货地址
        Integer row = addressMapper.insertAddress(address);
        if(row != 1) {
            throw new InsertException("未知错误 在 新建收货地址");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        //由于address对象的数据体量较大，不利于快速传输，所以重新定义并缩小数据
        for(Address address : list) {
            //address.setAid(null);
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
            address.setIsDefault(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //查询aid并返回结果
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        } else if(! result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        //先将所有的收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if(rows < 1) {
            throw new UpdateException("未知的异常 在 更新数据");
        }

        //设置用户的某条地址为默认收货地址
        rows = addressMapper.updateDefaultByUid(aid, username, new Date());
        if(rows != 1) {
            throw new UpdateException("未知的异常 在 更新数据");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if(result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        } else if(! result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        //删除数据
        Integer row = addressMapper.deleteByAid(aid);
        if(row != 1) {
            throw new DeleteException("未知异常 在 删除数据");
        }

        //统计数据个数
        Integer count = addressMapper.countAddress(uid);
        if(count == 0) return;

        //找到最新数据
        if(result.getIsDefault() == 1) {
            Address address = addressMapper.findLastModified(uid);
            row = addressMapper.updateDefaultByUid(address.getAid(), username, new Date());
            if(row != 1) {
                throw new UpdateException("未知异常 在 更新数据");
            }
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if(address == null) {
            throw new AddressNotFoundException("收货地址不存在");
        } else if(! address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }
}
