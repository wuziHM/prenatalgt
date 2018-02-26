package com.yunkang.prenatalgt.bean;

import java.io.Serializable;

/**
 * Author：HM $ on 18/1/24 14:15
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class UserBean implements Serializable {
    private String name;
    private int head;

    public UserBean(String name, int head) {
        this.name = name;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
