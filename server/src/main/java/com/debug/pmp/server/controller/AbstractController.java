package com.debug.pmp.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @Author baoguangyu
 * @Date 2021/5/30 9:48
 * @Version 1.0
 */
@Controller
public abstract class AbstractController {
    //日志
    protected Logger log= LoggerFactory.getLogger(getClass());
}
