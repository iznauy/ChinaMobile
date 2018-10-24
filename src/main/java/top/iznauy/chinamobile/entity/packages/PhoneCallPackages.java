package top.iznauy.chinamobile.entity.packages;

import javax.persistence.*;
import java.util.Date;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@Table
@IdClass(PackagesKey.class)
public class PhoneCallPackages implements PrivilegePackages {

    @Id
    private String phoneNumber;

    @Id
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Id
    private long packageId; // 指明套餐

    @Column(nullable = false)
    private double amount;

    public PhoneCallPackages() {
    }

    public PhoneCallPackages(String phoneNumber, Date date, long packageId, double amount) {
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.packageId = packageId;
        this.amount = amount;
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
}
