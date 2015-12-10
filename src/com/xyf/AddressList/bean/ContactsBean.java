package com.xyf.AddressList.bean;

import java.io.Serializable;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class ContactsBean implements Serializable {

    private String name;
    private String mobilephone;
    private String workphone;
    private String job;

    public void setName(String name) {
        this.name = name;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getWorkphone() {
        return workphone;
    }

    public String getJob() {
        return job;
    }
}
