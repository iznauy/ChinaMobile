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
@IdClass(PackagesOrder.PackagesOrderKey.class)
@Table
public class PackagesOrder {

    @Id
    private String phoneNumber;

    @Id
    private long packageId;

    @Id
    @Temporal(value = TemporalType.DATE)
    private Date time;

    @Column(nullable = false)
    private PackagesOrderInForceType inForceType;

    @Column(nullable = false)
    private PackagesOrderType orderType;

    public PackagesOrder() {
    }

    public PackagesOrder(String phoneNumber, long packageId, Date time, PackagesOrderInForceType inForceType, PackagesOrderType orderType) {
        this.phoneNumber = phoneNumber;
        this.packageId = packageId;
        this.time = time;
        this.inForceType = inForceType;
        this.orderType = orderType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public PackagesOrderInForceType getInForceType() {
        return inForceType;
    }

    public void setInForceType(PackagesOrderInForceType inForceType) {
        this.inForceType = inForceType;
    }

    public PackagesOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(PackagesOrderType orderType) {
        this.orderType = orderType;
    }

    public enum PackagesOrderInForceType {
        NOW,
        NEXT_MONTH
    }

    public enum PackagesOrderType {
        SUBSCRIBE,
        UN_SUBSCRIBE
    }

    public static class PackagesOrderKey implements Serializable {

        private String phoneNumber;

        private long packageId;

        private Date time;

        public PackagesOrderKey() {
        }

        public PackagesOrderKey(String phoneNumber, long packageId, Date time) {
            this.phoneNumber = phoneNumber;
            this.packageId = packageId;
            this.time = time;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public long getPackageId() {
            return packageId;
        }

        public void setPackageId(long packageId) {
            this.packageId = packageId;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PackagesOrderKey that = (PackagesOrderKey) o;

            if (packageId != that.packageId) return false;
            if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
            return time != null ? time.equals(that.time) : that.time == null;
        }

        @Override
        public int hashCode() {
            int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
            result = 31 * result + (int) (packageId ^ (packageId >>> 32));
            result = 31 * result + (time != null ? time.hashCode() : 0);
            return result;
        }
    }
}
