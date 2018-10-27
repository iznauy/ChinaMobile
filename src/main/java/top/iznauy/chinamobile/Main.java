package top.iznauy.chinamobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.iznauy.chinamobile.dao.*;
import top.iznauy.chinamobile.entity.PackagesOrder;
import top.iznauy.chinamobile.entity.PhoneData;
import top.iznauy.chinamobile.entity.PositivePhoneCall;
import top.iznauy.chinamobile.entity.User;
import top.iznauy.chinamobile.entity.packages.CurrentPackages;
import top.iznauy.chinamobile.entity.packages.PackageContent;
import top.iznauy.chinamobile.entity.packages.Packages;
import top.iznauy.chinamobile.entity.packages.SupportedPackages;
import top.iznauy.chinamobile.utils.Tuple;
import top.iznauy.chinamobile.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Service
public class Main {

    private UserJPA userJPA;

    private PackageOrderJPA packageOrderJPA;

    private PackageContentJPA packageContentJPA;

    private CurrentPackagesJPA currentPackagesJPA;

    private PackagesJPA packagesJPA;

    private PhoneDataJPA phoneDataJPA;

    private PositivePhoneCallJPA positivePhoneCallJPA;

    private SupportedPackagesJPA supportedPackagesJPA;

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

    @Autowired
    public void setPhoneDataJPA(PhoneDataJPA phoneDataJPA) {
        this.phoneDataJPA = phoneDataJPA;
    }

    @Autowired
    public void setPositivePhoneCallJPA(PositivePhoneCallJPA positivePhoneCallJPA) {
        this.positivePhoneCallJPA = positivePhoneCallJPA;
    }

    @Autowired
    public void setSupportedPackagesJPA(SupportedPackagesJPA supportedPackagesJPA) {
        this.supportedPackagesJPA = supportedPackagesJPA;
    }

    /**
     * 订阅指定套餐
     *
     * @param phoneNumber
     * @param packageId   需要订阅的套餐的id
     * @param inForceType 立即生效还是下月生效
     * @return 是否成功订阅
     */
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
            CurrentPackages newPackages = new CurrentPackages(phoneNumber, packageId);
            currentPackagesJPA.saveAndFlush(newPackages);
            List<PackageContent> packageContents = packageContentJPA.findByPackageId(packageId);
            System.out.println(packageContents);
            List<Packages> packagesList = new ArrayList<>();
            for (PackageContent content : packageContents) {
                PackageContent.PackageContentType type = content.getType();
                Packages packages = new Packages();

                packages.setAmount(content.getAmount());
                packages.setDate(new Date());
                packages.setPackageId(packageId);
                packages.setPhoneNumber(phoneNumber);
                packages.setType(type);

                packagesList.add(packages);
            }
            System.out.println(packageId);
            packagesJPA.saveAll(packagesList);
            packagesJPA.flush();
        }
        return true;
    }

    /**
     * 退订指定的套餐
     *
     * @param phoneNumber
     * @param packageId   退订的套餐编号
     * @param inForceType 立即生效还是下月生效
     * @return 是否成功退订
     */
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
            List<Packages> packagesList = packagesJPA.findByPhoneNumberAndPackageIdAndDateIsAfter(phoneNumber, packageId,
                    Utils.getBeginDate());

            System.out.println(packagesList);
            boolean hasUsed = false;
            for (Packages packages : packagesList) {
                for (PackageContent content : packageContents) {
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
            packagesJPA.flush();
            System.out.println("执行了删除操作");
        }

        PackagesOrder order = new PackagesOrder(phoneNumber, packageId, new Date(), inForceType,
                PackagesOrder.PackagesOrderType.UN_SUBSCRIBE);
        packageOrderJPA.saveAndFlush(order);
        currentPackagesJPA.delete(new CurrentPackages(phoneNumber, packageId));

        return true;

    }

    /**
     * 获取指定年月的优惠套餐及其使用情况
     *
     * @param phoneNumber
     * @param year
     * @param month
     * @return
     */
    public List<Packages> findPackages(String phoneNumber, int year, int month) {
        Date beginDate = Utils.getBeginDate(year, month);
        Date endDate = Utils.getEndDate(year, month);
        return packagesJPA.findByPhoneNumberAndDateIsBetween(phoneNumber, beginDate, endDate);
    }

    /**
     * 计算数据通信费用
     * 如果存在套餐，优先扣除套餐余量
     * 套餐不够时，使用话费支付
     *
     * @param phoneNumber
     * @param amount      通信的数据量，单位是MB
     * @param type        本地流量还是全国流量
     * @param begin       使用流量的开始时段
     * @param end         使用流量的结束时段
     * @return
     */
    public double calculatePhoneDataFee(String phoneNumber, double amount, PhoneData.PhoneDataType type, Date begin,
                                        Date end) {
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
        double fee;
        if (type == PhoneData.PhoneDataType.NATIVE)
            fee = extraAmount * FeeTable.NATIVE_DATA;
        else
            fee = extraAmount * FeeTable.DOMESTIC_DATA;

        packagesJPA.saveAll(packagesList);
        packagesJPA.flush();

        PhoneData phoneData = new PhoneData(phoneNumber, begin, end, amount, type, fee);
        phoneDataJPA.saveAndFlush(phoneData);

        if (extraAmount > 0) {
            if (type == PhoneData.PhoneDataType.NATIVE)
                user.addExtraNativeData(extraAmount);
            else
                user.addExtraDomesticData(extraAmount);
            userJPA.saveAndFlush(user);
        }

        return fee;
    }

    /**
     * 计算通信费用
     * 如果套餐存在余量，优先扣除套餐余量
     * 套餐不足时，剩余部分使用话费支付
     *
     * @param phoneNumber
     * @param start       通信开始时间
     * @param end         通信结束时间
     * @param receiver    被叫
     * @return
     */
    public double calculatePhoneCallFee(String phoneNumber, Date start, Date end, String receiver) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null)
            return 0.0;

        // calculate PhoneCall Time
        double minutes = (end.getTime() - start.getTime()) / 1000.0 / 60.0; // 取ceil
        double standardMinutes = Math.ceil(minutes); // 标准时长（向上取整）

        List<Packages> packagesList = packagesJPA.findByPhoneNumberAndTypeAndDateIsAfter(phoneNumber,
                PackageContent.PackageContentType.PHONE_CALL, Utils.getBeginDate());

        double extraTime = calculateExtraAmount(packagesList, standardMinutes);
        double fee = extraTime * FeeTable.PHONE_CALL;

        packagesJPA.saveAll(packagesList);
        packagesJPA.flush();

        PositivePhoneCall positivePhoneCall = new PositivePhoneCall(phoneNumber, receiver, start, end, fee);
        positivePhoneCallJPA.saveAndFlush(positivePhoneCall);

        if (extraTime > 0) {
            user.addExtraPhoneCallTime((int) fee);
            userJPA.saveAndFlush(user);
        }
        return fee;
    }

    /**
     * 显示当月账单信息
     *
     * @param phoneNumber
     */
    public void showBill(String phoneNumber) {
        User user = userJPA.findById(phoneNumber).orElse(null);
        if (user == null) {
            System.out.println("当前查询的账户不存在");
            return;
        }

        List<CurrentPackages> currentPackagesList = currentPackagesJPA.findByPhoneNumber(phoneNumber);
        Set<Long> relatedPackageIds = currentPackagesList.stream().map(CurrentPackages::getPackageId)
                .collect(Collectors.toSet());

        List<SupportedPackages> relatedSupportedPackages = supportedPackagesJPA.findAllById(relatedPackageIds);
        double basicFee = relatedSupportedPackages.stream().map(SupportedPackages::getFee).reduce(0.0,
                (integer, aDouble) -> integer + aDouble);
        double extraPhoneCallFee = user.getExtraPhoneCallTime() * FeeTable.PHONE_CALL;
        double extraMessageFee = user.getExtraMessageCount() * FeeTable.MESSAGE;
        double extraNativeDataFee = user.getExtraNativeData() * FeeTable.NATIVE_DATA;
        double extraDomesticDataFee = user.getExtraDomesticData() * FeeTable.DOMESTIC_DATA;

        // 输出用户基本信息
        System.out.println("当前用户：" + phoneNumber);
        System.out.println("账户余额：" + user.getBalance() + "元");
        System.out.println("您的月账单如下：");
        System.out.println("\t月基本费：" + basicFee + "元");
        System.out.println("\t套餐外通话时长：" + user.getExtraPhoneCallTime() + "分钟" + "\t\t\t套餐外通信费："
                + extraPhoneCallFee + "元");
        System.out.println("\t套餐外发送短信数：" + user.getExtraMessageCount() + "条" + "\t\t\t套餐外短信费："
                + extraMessageFee + "元");
        System.out.println("\t套餐外本地流量：" + user.getExtraNativeData() + "MB" + "\t\t\t套餐外本地流量费："
                + extraNativeDataFee + "元");
        System.out.println("\t套餐外国内流量：" + user.getExtraDomesticData() + "MB" + "\t\t\t套餐外国内流量费："
                + extraDomesticDataFee + "元");

        if (relatedSupportedPackages.size() != 0) {
            List<Packages> packagesList = packagesJPA.findByPhoneNumberAndDateIsAfter(phoneNumber, Utils.getBeginDate());
            Map<Long, List<Packages>> groupedPackagesList = packagesList.stream().
                    collect(Collectors.groupingBy(Packages::getPackageId));
            List<PackageContent> packageContentList = packageContentJPA.findByPackageIdIn(relatedPackageIds);

            Map<Tuple<Long, PackageContent.PackageContentType>, PackageContent> contentMap = new HashMap<>();
            for (PackageContent content : packageContentList) {
                Tuple<Long, PackageContent.PackageContentType> tuple = new Tuple<>(content.getPackageId(), content.getType());
                contentMap.put(tuple, content);
            }

            System.out.println("本月套餐使用情况如下：");
            for (SupportedPackages supportedPackages : relatedSupportedPackages) {
                System.out.println("\t套餐：" + supportedPackages.getPackageName());
                List<Packages> subPackagesList = groupedPackagesList.get(supportedPackages.getId());
                for (Packages packages : subPackagesList) {
                    PackageContent content = contentMap.get(new Tuple<>(packages.getPackageId(), packages.getType()));
                    switch (packages.getType()) {
                        case DOMESTIC_DATA:
                            System.out.println("\t\t全国流量总量为：" + content.getAmount() + "MB" + "\t\t\t剩余："
                                    + packages.getAmount() + "MB");
                            break;
                        case NATIVE_DATA:
                            System.out.println("\t\t本地流量总量为：" + content.getAmount() + "MB" + "\t\t\t剩余："
                                    + packages.getAmount() + "MB");
                            break;
                        case PHONE_CALL:
                            System.out.println("\t\t通话时长总量为：" + content.getAmount() + "分钟" + "\t\t\t剩余："
                                    + packages.getAmount() + "分钟");
                            break;
                        case MESSAGE:
                            System.out.println("\t\t短信总量为：" + content.getAmount() + "条" + "\t\t\t剩余："
                                    + packages.getAmount() + "条");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private double calculateExtraAmount(List<Packages> packagesList, double total) {
        double last = total;
        for (Packages packages : packagesList) {
            double amount = packages.getAmount();
            if (amount > 0.0) {
                if (amount >= last) {
                    amount -= last;
                    packages.setAmount(amount);
                    last = 0;
                    break;
                } else {
                    last -= amount;
                    amount = 0;
                    packages.setAmount(amount);
                }
            }
        }
        return last;
    }
}
