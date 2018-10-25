package top.iznauy.chinamobile.entity.packages;

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
public class SupportedPackages {

    @Id
    private long id;

    @Column(nullable = false)
    private String packageName;

    @Column(nullable = false)
    private double fee;

    public SupportedPackages() {
    }

    public SupportedPackages(long id, String packageName, double fee) {
        this.id = id;
        this.packageName = packageName;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
