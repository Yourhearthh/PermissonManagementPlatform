package com.debug.pmp.server.controller;

import com.debug.pmp.common.response.BaseResponse;
import com.debug.pmp.common.response.StatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author baoguangyu
 * @Date 2021/5/30 9:49
 * @Version 1.0
 */
@RestController
public class SysLoginController extends AbstractController {


    /**
     * 登录
     * @param username
     * @param password
     * @param captcha
     * @return
     */
    @RequestMapping(value = "/sys/login",method = RequestMethod.POST)
    public BaseResponse login(String username, String password, String captcha) {
        log.info("用户名：{} 密码：{} 验证码:{}", username, password, captcha);
        return new BaseResponse(StatusCode.Success);
    }

}
