package com.lrc.store.service;

import com.lrc.store.entity.District;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DistrictServiceTest {
    @Autowired
    private IDistrictService districtService;

    @Test
    public void getByParent() {
        List<District> list = districtService.getByParent("86");

        for(District district : list) {
            System.out.println(district);
        }
    }
}
