package com.blueeyescloud.ext.devicemaster.clean;

import android.graphics.drawable.Drawable;

/**
 * 清理界面显示的已安装app信息
 */
public class InstalledAppInfo {
    private String packageName;
    private Drawable icon;
    private String appName;
    private String versionName;
    private long size;
    private boolean isSelected;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable  getIcon() {
        return icon;
    }

    public void setIcon(Drawable  icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "InstalledAppInfo{" +
                "packageName='" + packageName + '\'' +
                ", icon=" + icon +
                ", appName='" + appName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", size=" + size +
                ", isSelected=" + isSelected +
                '}';
    }
}
