package com.good.word.redis.controller;

import com.good.word.redis.common.entity.R;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.service.IUserService;
import com.good.word.redis.valid.ValidRepeated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author wangxianlei
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    /*
     JSR303
     */
    @PostMapping
    public R save(@Validated @RequestBody UserModel userModel) {
        boolean isOk = userService.save(userModel);
        return isOk ? R.ok(userModel) : R.error();
    }

    @PostMapping("/importData")
    public R importData(@ValidRepeated(message = "{validator.repeated.userModel}")
                             @RequestBody List<UserModel> userModelList) {
        return R.ok();
    }

    /*
     mybatis 一级缓存
     */
    @GetMapping("/list")
    public R list() {
        //一级缓存不会生效，因为每次都被commit了不是同一个session:
//        List<UserModel> list = userService.findByAgeGt(30);
//        List<UserModel> list1 = userService.findByAgeGt(30);
//        list.addAll(list1);

        //一级缓存生效都演示代码:
        List<UserModel> list = userService.findByCache1(30);
        return R.ok().data(list);
    }

    @GetMapping("/map")
    public R mapResult() {
        UserModel userModel = new UserModel();
        Map<String, String> mapResult = userService.findMapResult(userModel, 12);
        return R.ok().data(mapResult);
    }

    @GetMapping("/mapKey")
    public R listMapResult() {
        Map<String, UserModel> mapList = userService.mapKey();
        return R.ok().data(mapList);
    }


    /*
     二级缓存可以跨 SESSION，开启后它们默认具有如下特性：
     映射文件中所有的select语句将被缓存
     映射文件中所有的insert、update和delete语句将刷新缓存
     */
    @GetMapping("/listCache2")
    public R listCache2() {
        List<UserModel> list = userService.findByCache2(30);
        return R.ok().data(list);
    }

    @PutMapping
    public void updateUserAge(@RequestBody UserModel userModel) {
        try {
            userService.updateUserAge(userModel, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @PutMapping("/1")
    public void updateUserAge1(@RequestBody UserModel userModel) {
        try {
            userService.updateUserAge(userModel, 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
