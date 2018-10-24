package top.iznauy.chinamobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import top.iznauy.chinamobile.dao.*;
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.*;
import top.iznauy.chinamobile.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private CurrentPackagesJPA currentPackagesJPA;

    private PackageOrderJPA packageOrderJPA;

    private PackageContentJPA packageContentJPA;

    private MessagePackageJPA messagePackageJPA;

    private Map<PackageContent.PackageContentType, JpaRepository> repositoryMap = new HashMap<>();

    private static class PackagesFactory {

         Object create(PackageContent.PackageContentType type, String phoneNumber, Date date,
                             long packageId, double amount) {
            switch (type) {
                case MESSAGE:
                    return new MessagePackages(phoneNumber, date, packageId, amount);
                case PHONE_CALL:
                    return new PhoneCallPackages(phoneNumber, date, packageId, amount);
                case NATIVE_DATA:
                    return new NativeDataPackages(phoneNumber, date, packageId, amount);
                case DOMESTIC_DATA:
                    return new DomesticDataPackages(phoneNumber, date, packageId, amount);
                default:
                    return null;
            }
        }
    }

    public Main() {
    }

    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
    }

    @Autowired
    public void setPhoneCallPackagesJPA(PhoneCallPackagesJPA phoneCallPackagesJPA) {
        repositoryMap.put(PackageContent.PackageContentType.PHONE_CALL, phoneCallPackagesJPA);
        this.phoneCallPackagesJPA = phoneCallPackagesJPA;
    }

    @Autowired
    public void setDomesticDataPackagesJPA(DomesticDataPackagesJPA domesticDataPackagesJPA) {
        repositoryMap.put(PackageContent.PackageContentType.DOMESTIC_DATA, domesticDataPackagesJPA);
        this.domesticDataPackagesJPA = domesticDataPackagesJPA;
    }

    @Autowired
    public void setNativeDataPackagesJPA(NativeDataPackagesJPA nativeDataPackagesJPA) {
        repositoryMap.put(PackageContent.PackageContentType.NATIVE_DATA, nativeDataPackagesJPA);
        this.nativeDataPackagesJPA = nativeDataPackagesJPA;
    }

    @Autowired
    public void setCurrentPackagesJPA(CurrentPackagesJPA currentPackagesJPA) {
        this.currentPackagesJPA = currentPackagesJPA;
    }

    @Autowired
    public void setPackageOrderJPA(PackageOrderJPA packageOrderJPA) {
        this.packageOrderJPA = packageOrderJPA;
    }

    @Autowired
    public void setPackageContentJPA(PackageContentJPA packageContentJPA) {
        this.packageContentJPA = packageContentJPA;
    }

    @Autowired
    public void setMessagePackageJPA(MessagePackageJPA messagePackageJPA) {
        this.messagePackageJPA = messagePackageJPA;
    }

    public boolean subscribePackages(String phoneNumber, long packageId,
                                     PackagesOrder.PackagesOrderInForceType inForceType) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("电话号码不存在");
            return false;
        }

//        CurrentPackages currentPackages = currentPackagesJPA.findById(new CurrentPackages.CurrentPackagesKey(phoneNumber,
//                packageId)).orElse(null);
//        if (currentPackages != null) {
//            System.out.println("本套餐已订阅");
//            return false;
//        }
//
//        PackagesOrder order = new PackagesOrder(phoneNumber, packageId, new Date(), inForceType,
//                PackagesOrder.PackagesOrderType.SUBSCRIBE);
//        packageOrderJPA.saveAndFlush(order);

        if (inForceType == PackagesOrder.PackagesOrderInForceType.NOW) { // 立即生效需要做一些额外处理
            List<PackageContent> packageContents = packageContentJPA.findByPackageId(packageId);
            PackagesFactory factory = new PackagesFactory();
            for (PackageContent content: packageContents) {
                PackageContent.PackageContentType type = content.getType();
                Object object = factory.create(type, phoneNumber, Utils.getBeginDate(), packageId, content.getAmount());
                JpaRepository repository = repositoryMap.get(type);
                repository.saveAndFlush(object);
            }
        }
        return true;
    }

    public boolean unSubscribePackages(String phoneNumber, long packageId,
                                     PackagesOrder.PackagesOrderInForceType inForceType) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("电话号码不存在");
            return false;
        }

        CurrentPackages currentPackages = currentPackagesJPA.findById(new CurrentPackages.CurrentPackagesKey(phoneNumber,
                packageId)).orElse(null);
        if (currentPackages == null) {
            System.out.println("本套餐尚未订阅");
            return false;
        }

        List<PackageContent> packageContents = packageContentJPA.findByPackageId(packageId);
        boolean hasUsed = false;
        for (PackageContent content: packageContents) {
            PackageContent.PackageContentType type = content.getType();
            switch (type) {
                case DOMESTIC_DATA:
                    domesticDataPackagesJPA.findByPhoneNumberAndDate(phoneNumber, )
            }
        }

//        PackagesOrder order = new PackagesOrder(phoneNumber, packageId, new Date(), inForceType,
//                PackagesOrder.PackagesOrderType.UN_SUBSCRIBE);
//        packageOrderJPA.saveAndFlush(order);
        return false;
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
//        for (PrivilegePackages privilegePackages: list) {
//            double amount = privilegePackages.getAmount();
//            if (amount > 0.0) {
//                if (amount >= last) {
//                    amount -= last;
//                    privilegePackages.setAmount(amount);
//                    break;
//                } else {
//                    amount = 0;
//                    last -= amount;
//                    privilegePackages.setAmount(amount);
//                }
//            }
//        }
        return last;
    }
}
