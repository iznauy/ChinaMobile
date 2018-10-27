package top.iznauy.chinamobile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.packages.Packages;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainTest {

    @Autowired
    public Main main;

    /**
     * 订阅某套餐后立即退订，以及订阅某套餐后下月退订
     * @throws Exception
     */
    @Test
    public void subscribePackages() throws Exception {
        System.out.println("是否成功订阅：");
        System.out.println(main.subscribePackages("13218068800", 3,
                PackagesOrder.PackagesOrderInForceType.NOW));
        Thread.sleep(1000);
        System.out.println("是否成功退订：");
        System.out.println(main.unSubscribePackages("13218068800", 3,
                PackagesOrder.PackagesOrderInForceType.NOW));

        System.out.println("是否成功订阅：");
        System.out.println(main.subscribePackages("13218068805", 3,
                PackagesOrder.PackagesOrderInForceType.NOW));
        Thread.sleep(1000);
        System.out.println("是否成功退订：");
        System.out.println(main.unSubscribePackages("13218068805", 3,
                PackagesOrder.PackagesOrderInForceType.NEXT_MONTH));
    }

    @Test
    public void usePhoneData() throws Exception {
        System.out.println("本次使用流量产生的通讯费：");
        System.out.println(main.calculatePhoneDataFee("13218068800", 1088, PhoneData.PhoneDataType.DOMESTIC,
                new Date(), new Date()));
        System.out.println("本次使用流量产生的通讯费：");
        System.out.println(main.calculatePhoneDataFee("13218068801", 24 + 24, PhoneData.PhoneDataType.DOMESTIC,
                new Date(), new Date()));
        System.out.println("本次使用流量产生的通讯费：");
        System.out.println(main.calculatePhoneDataFee("13218068802", 2048 + 24, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date()));
    }

    @Test
    public void findPackages() {
        List<Packages> list = main.findPackages("13218068800", 2018, 11);
        for (Packages packages: list) {
            System.out.println(packages);
        }
    }

    @Test
    public void phoneCall() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 20);
        Date begin = calendar.getTime();
        calendar.set(Calendar.MINUTE, 30);
        Date end = calendar.getTime();
        System.out.println("本次使用电话产生的通讯费：");
        System.out.println(main.calculatePhoneCallFee("13218068800", begin, end, "13218068800"));
    }

    @Test
    public void testBill() {

        main.showBill("13218068800");
        main.showBill("13218068801");
    }

}
