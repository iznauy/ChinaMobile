package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.SupportedPackages;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface SupportedPackagesJPA extends JpaRepository<SupportedPackages, Long> {
}
