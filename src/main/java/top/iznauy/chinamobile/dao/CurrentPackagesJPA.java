package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.CurrentPackages;

import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface CurrentPackagesJPA extends JpaRepository<CurrentPackages, CurrentPackages.CurrentPackagesKey> {

    List<CurrentPackages> findByPhoneNumber(String phoneNumber);

}
