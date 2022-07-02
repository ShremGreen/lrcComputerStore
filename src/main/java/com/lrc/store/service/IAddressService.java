package com.lrc.store.service;

import com.lrc.store.entity.Address;

import java.util.List;

//收货地址业务层接口
public interface IAddressService {
    /**
     * 新增地址信息
     * @param uid 用户uid
     * @param username 作为修改者信息
     * @param address 地址类信息
     */
    void addAddress(Integer uid, String username, Address address);

    /**
     * 获取uid对应用户的所有地址信息
     * @param uid 用户uid
     * @return 地址信息list
     */
    List<Address> getByUid(Integer uid);

    /**
     * 修改用户的收货地址为默认的收货地址
     * @param aid 收货地址的id值
     * @param uid 用户uid
     * @param username 作为修改者信息
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除用户收货地址数据
     * @param aid 收货地址id
     * @param uid 用户uid
     * @param username 作为修改者
     */
    void deleteAddress(Integer aid, Integer uid, String username);

    /**
     * 获取aid对应的地址信息
     * @param aid
     * @return
     */
    Address getByAid(Integer aid, Integer uid);
}
