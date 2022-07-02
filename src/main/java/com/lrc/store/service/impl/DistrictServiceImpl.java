package com.lrc.store.service.impl;

import com.lrc.store.entity.District;
import com.lrc.store.mapper.DistrictMapper;
import com.lrc.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);

        //注意：在进行网络数据的传输时，为了尽量避免无效数据的传递，可以将无效数据设置为null。
        //一方面节省流量，另一方面提升效率

        for(District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
