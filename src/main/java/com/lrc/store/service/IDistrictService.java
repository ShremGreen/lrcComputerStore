package com.lrc.store.service;

import com.lrc.store.entity.District;

import java.util.List;

public interface IDistrictService {
    /**
     * 通过父地址获取子地址List
     * @param parent 父地址代号
     * @return 子地址List
     */
    List<District> getByParent(String parent);

    /**
     * 通过地址代号获取地址名称
     * @param code 地址代号
     * @return 地址名称
     */
    String getNameByCode(String code);
}
