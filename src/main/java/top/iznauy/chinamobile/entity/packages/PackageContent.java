package top.iznauy.chinamobile.entity.packages;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@IdClass(PackageContent.PackageContentKey.class)
@Table
public class PackageContent {

    @Id
    private int packageId;

    @Id
    @Enumerated(value = EnumType.ORDINAL)
    private PackageContentType type;

    @Column(nullable = false)
    private double amount;

    public PackageContent() {
    }

    public PackageContent(int packageId, PackageContentType type, double amount) {
        this.packageId = packageId;
        this.type = type;
        this.amount = amount;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public PackageContentType getType() {
        return type;
    }

    public void setType(PackageContentType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public enum PackageContentType { // 4类型的套餐
        NATIVE_DATA,
        DOMESTIC_DATA,
        PHONE_CALL,
        MESSAGE
    }

    public static class PackageContentKey implements Serializable {

        private int packageId;

        private PackageContentType type;

        public PackageContentKey() {
        }

        public PackageContentKey(int packageId, PackageContentType type) {
            this.packageId = packageId;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PackageContentKey that = (PackageContentKey) o;

            if (packageId != that.packageId) return false;
            return type == that.type;
        }

        @Override
        public int hashCode() {
            int result = packageId;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

}


