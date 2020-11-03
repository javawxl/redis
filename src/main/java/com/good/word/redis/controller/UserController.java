package com.good.word.redis.controller;

import com.good.word.redis.common.entity.R;
import com.good.word.redis.entity.UserModel;
import com.good.word.redis.exception.BizException;
import com.good.word.redis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxianlei
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public R save(@Validated @RequestBody UserModel userModel) {
        boolean isOk = userService.save(userModel);
        return isOk ? R.ok(userModel) : R.error();
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
