package com.lrc.store.service;

import com.lrc.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTest {
    @Autowired
    private IAddressService addressService;

    @Test
    public void insertAddress() {
        Address address = new Address();
        address.setPhone("11122233344");
        address.setName("Mr.Smith");
        addressService.addAddress(3, "管理员", address);
    }

    @Test
    public void setDefaultAddress() {
        addressService.setDefault(1,5,"管理员");
    }

    @Test
    public void deleteAddress() {
        addressService.deleteAddress(14,24,"管理员");
    }
}
