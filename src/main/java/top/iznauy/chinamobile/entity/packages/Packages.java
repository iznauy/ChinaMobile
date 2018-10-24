package top.iznauy.chinamobile.entity.packages;

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
@IdClass(Packages.PackagesKey.class)
@Table
public class Packages {

    @Id
    private String phoneNumber;

    @Id
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Id
    private long packageId; // 指明套餐

    @Column(nullable = false)
    private double amount;

    @Id
    @Column(nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private PackageContent.PackageContentType type; // 指明套餐里具体的内容

    public Packages() {
    }

    public Packages(String phoneNumber, Date date, long packageId, double amount, PackageContent.PackageContentType type) {
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.packageId = packageId;
        this.amount = amount;
        this.type = type;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PackageContent.PackageContentType getType() {
        return type;
    }

    public void setType(PackageContent.PackageContentType type) {
        this.type = type;
    }

    public static class PackagesKey implements Serializable {

        private String phoneNumber;

        private Date date;

        private long packageId; // 指明套餐

        private PackageContent.PackageContentType type;

        public PackagesKey() {
        }

        public PackagesKey(String phoneNumber, Date date, long packageId, PackageContent.PackageContentType type) {
            this.phoneNumber = phoneNumber;
            this.date = date;
            this.packageId = packageId;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PackagesKey that = (PackagesKey) o;

            if (packageId != that.packageId) return false;
            if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
            if (date != null ? !date.equals(that.date) : that.date != null) return false;
            return type == that.type;
        }

        @Override
        public int hashCode() {
            int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
            result = 31 * result + (date != null ? date.hashCode() : 0);
            result = 31 * result + (int) (packageId ^ (packageId >>> 32));
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }
}
