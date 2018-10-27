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
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.PackageContent;
import top.iznauy.chinamobile.entity.packages.SupportedPackages;

import java.util.*;

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

    private List<String> phoneList = new ArrayList<>();

    @Autowired
    public UserJPA userJPA;

    @Autowired
    public SupportedPackagesJPA supportedPackagesJPA;

    @Autowired
    public PackageContentJPA packageContentJPA;

    @Autowired
    public Main main;

    @Before
    public void initData() throws Exception {
        insertUser();
        Thread.sleep(1000);
        insertSupportedPackages();
        Thread.sleep(1000);
        subscribePackages();
        Thread.sleep(1000);
        unSubscribePackages();
        Thread.sleep(1000);
        addData();
        Thread.sleep(1000);
        addPhoneCallInfo();
    }

    public void insertUser() {

        for (int i = 0; i < 10; i++) {
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


    public void subscribePackages() throws Exception {
        main.subscribePackages("13218068800", 5,
                PackagesOrder.PackagesOrderInForceType.NOW);
        main.subscribePackages("13218068800", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
        main.subscribePackages("13218068801", 1,
                PackagesOrder.PackagesOrderInForceType.NEXT_MONTH);
        main.subscribePackages("13218068802", 2,
                PackagesOrder.PackagesOrderInForceType.NOW);

        main.subscribePackages("13218068809", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
    }


    public void unSubscribePackages() {
        main.unSubscribePackages("13218068809", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
    }


    public void addData() throws Exception {
        main.calculatePhoneDataFee("13218068800", 1000, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
        main.calculatePhoneDataFee("13218068801", 24, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
        main.calculatePhoneDataFee("13218068802", 524, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
        Thread.sleep(3000);
        main.calculatePhoneDataFee("13218068800", 768, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
        main.calculatePhoneDataFee("13218068801", 10224, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
        main.calculatePhoneDataFee("13218068802", 524, PhoneData.PhoneDataType.DOMESTIC,
                new Date(), new Date());
    }


    public void addPhoneCallInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        Date begin1 = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        Date end1 = calendar.getTime();
        main.calculatePhoneCallFee("13218068800", begin1, end1, "13218068801");
        main.calculatePhoneCallFee("13218068801", begin1, end1, "13218068809");
    }

    @Test
    public void main() {
        System.out.println("OK");
    }

}
