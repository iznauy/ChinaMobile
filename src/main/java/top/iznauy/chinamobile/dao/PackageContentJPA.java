package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.packages.PackageContent;

import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public interface PackageContentJPA extends JpaRepository<PackageContent, PackageContent.PackageContentKey> {

    List<PackageContent> findByPackageId(long packageId);

    List<PackageContent> findByPackageIdIn(Iterable<Long> packagesIds);

}
