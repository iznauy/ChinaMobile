package top.iznauy.chinamobile.entity.packages;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public class PackagesKey implements Serializable {

    private String phoneNumber;

    private Date date;

    private long packageId; // 指明套餐

    public PackagesKey() {
    }

    public PackagesKey(String phoneNumber, Date date, long packageId) {
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.packageId = packageId;
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

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackagesKey that = (PackagesKey) o;

        if (packageId != that.packageId) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (packageId ^ (packageId >>> 32));
        return result;
    }
}