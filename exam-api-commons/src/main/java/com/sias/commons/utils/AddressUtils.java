package com.sias.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AddressUtils {
  public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?json=true";
  public static final String UNKNOWN = "XX XX";

  public static String getRealAddressByIP(String ip) throws IOException {
    if (IpUtils.internalIp(ip)) {
      return "内网IP";
    }
    BufferedReader in;
    StringBuilder result = new StringBuilder();
    URL realUrl = new URL(IP_URL);
    URLConnection connection = realUrl.openConnection();
    connection.setRequestProperty("accept", "*/*");
    connection.setRequestProperty("connection", "Keep-Alive");
    connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
    String line;
    while ((line = in.readLine()) != null) {
      result.append(line);
    }
    if ("".equals(result.toString())) {
      return UNKNOWN;
    }
    return result.toString();
  }
}
