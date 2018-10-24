package top.iznauy.chinamobile.entity.packages;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@IdClass(CurrentPackages.CurrentPackagesKey.class)
@Table
public class CurrentPackages {

    @Id
    private String phoneNumber;

    @Id
    private long packageId;

    public CurrentPackages() {
    }

    public CurrentPackages(String phoneNumber, long packageId) {
        this.phoneNumber = phoneNumber;
        this.packageId = packageId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentPackages that = (CurrentPackages) o;

        if (packageId != that.packageId) return false;
        return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
        result = 31 * result + (int) (packageId ^ (packageId >>> 32));
        return result;
    }

    public static class CurrentPackagesKey implements Serializable {

        private String phoneNumber;

        private long packageId;

        public CurrentPackagesKey() {
        }

        public CurrentPackagesKey(String phoneNumber, long packageId) {
            this.phoneNumber = phoneNumber;
            this.packageId = packageId;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CurrentPackagesKey that = (CurrentPackagesKey) o;

            if (packageId != that.packageId) return false;
            return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;
        }

        @Override
        public int hashCode() {
            int result = phoneNumber != null ? phoneNumber.hashCode() : 0;
            result = 31 * result + (int) (packageId ^ (packageId >>> 32));
            return result;
        }
    }


}
