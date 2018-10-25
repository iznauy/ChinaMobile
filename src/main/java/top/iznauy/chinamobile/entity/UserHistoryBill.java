package top.iznauy.chinamobile.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@Table
@IdClass(value = UserHistoryBill.UserHistoryBillKey.class)
public class UserHistoryBill {

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

    @Id
    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date date;

    public UserHistoryBill() {
    }

    public UserHistoryBill(String phoneNumber, int extraMessageCount, int extraPhoneCallTime, double extraNativeData,
                           double extraDomesticData, Date date) {
        this.phoneNumber = phoneNumber;
        this.extraMessageCount = extraMessageCount;
        this.extraPhoneCallTime = extraPhoneCallTime;
        this.extraNativeData = extraNativeData;
        this.extraDomesticData = extraDomesticData;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static class UserHistoryBillKey implements Serializable {

        private String phoneNumber;

        private Date date;

        public UserHistoryBillKey() {
        }

        public UserHistoryBillKey(String phoneNumber, Date date) {
            this.phoneNumber = phoneNumber;
            this.date = date;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserHistoryBillKey that = (UserHistoryBillKey) o;

            if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
            return date != null ? date.equals(that.date) : that.date == null;
        }

        @Override
        public int hashCode() {
            int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
            result = 31 * result + (date != null ? date.hashCode() : 0);
            return result;
        }
    }


}
