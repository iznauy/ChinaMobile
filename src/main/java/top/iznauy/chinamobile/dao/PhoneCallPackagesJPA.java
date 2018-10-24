package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.packages.PackagesKey;
import top.iznauy.chinamobile.entity.packages.PhoneCallPackages;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface PhoneCallPackagesJPA extends JpaRepository<PhoneCallPackages, PackagesKey> {

    List<PhoneCallPackages> findByPhoneNumberAndDate(String phoneNumber, Date date);

}
