package xyz.xhx.service;

import cn.hutool.json.JSONObject;
import xyz.xhx.entity.AutoKeyCommons;
import xyz.xhx.entity.SendMsgCommons;

import java.util.List;

public interface DupanService {
    JSONObject findFriends();

    void autoSendFile();

    JSONObject sendMsg(SendMsgCommons commons);

    JSONObject getUserInfo();

    void saveUser(JSONObject jsonObject);

    JSONObject storeData();

    JSONObject getFiles(String path);

    List<AutoKeyCommons> getAutoKey();

    void deleteAutoKeyById(int id);

    void addAutoKey(AutoKeyCommons autoKey);

    void editAutoKey(AutoKeyCommons autoKey);
}
