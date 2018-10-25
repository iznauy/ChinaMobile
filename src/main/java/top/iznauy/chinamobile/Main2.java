package top.iznauy.chinamobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.iznauy.chinamobile.dao.*;
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.CurrentPackages;
import top.iznauy.chinamobile.entity.packages.PackageContent;
import top.iznauy.chinamobile.entity.packages.Packages;
import top.iznauy.chinamobile.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Service
public class Main2 {

    private UserJPA userJPA;

    private PackageOrderJPA packageOrderJPA;

    private PackageContentJPA packageContentJPA;

    private CurrentPackagesJPA currentPackagesJPA;

    private PackagesJPA packagesJPA;

    @Autowired
    public void setUserJPA(UserJPA userJPA) {
        this.userJPA = userJPA;
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
    public void setCurrentPackagesJPA(CurrentPackagesJPA currentPackagesJPA) {
        this.currentPackagesJPA = currentPackagesJPA;
    }

    @Autowired
    public void setPackagesJPA(PackagesJPA packagesJPA) {
        this.packagesJPA = packagesJPA;
    }

    public boolean subscribePackages(String phoneNumber, long packageId,
                                     PackagesOrder.PackagesOrderInForceType inForceType) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("电话号码不存在");
            return false;
        }

        CurrentPackages currentPackages = currentPackagesJPA.findById(new CurrentPackages.CurrentPackagesKey(phoneNumber,
                packageId)).orElse(null);
        if (currentPackages != null) {
            System.out.println("本套餐已订阅");
            return false;
        }

        PackagesOrder order = new PackagesOrder(phoneNumber, packageId, new Date(), inForceType,
                PackagesOrder.PackagesOrderType.SUBSCRIBE);
        packageOrderJPA.saveAndFlush(order);

        if (inForceType == PackagesOrder.PackagesOrderInForceType.NOW) { // 立即生效需要做一些额外处理
            List<PackageContent> packageContents = packageContentJPA.findByPackageId(packageId);
            List<Packages> packagesList = new ArrayList<>();
            for (PackageContent content: packageContents) {
                PackageContent.PackageContentType type = content.getType();
                Packages packages = new Packages();

                packages.setAmount(content.getAmount());
                packages.setDate(new Date());
                packages.setPackageId(packageId);
                packages.setPhoneNumber(phoneNumber);
                packages.setType(type);

                packagesList.add(packages);
            }
            packagesJPA.saveAll(packagesList);
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

        if (inForceType == PackagesOrder.PackagesOrderInForceType.NOW) {
            List<PackageContent> packageContents = packageContentJPA.findByPackageId(packageId);
            List<Packages.PackagesKey> keyList = packageContents.stream().
                    map(e -> new Packages.PackagesKey(phoneNumber, Utils.getBeginDate(), packageId, e.getType()))
                    .collect(Collectors.toList());
            // TODO: 明早起来写代码
            List<Packages> packagesList = packagesJPA.findAllById(keyList);
            boolean hasUsed = false;
            for (Packages packages: packagesList) {
                for (PackageContent content: packageContents) {
                    if (content.getType() == packages.getType() && content.getAmount() > packages.getAmount()) {
                        // 已经用了
                        hasUsed = true;
                        break;
                    }
                }
                if (hasUsed)
                    break;
            }

            if (hasUsed) {
                System.out.println("套餐已使用，无法退订");
                return false;
            }
            // 没有用过的话，就删掉其中所有的小项优惠
            packagesJPA.deleteAll(packagesList);
        }

        PackagesOrder order = new PackagesOrder(phoneNumber, packageId, new Date(), inForceType,
                PackagesOrder.PackagesOrderType.UN_SUBSCRIBE);
        packageOrderJPA.saveAndFlush(order);

        return true;

    }

    public double calculatePhoneDataFee(String phoneNumber, double amount, PhoneData.PhoneDataType type) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("电话号码不存在");
            return 0.0;
        }

        PackageContent.PackageContentType contentType = PackageContent.PackageContentType.DOMESTIC_DATA;
        if (type == PhoneData.PhoneDataType.NATIVE)
            contentType = PackageContent.PackageContentType.NATIVE_DATA;

        List<Packages> packagesList = packagesJPA.findByPhoneNumberAndTypeAndDateIsAfter(phoneNumber,
                contentType, Utils.getBeginDate());

        double extraAmount = calculateExtraAmount(packagesList, amount);

        if (type == PhoneData.PhoneDataType.NATIVE)
            return extraAmount * FeeTable.NATIVE_DATA;
        else
            return extraAmount * FeeTable.DOMESTIC_DATA;
    }

    public double calculatePhoneCallFee(String phoneNumber, Date start, Date end) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null)
            return 0.0;

        // calculate PhoneCall Time
        double minutes = (end.getTime() - start.getTime()) / 1000.0 / 60.0; // 取ceil
        double standardMinutes = Math.ceil(minutes); // 标准时长（向上取整）

        List<Packages> packagesList = packagesJPA.findByPhoneNumberAndTypeAndDateIsAfter(phoneNumber,
                PackageContent.PackageContentType.PHONE_CALL, Utils.getBeginDate());

        double extraTime = calculateExtraAmount(packagesList, standardMinutes);
        return extraTime * FeeTable.PHONE_CALL;

    }

    private double calculateExtraAmount(List<Packages> packagesList, double total) {
        double last = total;
        for (Packages packages: packagesList) {
            double amount = packages.getAmount();
            if (amount > 0.0) {
                if (amount >= last) {
                    amount -= last;
                    packages.setAmount(amount);
                    break;
                } else {
                    amount = 0;
                    last -= amount;
                    packages.setAmount(amount);
                }
            }
        }
        return last;
    }
}
