package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.DomesticDataPackages;
import top.iznauy.chinamobile.entity.packages.PackagesKey;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface DomesticDataPackagesJPA extends JpaRepository<DomesticDataPackages, PackagesKey> {

    List<DomesticDataPackages> findByPhoneNumberAndDate(String phoneNumber, Date date);
}
