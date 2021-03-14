package xyz.xhx.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import xyz.xhx.entity.BaiduApplication;
import xyz.xhx.entity.Result;

public class BaiduUtils {

    /**
     * 获取授权地址URI
     * @return 授权URI
     */
    public static String getAuthorizationUrl() {
        String apiKey = BaiduApplication.apiKey;
        String secretKey = BaiduApplication.secretKey;
        String rollbackUri = BaiduApplication.rollbackUri;
        // http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&scope=basic,netdisk&display=tv&qrcode=1&force_login=1
        String authorizationUrl = BaiduApplication.authorizationUrl + "&client_id=" + apiKey + "&redirect_uri=" + rollbackUri;

        return authorizationUrl;
    }

    /**
     * 获取access_token
     * @param code 授权返回的code，用于获取access_token， 只能使用一次
     * @return access_token
     */
    public static String getAccessToken(String code) {
        String getAccessTokenUrl = BaiduApplication.getAccessTokenUrl + code;
        String body = HttpUtil.get(getAccessTokenUrl);
        JSONObject json = JSONUtil.parseObj(body);
        String accessToken = (String) json.get("access_token");
        return accessToken;
    }


    /**
     * 发送字符串消息
     * @param receiver 接收者Uk
     * @param msg 消息信息
     * @return
     */
    public static String sendStringMsg(String receiver,String msg){
        String url = BaiduApplication.sendMsgUrl;
        url = url + BaiduApplication.accessToken;
        String body = "send_type=3&receiver=%5B"+receiver+"%5D&msg_type=1&msg="+msg;
        return HttpUtil.post(url, body);
    }

    /**
     * 发送文件消息
     * @param receiver 接收者Uk
     * @param fs_ids 文件的fs_ids
     * @return
     */
    public static String sendFileMsg(String receiver,String fs_ids){
        String url = BaiduApplication.sendMsgUrl;
        url = url + BaiduApplication.accessToken;
        String body = "send_type=3&receiver=%5B"+receiver+"%5D&msg_type=2&fs_ids=%5B"+fs_ids+"%5D";
        return HttpUtil.post(url, body);
    }

    /**
     * 百度错误码处理
     * @param jsonObject 百度相应json数据
     * @return
     */
    public static Result errorCodeHandle(JSONObject jsonObject) {
        if (jsonObject != null) {
            if ((Integer)jsonObject.get("errno") == 0) {
                // errno = 0 相应成功数据
                return new Result(true,"成功",jsonObject);
            } else if ((Integer)jsonObject.get("errno") == 2) {
                // errno =2 参数错误
                return new Result(false,"参数错误");
            } else if ((Integer)jsonObject.get("errno") == -6) {
                // errno = -6 身份验证失败
                return new Result(false,"身份验证失败");
            }

        }

        return new Result(false,"失败,请联系管理员");
    }


}
