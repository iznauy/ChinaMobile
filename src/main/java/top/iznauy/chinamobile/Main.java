package top.iznauy.chinamobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.iznauy.chinamobile.dao.DomesticDataPackagesJPA;
import top.iznauy.chinamobile.dao.NativeDataPackagesJPA;
import top.iznauy.chinamobile.dao.PhoneCallPackagesJPA;
import top.iznauy.chinamobile.dao.UserJPA;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.DomesticDataPackages;
import top.iznauy.chinamobile.entity.packages.NativeDataPackages;
import top.iznauy.chinamobile.entity.packages.PhoneCallPackages;
import top.iznauy.chinamobile.entity.packages.PrivilegePackages;
import top.iznauy.chinamobile.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Service
public class Main {

    private UserJPA userJPA;

    private PhoneCallPackagesJPA phoneCallPackagesJPA;

    private DomesticDataPackagesJPA domesticDataPackagesJPA;

    private NativeDataPackagesJPA nativeDataPackagesJPA;

    public Main() {
    }

    public Main(UserJPA userJPA, PhoneCallPackagesJPA phoneCallPackagesJPA, DomesticDataPackagesJPA
            domesticDataPackagesJPA, NativeDataPackagesJPA nativeDataPackagesJPA) {
        this.userJPA = userJPA;
        this.phoneCallPackagesJPA = phoneCallPackagesJPA;
        this.domesticDataPackagesJPA = domesticDataPackagesJPA;
        this.nativeDataPackagesJPA = nativeDataPackagesJPA;
    }

    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    @Autowired
    public void setPhoneCallPackagesJPA(PhoneCallPackagesJPA phoneCallPackagesJPA) {
        this.phoneCallPackagesJPA = phoneCallPackagesJPA;
    }

    @Autowired
    public void setDomesticDataPackagesJPA(DomesticDataPackagesJPA domesticDataPackagesJPA) {
        this.domesticDataPackagesJPA = domesticDataPackagesJPA;
    }

    @Autowired
    public void setNativeDataPackagesJPA(NativeDataPackagesJPA nativeDataPackagesJPA) {
        this.nativeDataPackagesJPA = nativeDataPackagesJPA;
    }

    public double calculatePhoneDataFee(String phoneNumber, double amount, PhoneData.PhoneDataType type) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("电话号码不存在");
            return 0.0;
        }

        if (type == PhoneData.PhoneDataType.DOMESTIC) {
            List<DomesticDataPackages> domesticDataPackages = domesticDataPackagesJPA.
                    findByPhoneNumberAndDate(phoneNumber, Utils.getBeginDate());
            double last = calculateLast(domesticDataPackages, amount);
            return last * FeeTable.DOMESTIC_DATA;
        } else {
            List<NativeDataPackages> nativeDataPackages = nativeDataPackagesJPA.
                    findByPhoneNumberAndDate(phoneNumber, Utils.getBeginDate());
            double last = calculateLast(nativeDataPackages, amount);
            return last * FeeTable.NATIVE_DATA;
        }

    }



    public double calculatePhoneCallFee(String phoneNumber, Date start, Date end) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null)
            return 0.0;

        // calculate PhoneCall Time
        double minutes = (end.getTime() - start.getTime()) / 1000.0 / 60.0; // 取ceil
        double standardMinutes = Math.ceil(minutes); // 标准时长（向上取整）


        List<PhoneCallPackages> phoneCallPackages = phoneCallPackagesJPA.
                findByPhoneNumberAndDate(phoneNumber, Utils.getBeginDate());

        double lastMinutes = calculateLast(phoneCallPackages, standardMinutes);
        return lastMinutes * FeeTable.PHONE_CALL;
    }

    private double calculateLast(List<? extends PrivilegePackages> list, double last) {
        for (PrivilegePackages privilegePackages: list) {
            double amount = privilegePackages.getAmount();
            if (amount > 0.0) {
                if (amount >= last) {
                    amount -= last;
                    privilegePackages.setAmount(amount);
                    break;
                } else {
                    amount = 0;
                    last -= amount;
                    privilegePackages.setAmount(amount);
                }
            }
        }
        return last;
    }
}
