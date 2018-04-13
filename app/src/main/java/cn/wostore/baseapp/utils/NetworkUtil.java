package cn.wostore.baseapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Android手机网络相关工具类。
 * <p>
 * 用于检测网络状态，判断运营商等功能。
 * </p>
 *
 * @author chenxuliang
 * @version 1.0.0
 */
public class NetworkUtil {
    /**
     * 网络运行商类型，联通
     */
    public static final int OPERATOR_CHINAUNION = 1;
    /**
     * 网络运行商类型，移动
     */
    public static final int OPERATOR_CHINAMOBILE = 2;
    /**
     * 网络运行商类型，电信
     */
    public static final int OPERATOR_CHINATEL = 3;
    /**
     * 网络运行商类型，其他
     */
    public static final int OPERATOR_OTHER = 100;

    private static final String MOBILE_NETWOKSINGER_1 = "46000";
    private static final String MOBILE_NETWOKSINGER_2 = "46002";
    private static final String MOBILE_NETWOKSINGER_3 = "46007";
    private static final String MOBILE_NETWOKSINGER_4 = "46020";

    private static final String UNION_NETWOKSINGER_1 = "46001";
    private static final String UNION_NETWOKSINGER_2 = "46006";

    private static final String TEL_NETWOKSINGER_1 = "46003";
    private static final String TEL_NETWOKSINGER_2 = "46005";

    /**
     * 当前手机连接网络的类型,没有网络
     */
    public static final int MOBILENET_TYPE_NO = 0;
    /**
     * 当前手机连接网络的类型,仅手机GPRS网络开启中
     */
    public static final int MOBILENET_TYPE_ONLY_GPRS = 1;
    /**
     * 当前手机连接网络的类型,仅wifi网络开启中
     */
    public static final int MOBILENET_TYPE_ONLY_WIFI = 2;
    /**
     * 当前手机连接网络的类型,wifi网络和GPRS都开启
     */
    public static final int MOBILENET_TYPE_GPRS_AND_WIFI = 3;

    private NetworkUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 检查当前WIFI是否连接，两层意思，是否连接，连接是不是WIFI。
     * <p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
     *     </pre>
     * </p>
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return true表示当前网络处于连接状态，且是WIFI，否则返回false
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected() && ConnectivityManager.TYPE_WIFI == info.getType()) {
            return true;
        }
        return false;
    }

    /**
     * 检查当前GPRS是否连接，两层意思，是否连接，连接是不是GPRS。
     * <p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
     *     </pre>
     * </p>
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return true表示当前网络处于连接状态，且是GPRS，否则返回false
     */
    public static boolean isGprsConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected() && ConnectivityManager.TYPE_MOBILE == info.getType()) {
            return true;
        }
        return false;
    }

    /**
     * 检查当前网络是否连接。
     * <p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
     *     </pre>
     * </p>
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return true表示当前网络处于连接状态，否则返回false
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }


    /**
     * 判断当前手机连接网络的类型，0：没有网络；1：仅手机GPRS网络开启中；2：仅wifi网络开启中; 3：wifi网络和GPRS都开启。
     * <p>
     * 返回值
     * <table border="1">
     * <tr>
     * <td>返回值</td>
     * <td>对应的int值</td>
     * <td>说明</td>
     * </tr>
     * <tr>
     * <td>MOBILENET_TYPE_NO</td>
     * <td>0</td>
     * <td>没有网络</td>
     * </tr>
     * <tr>
     * <td>MOBILENET_TYPE_ONLY_GPRS</td>
     * <td>1</td>
     * <td>仅手机GPRS网络开启中</td>
     * </tr>
     * <tr>
     * <td>MOBILENET_TYPE_ONLY_WIFI</td>
     * <td>2</td>
     * <td>仅wifi网络开启中</td>
     * </tr>
     * <tr>
     * <td>MOBILENET_TYPE_GPRS_AND_WIFI</td>
     * <td>3</td>
     * <td>wifi网络和GPRS都开启</td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
     *     </pre>
     * </p>
     *
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 返回当前手机连接网络的类型，0：没有网络；1：仅手机GPRS网络开启中；2：仅wifi网络开启中; 3：wifi网络和GPRS都开启;
     */
    public static int getMobileNet(Context context) {
        if (isGprsConnected(context)) {
            if (isWifiConnected(context)) {
                return MOBILENET_TYPE_GPRS_AND_WIFI;
            } else {
                return MOBILENET_TYPE_ONLY_GPRS;
            }
        } else {
            if (isWifiConnected(context)) {
                return MOBILENET_TYPE_ONLY_WIFI;
            } else {
                return MOBILENET_TYPE_NO;
            }
        }
    }

    /**
     * 获取网络运行商，1：联通；2：移动；3：电信；100：其他。
     * <p>
     * 返回值
     * <table border="1">
     * <tr>
     * <td>返回值</td>
     * <td>对应的int值</td>
     * <td>说明</td>
     * </tr>
     * <tr>
     * <td>OPERATOR_CHINAUNION</td>
     * <td>1</td>
     * <td>联通</td>
     * </tr>
     * <tr>
     * <td>OPERATOR_CHINAMOBILE</td>
     * <td>2</td>
     * <td>移动</td>
     * </tr>
     * <tr>
     * <td>OPERATOR_CHINATEL</td>
     * <td>3</td>
     * <td>电信</td>
     * </tr>
     * <tr>
     * <td>OPERATOR_OTHER</td>
     * <td>100</td>
     * <td>其他</td>
     * </tr>
     * </table>
     * </p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.READ_PHONE_STATE"/&gt;
     *     </pre>
     * </p>
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 返回网络运行商，1：联通；2：移动；3：电信； 100：其他
     */
    public static int getSimOperator(Context context) {
        int ret;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(telephonyManager == null)
        {
            return OPERATOR_OTHER;
        }
        String simOperator = telephonyManager.getSimOperator();
        if (!TextUtils.isEmpty(simOperator)) {
            if (MOBILE_NETWOKSINGER_1.equals(simOperator) || MOBILE_NETWOKSINGER_2.equals(simOperator) ||
                    MOBILE_NETWOKSINGER_3.equals(simOperator) || MOBILE_NETWOKSINGER_4.equals(simOperator)) {
                // 中国移动
                ret = OPERATOR_CHINAMOBILE;
            } else if (UNION_NETWOKSINGER_1.equals(simOperator) || UNION_NETWOKSINGER_2.equals(simOperator)) {
                // 中国联通
                ret = OPERATOR_CHINAUNION;
            } else if (TEL_NETWOKSINGER_1.equals(simOperator) || TEL_NETWOKSINGER_2.equals(simOperator)) {
                // 中国电信
                ret = OPERATOR_CHINATEL;
            } else {
                // 其他
                ret = OPERATOR_OTHER;
            }
        } else {
            ret = OPERATOR_OTHER;
        }

        return ret;
    }

    /**
     * 获取手机当前的接入点名称 APN（Access Point Name）。
     * <p>
     * 注意：app中需要以下权限
     * <pre>
     *         &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
     *     </pre>
     * </p>
     * @param context 上下文对象，一般是{@link android.app.Application}或者{@link android.app.Activity}
     * @return 成功返回接入点APN信息，否则为""
     */
    public static String getAPN(Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        String apn = "";
        if (null != network) {
            apn = network.getExtraInfo();
        }
        return apn;
    }
}
