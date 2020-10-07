package com.blueeyescloud.ext.devicemaster.utils;

import java.text.DecimalFormat;

/**
 * 格式输出转换
 */
public class FormatUtils {
    public static String formatFileSize(long size){
        DecimalFormat df = new DecimalFormat(AT_MOST_ONE_DECIMAL);
        double value = 0;
        if(size > 1024 * 1024 * 1024){
            value = (size / (1024.0 * 1024 * 1024));
            return formatAtMostOneDecimal(value) + "G";
        }else if(size > 1024 * 1024){
            value = (size / (1024.0 * 1024));
            return formatAtMostOneDecimal(value) + "M";
        }else if (size > 1024){
            value = (size / 1024.0);
            return formatAtMostOneDecimal(value) + "K";
        }else {
            return size + "B";
        }
    }

    private static final String AT_MOST_ONE_DECIMAL = "###,##0.#"; //小数点后最多1位小数

    public static String formatAtMostOneDecimal(double value){
        DecimalFormat df = new DecimalFormat(AT_MOST_ONE_DECIMAL);
        return df.format(value);
    }

}
