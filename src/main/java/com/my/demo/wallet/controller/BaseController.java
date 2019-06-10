package com.my.demo.wallet.controller;

import com.my.demo.wallet.utils.XMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
public class BaseController {

    private String secret;

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

    protected boolean checkSign(Map<String, String> params) {
        if (StringUtils.isBlank(params.get("sign"))) {
            return false;
        }
        String sign = params.get("sign");
        params.remove("sign");
        params.put("secret", "test");
        Map<String, Object> sortedParams = new TreeMap<String, Object>(params);
        Set<Map.Entry<String, Object>> entrySet = sortedParams.entrySet();
        Map<String, Object> data = new HashMap<>();
        for (Map.Entry<String, Object> param : entrySet) {
            data.put(param.getKey(), param.getValue());
        }
        StringBuffer sb = new StringBuffer();
        XMLUtils.map2Element(data, sb);
        log.debug("sign:{}", sb.toString());
        if (!sign.equals(DigestUtils.md5Hex(sb.toString()))) {
            return false;
        }
        return true;
    }
}
