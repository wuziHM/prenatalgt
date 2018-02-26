package com.yunkang.prenatalgt.bean;

/**
 * Author：HM $ on 18/2/1 11:24
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 * 孕妇实体类
 */
public class Gravida extends DataBean{

    private String num;
    private String name;
    private int age;
    private String sex;

    public Gravida() {
    }

    public Gravida(String num, String name, int age, String sex) {
        this.num = num;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
