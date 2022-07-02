package com.lrc.store.service;

import com.lrc.store.VO.CartVO;

import java.util.List;

public interface ICartService {
    /**
     * 将商品添加到购物车中
     * @param uid 用户uid
     * @param pid 商品pid
     * @param amount 商品数量
     * @param username 作为修改者
     */
    void addProductToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 根据用户uid获取购物车和商品VO类
     * @param uid 用户uid
     * @return VO类
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 增加和减少用户的购物车商品的数量
     * @param cid 购物车cid
     * @param uid 用户uid
     * @param username 作为修改者
     * @return 添加成功后的商品总数
     */
    Integer addNum(Integer cid, Integer uid, String username);
    Integer subNum(Integer cid, Integer uid, String username);

    /**
     * 通过cid数组获取选中的购物车信息
     * @param uid 用户uid
     * @param cids 购物车信息
     * @return VO类
     */
    List<CartVO> getVOByCid(Integer uid, Integer[] cids);
}
