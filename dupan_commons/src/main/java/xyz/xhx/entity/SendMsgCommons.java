package xyz.xhx.entity;

import java.util.List;

public class SendMsgCommons {
    private String receiver;
    private String msg_type;
    private String msg;
    private String fs_id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getFs_id() {
        return fs_id;
    }

    public void setFs_id(String fs_id) {
        this.fs_id = fs_id;
    }
}
