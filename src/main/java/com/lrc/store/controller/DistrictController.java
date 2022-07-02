package com.lrc.store.controller;

import com.lrc.store.entity.District;
import com.lrc.store.service.IDistrictService;
import com.lrc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("district")
@RestController
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService districtService;

    //district开头的请求都被拦截到该方法
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> list = districtService.getByParent(parent);
        return new JsonResult<>(FINISH, list);
    }
}
