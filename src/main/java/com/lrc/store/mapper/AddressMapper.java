package com.lrc.store.mapper;

import com.lrc.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

//收货地址持久层接口
public interface AddressMapper {
    /**
     * 插入用户的收货地址数据
     * @param address 收货地址
     * @return 受影响的行数
     */
    Integer insertAddress(Address address);

    /**
     * 获取收货地址的数量（不能超过20），根据用户的uid
     * @param uid 用户uid
     * @return 收货地址数量
     */
    Integer countAddress(Integer uid);

    /**
     * 根据用户的uid查询用户的收货地址数据
     * @param uid 用户uid
     * @return 收货地址list集合
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid获取收货地址
     * @param aid 收货地址id
     * @return 收货地址
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid值修改用户的收货地址为非默认
     * @param uid 用户uid
     * @return 影响行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据用户uid更新用户的默认地址，
     * 与上两个方法封装在一个方法中，因为都是实现更新默认地址的步骤
     * @param aid
     * @return 影响行数
     */
    Integer updateDefaultByUid(@Param("aid") Integer aid, @Param("modifiedUser") String modifiedUser, @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户的aid删除数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 获取最近更新的一条地址数据
     * @param uid 用户uid
     * @return 收货地址
     */
    Address findLastModified(Integer uid);


}
