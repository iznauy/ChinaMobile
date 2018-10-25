package top.iznauy.chinamobile;

import org.junit.Before;
import org.junit.Test;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/10/25.
 * Description:
 *
 * @author iznauy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BackgroundTest {

    public static class Tuple<A, B> {
        A a;
        B b;
    }

    List<String> phoneList = new ArrayList<>();
    Map<Long, Tuple<SupportedPackages, List<PackageContent>>> idTopPackages = new HashMap<>();

    @Autowired
    public UserJPA userJPA;

    @Autowired
    public SupportedPackagesJPA supportedPackagesJPA;

    @Autowired
    public PackageContentJPA packageContentJPA;

    @Before
    public void insertUser() {

        for (int i = 0; i < 100; i++) {
            phoneList.add(String.format("132180688%02d", i));
        }

        List<User> users = new ArrayList<>();
        for (String aPhoneList : phoneList) {
            User user = new User();
            user.setPhoneNumber(aPhoneList);
            user.setBalance(1000);
            users.add(user);
        }
        userJPA.saveAll(users);
    }

    @Before
    public void insertSupportedPackages() {
        // 全国流量套餐
        SupportedPackages supportedPackages = new SupportedPackages(1, "30元包2GB全国流量", 30);
        supportedPackagesJPA.saveAndFlush(supportedPackages);

        PackageContent packageContent = new PackageContent(1, PackageContent.PackageContentType.DOMESTIC_DATA,
                2048);
        packageContentJPA.saveAndFlush(packageContent);

        // 本地流量套餐
        SupportedPackages supportedPackages2 = new SupportedPackages(2, "20元包2G本地流量", 20);
        supportedPackagesJPA.saveAndFlush(supportedPackages2);

        PackageContent packageContent2 = new PackageContent(2, PackageContent.PackageContentType.NATIVE_DATA,
                2048);
        packageContentJPA.saveAndFlush(packageContent2);

        // 通话套餐
        SupportedPackages supportedPackages3 = new SupportedPackages(3, "20元包100分钟通话", 20);
        supportedPackagesJPA.saveAndFlush(supportedPackages3);

        PackageContent packageContent3 = new PackageContent(3, PackageContent.PackageContentType.PHONE_CALL,
                100);
        packageContentJPA.saveAndFlush(packageContent3);

        // 短信套餐
        SupportedPackages supportedPackages4 = new SupportedPackages(4, "10元包200条短信", 10);
        supportedPackagesJPA.saveAndFlush(supportedPackages4);

        PackageContent packageContent4 = new PackageContent(4, PackageContent.PackageContentType.MESSAGE,
                200);
        packageContentJPA.saveAndFlush(packageContent4);

        // 综合套餐
        // 200条短信，100分钟通话，1G全国流量，1G本地流量，只要50元
        SupportedPackages supportedPackages5 = new SupportedPackages(5, "超值特惠", 50);
        supportedPackagesJPA.saveAndFlush(supportedPackages5);

        PackageContent packageContent5 = new PackageContent(5, PackageContent.PackageContentType.MESSAGE,
                200);
        PackageContent packageContent6 = new PackageContent(5, PackageContent.PackageContentType.PHONE_CALL,
                100);
        PackageContent packageContent7 = new PackageContent(5, PackageContent.PackageContentType.NATIVE_DATA,
                1024);
        PackageContent packageContent8 = new PackageContent(5, PackageContent.PackageContentType.DOMESTIC_DATA,
                1024);
        packageContentJPA.saveAndFlush(packageContent5);
        packageContentJPA.saveAndFlush(packageContent6);
        packageContentJPA.saveAndFlush(packageContent7);
        packageContentJPA.saveAndFlush(packageContent8);
    }

    @Test
    public void test() {
        System.out.println("ok");
    }

}
