package com.my.demo.wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
@Controller
public class BaseController {

    @ModelAttribute
    public void init(ModelMap model) {

    }

    /**
     * 获取session
     *
     * @return HttpSession.
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取请求头部信息
     *
     * @param request
     * @return
     */
    protected String getHeaderInfo(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        StringBuilder headInfo = new StringBuilder();
        if (headers != null && headers.hasMoreElements()) {
            String headElement = null;
            while (headers.hasMoreElements()) {
                headElement = headers.nextElement();
                headInfo.append(headElement).append(":").append(request.getHeader(headElement)).append("\n");
            }
        } else {
        }
        return headInfo.toString();
    }
}
