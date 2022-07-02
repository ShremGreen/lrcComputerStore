package com.lrc.store.controller;

import com.lrc.store.entity.Address;
import com.lrc.store.service.IAddressService;
import com.lrc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_address")
    public JsonResult<Void> insertAddress(HttpSession session, Address address) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addAddress(uid, username, address);
        return new JsonResult<>(FINISH);
    }

    @RequestMapping("")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(FINISH, data);
    }

    //Restful风格的请求编写
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session), getUsernameFromSession(session));
        System.out.println("用户更改默认地址 -> aid = " + aid + "  uid = " + getUidFromSession(session));
        return new JsonResult<>(FINISH);
    }

    @RequestMapping("{aid}/delete_address")
    public JsonResult<Void> deleteAddress(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.deleteAddress(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(FINISH);
    }
}
