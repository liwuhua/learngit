package com.yjdj.view.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 *
 *
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version 3.1.0
 * @since 2013-7-19 下午3:09:55
 */
public class FileUtils {

    public static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public final static long ONE_KB = 1024;
    public final static long ONE_MB = ONE_KB * 1024;
    public final static long ONE_GB = ONE_MB * 1024;
    public final static long ONE_TB = ONE_GB * (long) 1024;
    public final static long ONE_PB = ONE_TB * (long) 1024;
    public static int[] DAYS = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     *
     * 得到文件大小。
     *
     * @param fileSize
     * @return
     */
    public static String getHumanReadableFileSize(long fileSize) {
        if (fileSize < 0) {
            return String.valueOf(fileSize);
        }
        String result = getHumanReadableFileSize(fileSize, ONE_PB, "PB");
        if (result != null) {
            return result;
        }

        result = getHumanReadableFileSize(fileSize, ONE_TB, "TB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableFileSize(fileSize, ONE_GB, "GB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableFileSize(fileSize, ONE_MB, "MB");
        if (result != null) {
            return result;
        }
        result = getHumanReadableFileSize(fileSize, ONE_KB, "KB");
        if (result != null) {
            return result;
        }
        return String.valueOf(fileSize) + "B";
    }

    private static String getHumanReadableFileSize(long fileSize, long unit,
            String unitName) {
        if (fileSize == 0) {
            return "0";
        }

        if (fileSize / unit >= 1) {
            double value = fileSize / (double) unit;
            DecimalFormat df = new DecimalFormat("######.##" + unitName);
            return df.format(value);
        }
        return null;
    }

    public static String getFileExt(String fileName) {
        if (fileName == null) {
            return "";
        }

        String ext = "";
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            ext = fileName.substring(lastIndex + 1).toLowerCase();
        }

        return ext;
    }

    /**
     * 得到不包含后缀的文件名字。
     *
     * @return
     */
    public static String getRealName(String name) {
        if (name == null) {
            return "";
        }

        int endIndex = name.lastIndexOf(".");
        if (endIndex == -1) {
            return null;
        }
        return name.substring(0, endIndex);
    }

    /**
     * 文件数值型内容校验
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(Object str) {
        if (str == null || "".equals(str.toString().trim()) || " ".equals(str.toString().trim())) {
            return true;
        } else {
            Pattern pattern = Pattern.compile("[-]{0,1}[0-9]{1,10}[.]{0,1}[0-9]{0,10}");
            java.util.regex.Matcher isNum = pattern.matcher(str.toString());
            if (!isNum.matches()) {
                try {
                    Double.parseDouble(str.toString());
                    return true;
                } catch (NumberFormatException e) {
                    log.error("异常：\"" + str + "\"不是数字/整数..." + e.getMessage());
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss 或 yyyyMMdd 或 yyyyMM
     * @return
     */
    public static boolean isValidDate(String date) {
        try {
            if (date.length() == 6) {
                int year = Integer.parseInt(date.substring(0, 4));
                if (year <= 0) {
                    return false;
                }
                int month = Integer.parseInt(date.substring(4, 6));
                if (month <= 0 || month > 12) {
                    return false;
                }
            } else if (date.length() == 8) {
                int year = Integer.parseInt(date.substring(0, 4));
                if (year <= 0) {
                    return false;
                }
                int month = Integer.parseInt(date.substring(4, 6));
                if (month <= 0 || month > 12) {
                    return false;
                }
                int day = Integer.parseInt(date.substring(6, 8));
                if (day <= 0 || day > DAYS[month]) {
                    return false;
                }
                if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
                    return false;
                }
            } else if (date.length() == 19) {
                int year = Integer.parseInt(date.substring(0, 4));
                if (year <= 0) {
                    return false;
                }
                int month = Integer.parseInt(date.substring(5, 7));
                if (month <= 0 || month > 12) {
                    return false;
                }
                int day = Integer.parseInt(date.substring(8, 10));
                if (day <= 0 || day > DAYS[month]) {
                    return false;
                }
                if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
                    return false;
                }
                int hour = Integer.parseInt(date.substring(11, 13));
                if (hour < 0 || hour > 23) {
                    return false;
                }
                int minute = Integer.parseInt(date.substring(14, 16));
                if (minute < 0 || minute > 59) {
                    return false;
                }
                int second = Integer.parseInt(date.substring(17, 19));
                if (second < 0 || second > 59) {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static final boolean isGregorianLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}
