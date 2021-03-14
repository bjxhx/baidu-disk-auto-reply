package xyz.xhx.entity;

public class BaiduApplication {
    public static String apiKey = "sdUP7QlQFhXRyZ1roGn0RCLkHk861LGX";
    public static String secretKey = "N25lXmTdnTb957qHfx9auSiGMaE86uKv";
    public static String rollbackUri = "oob";
    public static String accessToken = "121.e9c2a177cd1f0fa218f0c418943556ab.YgLxHoC02nIp6VV_sw1kcJ6l5wmFMYWDT5d-cYe.0vWn9Q";
    public static String getFriendsUrl = "https://pan.baidu.com/mbox/relation/getfollowlist?access_token=";
    public static String getFriendsMsgUrl = "https://pan.baidu.com/mbox/msg/unreadsession?access_token=";
    public static String sendMsgUrl = "https://pan.baidu.com/mbox/msg/send?access_token=";
    public static String userInfoUrl = "https://pan.baidu.com/rest/2.0/xpan/nas?method=uinfo&access_token=";
    public static String authorizationUrl = "http://openapi.baidu.com/oauth/2.0/authorize?response_type=code&scope=basic,netdisk";
    public static String getAccessTokenUrl = "https://openapi.baidu.com/oauth/2.0/token?" +
            "grant_type=authorization_code&" +
            "client_id=sdUP7QlQFhXRyZ1roGn0RCLkHk861LGX&" +
            "client_secret=N25lXmTdnTb957qHfx9auSiGMaE86uKv&" +
            "redirect_uri=http://localhost:8080/user/rollback.do&" +
            "code=";
    public static String storeDataUrl = "https://pan.baidu.com/api/quota?access_token=";
    public static String filesUrl = "https://pan.baidu.com/rest/2.0/xpan/file?method=list&access_token=";
    public static String getUnReadMessageUrl = "https://pan.baidu.com/mbox/msg/unreadsession?access_token=";


}
