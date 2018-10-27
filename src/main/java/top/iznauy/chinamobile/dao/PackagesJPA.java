package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.PackageContent;
import top.iznauy.chinamobile.entity.packages.Packages;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface PackagesJPA extends JpaRepository<Packages, Packages.PackagesKey> {

    List<Packages> findByPhoneNumberAndTypeAndDateIsAfter(String phoneNumber, PackageContent.PackageContentType type,
                                                          Date date);

    List<Packages> findByPhoneNumberAndDateIsBetween(String phoneNumber, Date begin, Date end);

    List<Packages> findByPhoneNumberAndDateIsAfter(String phoneNumber, Date date);

    List<Packages> findByPhoneNumberAndPackageIdAndDateIsAfter(String phoneNumber, long packageId,
                                                               Date date);
}
