package iunsuccessful.demo.shiro.session.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 首页测试
 * Created by LiQZ on 2017/3/22.
 */
@Controller
public class IndexController {

    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private HttpSession session;

    /**
     * session type of class org.apache.catalina.connector.RequestFacade
     * session type of org.apache.catalina.connector.RequestFacade@2886564
     *
     * request type of class org.apache.shiro.web.servlet.ShiroHttpServletRequest
     * request type of org.apache.shiro.web.servlet.ShiroHttpServletRequest@68b71e07
     */
    @ResponseBody
    @RequestMapping("index")
    public String index(HttpServletRequest request) {
        logger.debug("request type of {}", request.getClass());
        logger.debug("request type of {}", request);
        session.setAttribute("hello", "world!");
        return "Hello world!";
    }

    @ResponseBody
    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        logger.info("this is home");
        return "Hello home!";
    }

}
