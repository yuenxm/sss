package com.blueeyescloud.baselib.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取mac地址
 * 方式：
 * （1）通过busybox获取本地存储的mac地址
 * （2）读取接口配置文件或扫描各个网络接口获取mac地址
 * （3）通过ip地址来获取绑定的mac地址
 */
public class MacUtils {

    private static String mMacAddress = null;

    public static String getMacAddress() {
        if (mMacAddress != null) {
            return mMacAddress;
        }
        String result = null;
        if (!Build.MODEL.equals("N9201")) {
            result = callCmd("busybox ifconfig eth0", "HWaddr");
        }

        if (!isValidMac(result)) {
            result = readSysfs("/sys/class/net/eth0/address");
        }

        if (!isValidMac(result) && !Build.MODEL.equals("N9201")) {//多彩云
            result = callCmd("busybox ifconfig wlan0", "HWaddr");
        }

        if (!isValidMac(result)) {
            result = readSysfs("/sys/class/net/wlan0/address");
        }

        if (!isValidMac(result)) {
            result = getMacFromHardware();
        }

        if (!isValidMac(result)) {
            result = getMacFromIp();
        }

        if (result == null) {
            Log.e("MacUtil", "can not get MacAddress");

            //如果上述方法无法获取到mac地址，返回一个固定的mac地址（android 6.0以上系统定义的）
            return "02:00:00:00:00:00";

        }
        mMacAddress = result;
        return result;
    }

    /**
     * 通过ifconfig去获取mac地址。。。
     */
    private static String callCmd(String cmd, String filter) {
        String result = null;
        String line;
        InputStreamReader is = null;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            //执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && line.contains(filter) == false) {
                //result += line;
            }
            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //对该行数据进行解析
        if (!TextUtils.isEmpty(result) && result.contains("HWaddr") == true) {
            result = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
            if (result.length() > 1) {
                return result.trim();
            }
        }

        return result;
    }

    private static boolean isValidMac(String macStr) {
        if (TextUtils.isEmpty(macStr)) {
            return false;
        }
        String macAddressRule = "([A-Fa-f0-9]{2}[-:]){5}[A-Fa-f0-9]{2}";
        // 这是真正的MAC地址；正则表达式；
        if (macStr.matches(macAddressRule)) {
            String splitStr = null;
            if (macStr.contains(":")) {
                splitStr = ":";
            } else if (macStr.contains("-")) {
                splitStr = "-";
            }
            String[] addr = macStr.split(splitStr);
            boolean isAllZero = true;
            for (String add : addr) {
                if (!"00".equals(add)) {
                    isAllZero = false;
                    break;
                }
            }
            boolean isAllOne = true;
            for (String add : addr) {
                if (!"11".equals(add)) {
                    isAllOne = false;
                    break;
                }
            }
            if (isAllZero || isAllOne) {
                return false;
            }
            Log.e("zyx", "it is a valid MAC address");
            return true;
        } else {
            Log.e("zyx", "it is not a valid MAC address!!! " + macStr);
            return false;
        }
    }

    private static String readSysfs(String path) {
        if (!new File(path).exists()) {
            return null;
        }
        FileReader fr = null;
        BufferedReader br = null;
        StringBuilder value = null;
        try {
            String str;
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            value = new StringBuilder();
            while ((str = br.readLine()) != null) {
                value.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
                if (null != br) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value != null ? value.toString() : null;
        }
    }

    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = netInterfaces.nextElement();
                Enumeration<InetAddress> ipEnumeration = netInterface.getInetAddresses();
                while (ipEnumeration.hasMoreElements()) {
                    ip = ipEnumeration.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) break;
                    else ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 根据ip获取mac地址
     *
     * @return
     */
    private static String getMacFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strMacAddr;
    }

    /**
     * 扫描所有网络接口，找到接口是eth0或wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        String mac = getMacFromHardwareByInterfaceName("eth0");
        if (!isValidMac(mac)) {
            mac = getMacFromHardwareByInterfaceName("wlan0");
        }
        return mac;
    }

    private static String getMacFromHardwareByInterfaceName(String netInterface) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase(netInterface)) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}