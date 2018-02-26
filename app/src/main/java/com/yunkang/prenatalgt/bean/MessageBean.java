package com.yunkang.prenatalgt.bean;

import java.io.Serializable;

/**
 * Author：HM $ on 18/1/24 14:14
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class MessageBean implements Serializable{
    private String content;
    private UserBean user;
    private String time;

    public MessageBean() {
    }

    public MessageBean(String content, UserBean user, String time) {
        this.content = content;
        this.user = user;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
