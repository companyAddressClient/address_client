package com.xyf.AddressList.bean;

import java.io.Serializable;

/**
 * Created by shxiayf on 2015/12/9.
 */
public class UpdateRequestBean implements Serializable{

    private String xlsVersion;
    private String packageName;
    private String appVersion;

    public void setXlsVersion(String xlsVersion) {
        this.xlsVersion = xlsVersion;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getXlsVersion() {
        return xlsVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAppVersion() {
        return appVersion;
    }
}
