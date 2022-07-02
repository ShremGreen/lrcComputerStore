package com.lrc.store.mapper;

import com.lrc.store.entity.District;

import java.util.List;

public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 父区域下所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据code代号获取对应的地址名称
     * @param code 地址代号
     * @return 地址名称
     */
    String findNameByCode(String code);
}
