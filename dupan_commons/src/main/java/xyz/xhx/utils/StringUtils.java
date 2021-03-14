package xyz.xhx.utils;

import cn.hutool.json.JSONObject;
import xyz.xhx.entity.FileCommons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtils {

    /**
     * 获取主机，协议，端口号
     */
    public static String getHost(String path) {
        int index = path.lastIndexOf("/");
        String newPath = path.substring(0,index);
        int index1 = newPath.lastIndexOf("/");
        newPath = path.substring(0,index1+1);
//        System.out.println(newPath);
        return newPath;
    }

    public static List<FileCommons> getFileListUtil(JSONObject jsonObject) {
        List<Map<String,Object>> listJson = (List<Map<String, Object>>) jsonObject.get("list");
        List<FileCommons> list = new ArrayList<FileCommons>();
        int id = 1;
        for (Map<String, Object> map : listJson) {
            Integer isdir = (Integer) map.get("isdir");
            String size = map.get("size").toString();
            Long fs_id = (Long) map.get("fs_id");
            String path = (String) map.get("path");
            String server_filename = (String) map.get("server_filename");

            FileCommons commons = new FileCommons();
            commons.setId(id);
            commons.setFs_id(fs_id);
            commons.setPath(path);
            commons.setServer_filename(server_filename);
            commons.setSize(size);
            commons.setIsdir(isdir);
            if (isdir == 0) {
                commons.setType("文件");
                commons.setHasChildren(false);
            } else {
                commons.setType("文件夹");
                commons.setHasChildren(true);
            }

            list.add(commons);

            id++;
        }

        return list;
    }

}
