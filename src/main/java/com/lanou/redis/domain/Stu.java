package com.lanou.redis.domain;

import java.io.Serializable;

/**
 * Created by dllo on 17/12/28.
 */
public class Stu implements Serializable{
    private int sid;
    private String sname;

    public Stu() {
    }

    public Stu(int sid, String sname) {
        this.sid = sid;
        this.sname = sname;
    }

    public Stu(String sname) {
        this.sname = sname;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                '}';
    }
}
