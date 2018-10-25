package top.iznauy.chinamobile;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.iznauy.chinamobile.dao.PackageContentJPA;
import top.iznauy.chinamobile.dao.SupportedPackagesJPA;
import top.iznauy.chinamobile.dao.UserJPA;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.PackageContent;
import top.iznauy.chinamobile.entity.packages.SupportedPackages;

/**
 * Created on 2018/10/25.
 * Description:
 *
 * @author iznauy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BackgroundTest {

    @Autowired
    public UserJPA userJPA;

    @Autowired
    public SupportedPackagesJPA supportedPackagesJPA;

    @Autowired
    public PackageContentJPA packageContentJPA;

    @Before
    public void insertUser() {
        User user = new User();
        user.setPhoneNumber("13218068898");
        user.setBalance(1000);
        userJPA.saveAndFlush(user);

        SupportedPackages supportedPackages = new SupportedPackages(1, "20元包100MB全国流量");
        supportedPackagesJPA.saveAndFlush(supportedPackages);

        PackageContent packageContent = new PackageContent(1, PackageContent.PackageContentType.DOMESTIC_DATA,
                200);
        packageContentJPA.saveAndFlush(packageContent);
    }

}
