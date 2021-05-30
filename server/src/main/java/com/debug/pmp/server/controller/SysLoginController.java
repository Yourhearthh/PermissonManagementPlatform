package com.debug.pmp.server.controller;

import com.debug.pmp.common.response.BaseResponse;
import com.debug.pmp.common.response.StatusCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
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

        try {
            //提交登录
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
            }
        } catch (UnknownAccountException e) {
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return new BaseResponse(StatusCode.AccountPasswordNotMatch);
        }catch (LockedAccountException e) {
            return new BaseResponse(StatusCode.AccountHasBeenLocked);
        }catch (AuthenticationException e) {
            return new BaseResponse(StatusCode.AccountValidateFail);
        }


        return new BaseResponse(StatusCode.Success);
    }

}
