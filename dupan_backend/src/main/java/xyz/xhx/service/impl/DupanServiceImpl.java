package xyz.xhx.service.impl;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xhx.dao.DuyunDao;
import xyz.xhx.entity.AutoKeyCommons;
import xyz.xhx.entity.BaiduApplication;
import xyz.xhx.entity.SendMsgCommons;
import xyz.xhx.service.DupanService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class DupanServiceImpl implements DupanService {

    @Autowired
    private DuyunDao duyunDao;
    private String accessToken;


    public JSONObject findFriends() {
        String url = BaiduApplication.getFriendsUrl + BaiduApplication.accessToken;
        System.out.println(url);
        String body = HttpUtil.get(url);

        JSONObject json = JSONUtil.parseObj(body);
        return json;
    }

    public void autoSendFile() {
        String url = BaiduApplication.getFriendsMsgUrl + BaiduApplication.accessToken;

        HttpRequest request = HttpUtil.createGet(url);
        HttpResponse response = request.execute();
        String body = response.body();

    }

    public JSONObject sendMsg(SendMsgCommons commons) {
        String msg_type = commons.getMsg_type();
        String msg = commons.getMsg();
        String receiver = commons.getReceiver();
        String fs_id = commons.getFs_id();
        String sendMsgUrl = BaiduApplication.sendMsgUrl + BaiduApplication.accessToken;

        JSONObject jsonObject = null;
        String post = null;
        String body = null;
        if (msg_type != null) {

            if ("1".equals(msg_type) && msg != null) {
                // 发送普通消息
                body = "send_type=3&receiver=%5B" + receiver + "%5D&msg_type=" + msg_type + "&msg=" + msg;
                post = HttpUtil.post(sendMsgUrl, body);
            } else if ("2".equals(msg_type)) {
                // 发送文件
                body = "send_type=3&receiver=%5B" + receiver + "%5D&msg_type=" + msg_type + "&fs_ids=%5B" + fs_id + "%5D";
//                System.out.println(body);
                post = HttpUtil.post(sendMsgUrl, body);
            }
            System.out.println(body);
            jsonObject = JSONUtil.parseObj(post);
        }
        return jsonObject;
    }

    public JSONObject getUserInfo() {
        JSONObject jsonObject = null;
        if (getAccessToken()) {
            String userInfoUrl = BaiduApplication.userInfoUrl + accessToken;
            String body = HttpUtil.get(userInfoUrl);
            jsonObject = JSONUtil.parseObj(body);
            saveUser(jsonObject);

        }
        return jsonObject;
    }

    public void saveUser(JSONObject userJson) {
        if (userJson != null) {
            if ("succ".equalsIgnoreCase((String) userJson.get("errmsg"))) {
                // errmsg = succ 数据响应成功
                String baidu_name = userJson.get("baidu_name").toString();
                String netdisk_name = userJson.get("netdisk_name").toString();
                String baidu_uk = userJson.get("uk").toString();

                // 判断用户是否已存在，已存在不存储
                Map<String, Object> userInfo = duyunDao.getUserInfo(baidu_uk);
                if (userInfo == null) {
                    // 用户不存在
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("baidu_name", baidu_name);
                    map.put("netdisk_name", netdisk_name);
                    map.put("baidu_uk", baidu_uk);

                    duyunDao.saveUser(map);
                }
            }
        }
    }

    public JSONObject storeData() {
        if (getAccessToken()) {
            String url = BaiduApplication.storeDataUrl + BaiduApplication.accessToken;
            String body = HttpUtil.get(url);
            JSONObject jsonObject = JSONUtil.parseObj(body);

            if (jsonObject != null) {
                return jsonObject;
            }
        }
        return null;
    }

    public JSONObject getFiles(String path) {
        if (getAccessToken()) {
            path = URLUtil.encode(path);
            System.out.println(path);
            String url = BaiduApplication.filesUrl + BaiduApplication.accessToken + "&dir=" + path;
            System.out.println(url);
            String body = HttpUtil.get(url);
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject != null) {
                return jsonObject;
            }
        }


        return null;
    }

    public List<AutoKeyCommons> getAutoKey() {
        List<AutoKeyCommons> list = null;
        if (getAccessToken()) {
            list = duyunDao.findAllAutoKey();
        }
        return list;
    }

    public void deleteAutoKeyById(int id) {
        duyunDao.deleteAutoKeyById(id);
    }

    public void addAutoKey(AutoKeyCommons autoKey) {
        duyunDao.addAutoKey(autoKey);
    }

    public void editAutoKey(AutoKeyCommons autoKey) {
        duyunDao.editAutoKey(autoKey);
    }

    public boolean getAccessToken() {
        this.accessToken = BaiduApplication.accessToken;
        if (accessToken != null) {
            return true;
        }

        return false;
    }

}
