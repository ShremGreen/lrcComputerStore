package com.lrc.store.mapper;

import com.lrc.store.entity.Address;
import com.lrc.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictMapperTest {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent() {
        List<District> list = districtMapper.findByParent("86");
        for(District district : list) {
            System.out.println(district);
        }
    }

    @Test
    public void findNameByCode() {
        String name = districtMapper.findNameByCode("421300");
        System.out.println(name);
    }
}