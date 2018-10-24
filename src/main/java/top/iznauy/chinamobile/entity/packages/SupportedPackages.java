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

    public SupportedPackages() {
    }

    public SupportedPackages(long id, String packageName) {
        this.id = id;
        this.packageName = packageName;
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
}
