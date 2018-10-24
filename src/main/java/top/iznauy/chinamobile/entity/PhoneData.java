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
@IdClass(PhoneData.PhoneDataKey.class)
public class PhoneData {

    @Id
    @Column(nullable = false)
    private String phoneNumber;

    @Id
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private PhoneDataType type;

    @Column(nullable = false)
    private double fee;

    public PhoneData() {
    }

    public PhoneData(String phoneNumber, Date startDate,
                     Date endDate, double amount, PhoneDataType type, double fee) {
        this.phoneNumber = phoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.type = type;
        this.fee = fee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PhoneDataType getType() {
        return type;
    }

    public void setType(PhoneDataType type) {
        this.type = type;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public enum PhoneDataType {
        NATIVE,
        DOMESTIC
    }

    public static class PhoneDataKey implements Serializable {

        private String phoneNumber;

        private Date startDate;

        public PhoneDataKey() {
        }

        public PhoneDataKey(String phoneNumber, Date startDate) {
            this.phoneNumber = phoneNumber;
            this.startDate = startDate;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PhoneDataKey that = (PhoneDataKey) o;

            if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
            return startDate != null ? startDate.equals(that.startDate) : that.startDate == null;
        }

        @Override
        public int hashCode() {
            int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
            result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
            return result;
        }
    }
}
