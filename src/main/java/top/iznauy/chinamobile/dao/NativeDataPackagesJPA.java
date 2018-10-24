package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.NativeDataPackages;
import top.iznauy.chinamobile.entity.packages.PackagesKey;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface NativeDataPackagesJPA extends JpaRepository<NativeDataPackages, PackagesKey> {

    List<NativeDataPackages> findByPhoneNumberAndDate(String phoneNumber, Date date);

}
