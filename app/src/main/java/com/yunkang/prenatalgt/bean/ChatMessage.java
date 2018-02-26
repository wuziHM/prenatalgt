package com.yunkang.prenatalgt.bean;

/**
 * Author：HM $ on 18/1/25 10:38
 * E-mail：359222347@qq.com
 * <p>
 * use to...聊天信息
 */
public class ChatMessage {

    private int type;  //0 收到的消息  1发出去的消息

    private UserBean userBean;
    private String content;

    public ChatMessage(int type, UserBean userBean, String content) {
        this.type = type;
        this.userBean = userBean;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
