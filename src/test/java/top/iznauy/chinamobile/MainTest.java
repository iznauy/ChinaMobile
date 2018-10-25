package top.iznauy.chinamobile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.utils.Utils;

import java.util.Date;

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

    @Test
    public void subscribePackages1() {
        assert main.subscribePackages("13218068898", 5,
                PackagesOrder.PackagesOrderInForceType.NOW);
        assert main.subscribePackages("13218068898", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
    }

    @Test
    public void subscribePackages2() throws Exception {
        assert main.subscribePackages("13218068899", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
        Thread.sleep(2000);
        assert !main.subscribePackages("13218068899", 1,
                PackagesOrder.PackagesOrderInForceType.NOW);
    }

    @Test
    public void subscribePackages3() throws Exception {
        main.subscribePackages("13218068899", 3,
                PackagesOrder.PackagesOrderInForceType.NEXT_MONTH);
        Thread.sleep(2000);
        main.unSubscribePackages("13218068899", 3,
                PackagesOrder.PackagesOrderInForceType.NEXT_MONTH);
    }

    @Test
    public void usePhoneData() throws Exception {
        main.calculatePhoneDataFee("13218068898", 1000, PhoneData.PhoneDataType.DOMESTIC,
                new Date(), new Date());
        Thread.sleep(2000);
        main.calculatePhoneDataFee("13218068898", 24 + 24, PhoneData.PhoneDataType.DOMESTIC,
                new Date(), new Date());
        Thread.sleep(2000);
        main.calculatePhoneDataFee("13218068899", 2048 + 24, PhoneData.PhoneDataType.NATIVE,
                new Date(), new Date());
    }

    @Test
    public void findPackages() {
        System.out.println(main.findPackages("13218068898", 2018, 10));
    }

    @Test
    public void phoneCall() throws Exception {
        main.calculatePhoneCallFee("13218068897", Utils.getBeginDate(), new Date(), "13218068899");
    }

    @Test
    public void testBill() {
        main.showBill("13218068898");
    }

}
