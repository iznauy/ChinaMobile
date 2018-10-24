package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.PackagesOrder;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface PackageOrderJPA extends JpaRepository<PackagesOrder, PackagesOrder.PackagesOrderKey> {
}
