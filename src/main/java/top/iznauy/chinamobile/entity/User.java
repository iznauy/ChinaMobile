package top.iznauy.chinamobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@Table
public class User {

    @Id
    @Column
    private String phoneNumber;

    @Column(nullable = false)
    private int extraMessageCount;

    @Column(nullable = false)
    private int extraPhoneCallTime;

    @Column(nullable = false)
    private double extraNativeData;

    @Column(nullable = false)
    private double extraDomesticData;

    @Column(nullable = false)
    private double balance;

    public User() {
    }

    public User(String phoneNumber, int extraMessageCount, int extraPhoneCallTime,
                double extraNativeData, double extraDomesticData, double balance) {
        this.phoneNumber = phoneNumber;
        this.extraMessageCount = extraMessageCount;
        this.extraPhoneCallTime = extraPhoneCallTime;
        this.extraNativeData = extraNativeData;
        this.extraDomesticData = extraDomesticData;
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getExtraMessageCount() {
        return extraMessageCount;
    }

    public void setExtraMessageCount(int extraMessageCount) {
        this.extraMessageCount = extraMessageCount;
    }

    public int getExtraPhoneCallTime() {
        return extraPhoneCallTime;
    }

    public void setExtraPhoneCallTime(int extraPhoneCallTime) {
        this.extraPhoneCallTime = extraPhoneCallTime;
    }

    public double getExtraNativeData() {
        return extraNativeData;
    }

    public void setExtraNativeData(double extraNativeData) {
        this.extraNativeData = extraNativeData;
    }

    public double getExtraDomesticData() {
        return extraDomesticData;
    }

    public void setExtraDomesticData(double extraDomesticData) {
        this.extraDomesticData = extraDomesticData;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
