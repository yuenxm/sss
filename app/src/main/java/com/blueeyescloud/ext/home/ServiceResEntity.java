package com.blueeyescloud.ext.home;

import java.io.Serializable;

public class ServiceResEntity implements Serializable {
    private String keyName;
    private int icon;
    private String name;
    private String description;
    private boolean isLimitedFree;
    private String uri;
    private boolean granted; //是否有权限

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLimitedFree() {
        return isLimitedFree;
    }

    public void setLimitedFree(boolean limitedFree) {
        isLimitedFree = limitedFree;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public boolean getGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }
}
