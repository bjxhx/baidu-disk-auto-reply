package xyz.xhx.test;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.commons.codec.binary.Base32OutputStream;
import org.junit.Before;
import xyz.xhx.entity.BaiduApplication;
import xyz.xhx.utils.BaiduUtils;
import xyz.xhx.utils.StringUtils;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Test {

    @Before
    public void before() {
        BaiduApplication.accessToken = "121.e9c2a177cd1f0fa218f0c418943556ab.YgLxHoC02nIp6VV_sw1kcJ6l5wmFMYWDT5d-cYe.0vWn9Q";
        System.out.println("before执行。。。。");
    }

    @org.junit.Test
    public void Test5() {
        String sendMsgUrl = BaiduApplication.sendMsgUrl + BaiduApplication.accessToken;
        System.out.println(sendMsgUrl);
    }

    @org.junit.Test
    public void Test2() throws IOException {

        // https://openapi.baidu.com/oauth/2.0/token?grant_type=authorization_code&code=CODE&client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&redirect_uri=YOUR_REGISTERED_REDIRECT_URI
        String url = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token=121.e9c2a177cd1f0fa218f0c418943556ab.YgLxHoC02nIp6VV_sw1kcJ6l5wmFMYWDT5d-cYe.0vWn9Q";
        HttpRequest request = HttpUtil.createGet(url);
        HttpResponse httpResponse = request.execute();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","*/*");
        headers.put("Accept-Encoding","gzip, deflate, br");
        headers.put("Connection","keep-alive");
        headers.put("Referer","https://pan.baidu.com/mbox/homepage");
        headers.put("User-Agent","LogStatistic");
        headers.put("X-Requested-With","XMLHttpRequest");
        headers.put("Sec-Fetch-Dest","empty");
        headers.put("Sec-Fetch-Mode","cors");
        headers.put("Sec-Fetch-Site","same-origin");
        headers.put("Host","pan.baidu.com");


        Set<String> keySet = headers.keySet();

     /*   for (String key : keySet) {
            System.out.println("key:"+key);
            List<String> stringList = headers.get(key);
            for (String s : stringList) {
                System.out.println(s);
            }

            System.out.println("====================");

        }*/

        List<HttpCookie> cookies = httpResponse.getCookies();
        String friendsUrl = "https://pan.baidu.com/mbox/relation/getfollowlist?start=1&limit=20&access_token=121.e9c2a177cd1f0fa218f0c418943556ab.YgLxHoC02nIp6VV_sw1kcJ6l5wmFMYWDT5d-cYe.0vWn9Q";

        HttpRequest request1 = HttpUtil.createGet(friendsUrl);
//        request1.addHeaders(headers);
//        request1.cookie(cookies);
        HttpResponse response = request1.execute();
        String body = response.body();


        System.out.println(body);


    }

    @org.junit.Test
    public void Test1() {


        String msg = BaiduUtils.sendFileMsg("3965582225", "200769199585149");


        System.out.println(msg);


    }

    @org.junit.Test
    public void Test3() {
        String url = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token=";
        url = url + BaiduApplication.accessToken;
        String resp = HttpUtil.get(url);
        System.out.println(resp);

    }
    @org.junit.Test
    public void Test4() {


    }


}
