package com.blueeyescloud.ext.devicemaster.entity;

/**
 * 功能开启与否的配置
 */
public class ExpandEnabledResEntity {
    private boolean master;
    private boolean gps;

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }
}
