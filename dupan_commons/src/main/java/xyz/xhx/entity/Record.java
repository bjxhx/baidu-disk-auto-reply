package xyz.xhx.entity;

import java.io.Serializable;
import java.util.List;

public class Record implements Serializable {
    private Long  time;
    private Long  uk;
    private Integer  count;
    private String  uname;
    private String  priority_name;
    private String  msg;
    private List<String> file_list;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getUk() {
        return uk;
    }

    public void setUk(Long uk) {
        this.uk = uk;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPriority_name() {
        return priority_name;
    }

    public void setPriority_name(String priority_name) {
        this.priority_name = priority_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getFile_list() {
        return file_list;
    }

    public void setFile_list(List<String> file_list) {
        this.file_list = file_list;
    }
}
