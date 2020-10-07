package com.blueeyescloud.ext.devicemaster.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 属性字段 实体类
 */
public class AttributeEntity implements Serializable, Parcelable {
    private String manufacturer; //厂商
    private String brand; //品牌
    private String model; //设备型号
    private String serial; //序列号
//    private String systemVersion;//版本号 非服务端传递下来，且不可更改
    private String phonenum; //手机号
    private String androidid;
    private String imei;
    private String imsi;
    private String iccid;
    private String wifiname;//wifi名称
    private String wifimac; //wifi mac地址

    public AttributeEntity(){

    }

    protected AttributeEntity(Parcel in) {
        manufacturer = in.readString();
        brand = in.readString();
        model = in.readString();
        serial = in.readString();
        phonenum = in.readString();
        androidid = in.readString();
        imei = in.readString();
        imsi = in.readString();
        iccid = in.readString();
        wifiname = in.readString();
        wifimac = in.readString();
    }

    public static final Creator<AttributeEntity> CREATOR = new Creator<AttributeEntity>() {
        @Override
        public AttributeEntity createFromParcel(Parcel in) {
            return new AttributeEntity(in);
        }

        @Override
        public AttributeEntity[] newArray(int size) {
            return new AttributeEntity[size];
        }
    };

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

//    public String getSystemVersion() {
//        return systemVersion;
//    }
//
//    public void setSystemVersion(String systemVersion) {
//        this.systemVersion = systemVersion;
//    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAndroidid() {
        return androidid;
    }

    public void setAndroidid(String androidId) {
        this.androidid = androidId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getWifiname() {
        return wifiname;
    }

    public void setWifiname(String wifiname) {
        this.wifiname = wifiname;
    }

    public String getWifimac() {
        return wifimac;
    }

    public void setWifimac(String wifimac) {
        this.wifimac = wifimac;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(manufacturer);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(serial);
        dest.writeString(phonenum);
        dest.writeString(androidid);
        dest.writeString(imei);
        dest.writeString(imsi);
        dest.writeString(iccid);
        dest.writeString(wifiname);
        dest.writeString(wifimac);
    }

    public static class PageResEntity<T> implements Serializable {

        @SerializedName("page_no")
        private int pageNo;

        @SerializedName("page_size")
        private int pageSize;

        @SerializedName("total_count")
        private int totalCount;

        @SerializedName("result")
        private List<T> result;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<T> getResult() {
            return result;
        }

        public void setResult(List<T> result) {
            this.result = result;
        }
    }
}
