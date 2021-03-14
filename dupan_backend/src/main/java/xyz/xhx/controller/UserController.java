package xyz.xhx.controller;

import cn.hutool.cron.CronUtil;
import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xhx.entity.*;
import xyz.xhx.service.DupanService;
import xyz.xhx.utils.BaiduUtils;
import xyz.xhx.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DupanService dupanService;

    @RequestMapping("/auth")
    public String login(HttpServletRequest request) {
        // http://localhost:8080/user/auth.do
        StringBuffer url = request.getRequestURL();
        // http://localhost:8080/
        String host = StringUtils.getHost(url.toString());
        String rollbackUri = host + "user/rollback.do";
        BaiduApplication.rollbackUri = rollbackUri;

        String authorizationUri = BaiduUtils.getAuthorizationUrl();
        return "redirect:" + authorizationUri;
    }

    @RequestMapping("rollback")
    public String rollback(String code) {
        String accessToken = BaiduUtils.getAccessToken(code);

        BaiduApplication.accessToken = accessToken;

        return "redirect:../pages/main.html";
    }

    @RequestMapping("info")
    @ResponseBody
    public Object getUserInfo() {

        JSONObject userJson = dupanService.getUserInfo();
        if (userJson != null && "succ".equalsIgnoreCase((String) userJson.get("errmsg"))) {
            // errmsg = succ 数据响应成功
            return new Result(true, "获取用户信息成功", userJson);

        }
        return new Result(false, "获取用户信息失败");

    }

    @RequestMapping("findFriends")
    @ResponseBody
    public Result findFriends() {
        JSONObject jsonObject = dupanService.findFriends();

        return BaiduUtils.errorCodeHandle(jsonObject);
    }

    @RequestMapping("storeData")
    @ResponseBody
    public Result storeData() {
        JSONObject jsonObject = dupanService.storeData();

        return BaiduUtils.errorCodeHandle(jsonObject);
    }

    @RequestMapping("getFiles")
    @ResponseBody
    public Result getFiles(@RequestBody Map<String,String> map) {
        String path = map.get("path");
//        System.out.println(path);
        if (path == null || "{}".equals(path)) {
            path = "/";
        }
        JSONObject jsonObject = dupanService.getFiles(path);
        if ((Integer) jsonObject.get("errno") == 0) {
            List<FileCommons> list = StringUtils.getFileListUtil(jsonObject);
            return new Result(true, "获取文件列表成功", list);


        }
        return BaiduUtils.errorCodeHandle(jsonObject);
    }

    @RequestMapping("jobSetting")
    @ResponseBody
    public Result startJob(String method) {
        try {
            CronUtil.setMatchSecond(true);
            if ("start".equals(method)) {
                CronUtil.start(false);
                return new Result(true, "开始自动发送消息成功");
            } else if ("stop".equals(method)) {
                CronUtil.stop();

                return new Result(true, "停止自动发送消息成功");
            } else {
                return new Result(false, "指令错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "未知错误，请联系管理员");
        }
    }

    @RequestMapping("getAutoKey")
    @ResponseBody
    public Result getAutoKey() {
        List<AutoKeyCommons> list = null;
        try {
            list = dupanService.getAutoKey();
            if (list != null) {
                return new Result(true,"查询自动配置成功",list);
            }
            return new Result(false,"查询自动配置失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询自动配置失败");
        }

    }

    @RequestMapping("deleteAutoKeyById")
    @ResponseBody
    public Result deleteAutoKeyById(String idStr) {
        int id = -1;
        if (idStr != null) {
            id =   Integer.parseInt(idStr);
        }
        try {
            dupanService.deleteAutoKeyById(id);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");

        }


    }

    @RequestMapping("addAutoKey")
    @ResponseBody
    public Result addAutoKey(@RequestBody AutoKeyCommons autoKey) {
        try {
            dupanService.addAutoKey(autoKey);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }


    }

    @RequestMapping("editAutoKey")
    @ResponseBody
    public Result editAutoKey(@RequestBody AutoKeyCommons autoKey) {
        try {
            dupanService.editAutoKey(autoKey);
            return new Result(true,"编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }


    }


}
