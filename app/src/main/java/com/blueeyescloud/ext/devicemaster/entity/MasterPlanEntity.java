package com.blueeyescloud.ext.devicemaster.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *  方案对象
 */
public class MasterPlanEntity implements Parcelable {
    private String id;

    @SerializedName("phone_id")
    private String phoneId;

    private String name;

    private AttributeEntity attribute;

    @SerializedName("create_time")
    private String createTime;

    public MasterPlanEntity(){

    }

    protected MasterPlanEntity(Parcel in) {
        id = in.readString();
        phoneId = in.readString();
        name = in.readString();
        attribute = in.readParcelable(AttributeEntity.class.getClassLoader());
        createTime = in.readString();
    }

    public static final Creator<MasterPlanEntity> CREATOR = new Creator<MasterPlanEntity>() {
        @Override
        public MasterPlanEntity createFromParcel(Parcel in) {
            return new MasterPlanEntity(in);
        }

        @Override
        public MasterPlanEntity[] newArray(int size) {
            return new MasterPlanEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttributeEntity getAttribute() {
        if(attribute == null){
            attribute = new AttributeEntity();
        }
        return attribute;
    }

    public void setAttribute(AttributeEntity attribute) {
        this.attribute = attribute;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        MasterPlanEntity planEntity = (MasterPlanEntity) obj;
        return planEntity.getId() == ((MasterPlanEntity) obj).getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(phoneId);
        dest.writeString(name);
        dest.writeParcelable(attribute, flags);
        dest.writeString(createTime);
    }
}
