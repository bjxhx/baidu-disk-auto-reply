package xyz.xhx.job;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.sql.Condition;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sun.istack.internal.NotNull;
import com.sun.tools.javac.util.Log;
import org.springframework.stereotype.Controller;
import xyz.xhx.entity.BaiduApplication;
import xyz.xhx.entity.SendMsgCommons;
import xyz.xhx.service.impl.DupanServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class AutoMsg {


    private SendMsgCommons commons = null;

    public void getAutoKey(String param, String uk) {
        try {
            List<Entity> query = Db.use().findBy("t_autokey", new Condition("code", param));
            if (!query.isEmpty()) {
//                System.out.println("查询到结果："+query);
                for (Entity entity : query) {
                    Integer count = entity.getInt("count");
                    String fsId = entity.getStr("fsId");
                    String code = entity.getStr("code");
                    if (count > 0 && StrUtil.isNotEmpty(fsId)) {
                        commons = new SendMsgCommons();
                        commons.setMsg_type("2");
                        commons.setReceiver(uk);
                        commons.setFs_id(fsId);
                        JSONObject jsonObject = sendMsg(commons);
                        count--;

                        setCount(code, count);
                        break;
                    }else {
                        commons = new SendMsgCommons();
                        commons.setReceiver(uk);
                        commons.setMsg_type("1");
                        commons.setMsg("抱歉，无法理解！！");
                        sendMsg(commons);
                    }
                }
                setMessageAsRead(uk);



            } else {
                commons = new SendMsgCommons();
                commons.setReceiver(uk);
                commons.setMsg_type("1");
                commons.setMsg("抱歉，无法理解！！");
                sendMsg(commons);
                setMessageAsRead(uk);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void setMessageAsRead(String uk) {
        String url = "https://pan.baidu.com/mbox/msg/sessioninfo?access_token="
                + BaiduApplication.accessToken;
        String paramBody = "type=1&to_uk=" + uk;

        String post = HttpUtil.post(url, paramBody);
        System.out.println(post);

        if (StrUtil.isNotEmpty(post)) {
            JSONObject jsonObject = JSONUtil.parseObj(post);

            Object records = jsonObject.get("records");

            JSONObject obj = JSONUtil.parseObj(records);
            List<Object> list = (List<Object>) obj.get("list");

            if (!list.isEmpty()) {

                Object o = list.get(0);// 获取第一个位置消息

                JSONObject obj1 = JSONUtil.parseObj(o);

                Object mtimeObj = obj1.get("mtime");
                String mtime = mtimeObj.toString();

                String setReadUrl = "https://pan.baidu.com/mbox/msg/setread?access_token="
                        + BaiduApplication.accessToken;
                String setReadBody = "last_msg_time=" + mtime + "&from_uk=" + uk;

                HttpUtil.post(setReadUrl, setReadBody);
                System.out.println("设置消息为已读成功！！！");
            }

        } else {
            System.out.println("设置消息为已读失败！！！");
        }


    }

    private void setCount(String code, Integer count) {
        try {
            Db.use().update(
                    Entity.create().set("count", count),
                    Entity.create("t_autokey").set("code", code)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCommons() {
        commons = null;

        String unreadMessageUrl = BaiduApplication.getUnReadMessageUrl + BaiduApplication.accessToken;

        String body = HttpUtil.get(unreadMessageUrl);

        JSONObject jsonObject = JSONUtil.parseObj(body);


        Integer errno = jsonObject.getInt("errno");

        if (errno != null && errno == 0) {

            List<Map<String, Object>> list = (List<Map<String, Object>>) jsonObject.get("records");

            if (!list.isEmpty()) {

                for (Map<String, Object> map : list) {

                    String uk = String.valueOf(map.get("uk"));
                    String msg = String.valueOf(map.get("msg"));

                    if (StrUtil.isNotEmpty(msg)) {
                        getAutoKey(msg, uk);

                    } else {
                        commons = new SendMsgCommons();
                        commons.setReceiver(uk);
                        commons.setMsg_type("1");
                        commons.setMsg("抱歉，无法理解！！");
                        sendMsg(commons);
                        setMessageAsRead(uk);
                    }

                }
            } else {
                System.out.println("无新消息,不做任何事");
            }

        }


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
//            System.out.println(body);
            jsonObject = JSONUtil.parseObj(post);
        }
        return jsonObject;
    }

}
