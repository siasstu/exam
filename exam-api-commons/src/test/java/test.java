import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-18 20:33:31
 */
public class test {
  public static void main(String[] args) throws IOException {

    // BufferedReader in;
    // StringBuilder result = new StringBuilder();
    //
    // URL realUrl = new URL("http://whois.pconline.com.cn/ipJson.jsp?json=true");
    // URLConnection connection = realUrl.openConnection();
    // connection.setRequestProperty("accept", "*/*");
    // connection.setRequestProperty("connection", "Keep-Alive");
    // connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    // in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
    // String line;
    // while ((line = in.readLine()) != null) {
    //   result.append(line);
    // }
    // JSONObject entries = JSON.parseObject(result.toString());
    // String pro = entries.getString("pro");
    // String city = entries.getString("city");
    // System.out.println(pro);
    // System.out.println(city);



  }

}
