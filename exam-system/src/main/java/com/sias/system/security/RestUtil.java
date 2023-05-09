package com.sias.system.security;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sias.commons.base.RestResponse;
import com.sias.commons.enums.SystemCode;
import com.sias.commons.model.SysLoginLog;
import com.sias.commons.utils.AddressUtils;
import com.sias.commons.utils.IpUtils;
import com.sias.commons.utils.ServletUtils;
import com.sias.system.service.SysLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author 123
 */
@Slf4j
@Component
public class RestUtil {

    static  SysLoginLogService sysLoginLogService;
    @Autowired
    public void setSysLoginLogService(SysLoginLogService sysLoginLogService) {
        RestUtil.sysLoginLogService = sysLoginLogService;
    }

    public static void response(HttpServletResponse response, SystemCode systemCode) {
        response(response,systemCode,null);
    }
    public static void response(HttpServletResponse response, SystemCode systemCode, Object content) {
        try {
            RestResponse res = new RestResponse<>(systemCode,content);
            String resStr = JSONUtil.toJsonStr(res);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(resStr);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    public static void saveLoginLog(String username,HttpServletRequest request, SystemCode systemCode) throws IOException {
        SysLoginLog sysLoginLog = new SysLoginLog();
        String header = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(header);
        String browser = ua.getBrowser().toString();
        String os = ua.getOs().toString();
        sysLoginLog.setUsername(username);
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        sysLoginLog.setIp(ip);
        sysLoginLog.setBrowser(browser);
        sysLoginLog.setOs(os);
        String address = AddressUtils.getRealAddressByIP(ip);
        if ("内网IP".equals(address)){
            sysLoginLog.setLocation(address);
        }else {
            JSONObject jsonObject = JSON.parseObject(address);
            sysLoginLog.setLocation((String) jsonObject.get("addr"));
        }
        switch(systemCode){
            case OK:
                sysLoginLog.setInfo("登录成功");
                sysLoginLog.setStatus(0);
                break;
            default:
                sysLoginLog.setStatus(1);
                sysLoginLog.setInfo(systemCode.message+",登录失败");
        }
        sysLoginLog.setCreateTime(new Date());
        sysLoginLogService.insertByFilter(sysLoginLog);
    }
}