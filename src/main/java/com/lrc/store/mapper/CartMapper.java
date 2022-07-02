package com.lrc.store.mapper;

import com.lrc.store.VO.CartVO;
import com.lrc.store.entity.Cart;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 添加商品进购物车
     * @param cart 购物车
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid 购物车id
     * @param num 更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据 uid 和 pid 判断购物车数据是否存在
     * @param uid
     * @param pid
     * @return
     */
    Cart findById(Integer uid, Integer pid);

    /**
     * 通过uid获取用户名下的购物车信息
     * @param uid 用户uid
     * @return 购物车和商品信息，VO组装类
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 查询当前购物车信息是否存在
     * @param cid 购物车cid
     * @return 购物车信息
     */
    Cart findByCid(Integer cid);

    /**
     * 获取购物车中勾选的商品信息
     * @param cids 购物车信息cid
     * @return 商品信息
     */
    List<CartVO> findVOByCid(Integer[] cids);
}
