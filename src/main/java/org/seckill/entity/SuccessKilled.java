package org.seckill.entity;

import java.util.Date;

/**
 * Created by pengfei on 2016/10/16.
 */
public class SuccessKilled {

    private long seckilledId ;

    private  long userPhone ;

    private short  state ;

    private Date createTime ;

    private Seckill seckill ;

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public long getSeckilledId() {
        return seckilledId;
    }

    public void setSeckilledId(long seckilledId) {
        this.seckilledId = seckilledId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckilledId=" + seckilledId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
