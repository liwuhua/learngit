package com.yjdj.view.core.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

    private static String PREFIX = "\\u";

    private StringUtil() {
    }

    /**
     * @param list 列表
     * @return
     * @author wangjunfeng
     * @date 2013-1-24
     */
    public static String getAllWapPushid(List<?> list) {
        StringBuffer wapPushMessageids = new StringBuffer(128);

        if (list.size() > 0) {
            Object wapPushMessageid = list.get(0);
            wapPushMessageids.append(wapPushMessageid);
        }
        for (int i = 1; i < list.size(); i++) {
            Object wapPushMessageid = list.get(i);
            wapPushMessageids.append(",");
            wapPushMessageids.append(wapPushMessageid);
        }
        return wapPushMessageids.toString();
    }

    /**
     * 取域名
     *
     * @param url 要取域名的url
     */
    public static String getDomainName(String url) {
        String domainName = "";
        String pattern = "[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)";
        pattern = "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        boolean match = matcher.find();
        if (match) {
            domainName = matcher.group();
        }
        return domainName;
    }

    /**
     * 根据|分隔的字符串拼成一个列表
     *
     * @param str |分隔的字符串
     */
    public static List<String> getListByStr(String str) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < strChars.length; i++) {
            if (strChars[i] != '|' && i != strChars.length - 1) {
                sb.append(strChars[i]);
            } else {
                tempStr = sb.toString();
                String[] segArray = tempStr.split(":");
                if (segArray.length > 1) {
                    list.add(segArray[1]);
                }
                sb = new StringBuffer();
            }
        }
        return list;
    }

    /**
     * 根据指定分隔符的字符串拼成一个列表
     *
     * @param str 分隔的字符串
     * @param seperator 分隔符
     */
    public static List<String> getListByStr(String str, char seperator) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < strChars.length; i++) {
            if (strChars[i] != seperator && i != strChars.length - 1) {
                sb.append(strChars[i]);
            } else {
                if (i == strChars.length - 1 && strChars[strChars.length - 1] != seperator) {
                    sb.append(strChars[i]);
                }
                tempStr = sb.toString();
                list.add(tempStr);
                sb = new StringBuffer();
            }
        }
        return list;
    }

    /**
     * 根据|分隔的字符串拼成一个列表
     *
     * @param str |分隔的字符串
     */
    public static List<String> getListByStr2(String str) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < strChars.length; i++) {
            if (strChars[i] != '|' && i != strChars.length - 1) {
                sb.append(strChars[i]);
            } else {
                tempStr = sb.toString();
                String[] segArray = tempStr.split(":");
                if (segArray.length > 0) {
                    list.add(segArray[0]);
                }
                sb = new StringBuffer();
            }
        }
        return list;
    }

    /**
     * 根据|分隔的字符串拼成一个列表
     *
     * @param str |分隔的字符串
     */
    public static List<String> getListByStrForBook(String str) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < strChars.length; i++) {
            if (strChars[i] != '|' && i != strChars.length - 1) {
                sb.append(strChars[i]);
            } else {
                tempStr = sb.toString();
                String[] segArray = tempStr.split(":");
                if (segArray.length > 0) {
                    list.add(tempStr);
                }
                sb = new StringBuffer();
            }
        }
        return list;
    }

    /**
     * 根据|分隔的字符串拼成一个列表
     *
     * @param str |分隔的字符串
     */
    public static List<String> getListByStr3(String str) {
        List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        char[] strChars = str.toCharArray();
        for (int i = 0; i < strChars.length; i++) {
            if (strChars[i] != ',' && i != strChars.length - 1) {
                sb.append(strChars[i]);
            } else {
                tempStr = sb.toString();
                String[] segArray = tempStr.split(":");
                if (segArray.length > 0) {
                    list.add(segArray[0]);
                }
                sb = new StringBuffer();
            }
        }
        return list;
    }

    /**
     * 判断一个字符串是否是null或者空格或者空
     *
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.trim().length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 如果一个字符串为null，则转化为“”；否则，返回本身
     *
     * @param str 字符串
     * @return
     */
    public static String nullToStr(String str) {
        return str == null ? "" : str;
    }

    /**
     * 如果一个字符串为null，则转化为“”；否则，返回本身
     *
     * @param str 字符串
     * @return
     */
    public static String nullToStr(Object str) {
        return str == null ? "" : String.valueOf(str);
    }

    /**
     * <p>
     * 字符的前后几位保持不变，其它字符用代替字符替换（先替换前几位字符，后替换后几位字符）
     * </p>
     *
     * @param str 原字符
     * @param prefix 前几位(0,表示不做替换）
     * @param postfix 后几位(0,表示不做替换）
     * @param character 替换字符(若替换字符为一位，则保持长度不变)
     * @return 替换后的字符
     */
    public static String stringSwitch(String str, int prefix, int postfix, String character) {
        if (prefix < 0 || postfix < 0) {
            return str;
        }
        if (prefix == 0 && postfix == 0) {
            return str;
        }
        if (str != null && str.trim().length() > 0) {
            StringBuffer buf = new StringBuffer();
            int argsLength = str.length();
            // 保证被替换的长度大于原字符长度
            if (argsLength > prefix + postfix) {
                if (prefix != 0) {
                    String stringPrefix = str.substring(0, prefix);
                    buf.append(stringPrefix);
                }
                for (int i = prefix; i < argsLength - postfix; i++) {
                    buf.append(character);
                }
                if (postfix != 0) {
                    String stringPostfix = str.substring(argsLength - postfix);
                    buf.append(stringPostfix);
                }
                return buf.toString();
            } else {
                return str;
            }
        }
        return null;
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroStr(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num 数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthStr(long num, int fixdlenth) throws Exception {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroStr(fixdlenth - strNum.length()));
        } else {
            throw new Exception("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num 数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthStr(int num, int fixdlenth) throws Exception {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroStr(fixdlenth - strNum.length()));
        } else {
            throw new Exception("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 按字节长度截取字符串
     *
     * @param str 将要截取的字符串参数
     * @param toCount 截取的字节长度
     * @param more 字符串末尾补上的字符串
     * @return 返回截取后的字符串
     */
    public static String substring(String str, int toCount, String more) {
        int reInt = 0;
        String reStr = "";
        if (str == null) {
            return "";
        }
        char[] tempChar = str.toCharArray();
        for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
            String s1 = String.valueOf(tempChar[kk]);
            byte[] b = s1.getBytes();
            reInt += b.length;
            reStr += tempChar[kk];
        }
        if (toCount == reInt || (toCount == reInt - 1)) {
            reStr += more;
        }
        return reStr;
    }

    /**
     * 将对象转换为字符串，如果对象为null，则返回null
     *
     * @param obj 对象
     * @return
     */
    public static String object2Str(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    /**
     * filtertoHTML
     *
     * @param input String
     * @return input String
     * @author wangjunfeng
     */
    public static String filterToHtml(String input) {
        if (input == null || input.equals("")) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i <= input.length() - 1; i++) {
            c = input.charAt(i);
            switch (c) {
                case '"':
                    filtered.append("&quot;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case ' ':
                    filtered.append("&#160;");
                    break;
//            case '|':
//                filtered.append("&brvbar;");
//                break;
                case 13:
                    filtered.append("<br/>");
                    break;
                default:
                    filtered.append(c);
            }
        }
        return (filtered.toString());
    }

    /**
     * 判断一个IP地址是否合法
     *
     * @param ip IP
     * @return
     * @author wangjunfeng
     */
    public static boolean isIPAddressCorrect(String ip) {
        String[] ips = ip.split("\\.");
        if (ips.length != 4) {
            return false;
        }
        for (int i = 0; i < ips.length; i++) {
            try {
                if (Integer.parseInt(ips[i]) > 255) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * 检查传入的手机号码 是否 属于 中国移动的手机号码
     * </P>
     *
     * @param destmsisdn 手机号
     * @return true 是中国移动手机号码 false 不是中国移动手机号码
     * @throws Exception
     */
    public static boolean isCMCCmobile(String destmsisdn) {
        // 中国移动手机号码 段正则表达式
        String regEx = "^1((3[4-9])|(4[7])|(5[0-3|6-9])|(8[278]))[0-9]{8}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher m = pattern.matcher(destmsisdn); // 拿当前的手机号码与正则表达式去匹配
        boolean isCMCC = m.find(); // 匹配成功 的为中国移动手机号码
        return isCMCC;
    }

    /**
     * <p>
     * 将数组中重复的字符串 去除
     * </P>
     *
     * @param destmsisdns 手机号
     * @return
     */
    public static String removeSameDestmsisdn(String destmsisdns) {
        String tmp = null;
        StringBuffer sb = new StringBuffer();
        String[] destmsisdnArray = destmsisdns.split(",");
        if (destmsisdnArray == null || destmsisdnArray.length == 0) {
            return null;
        }
        ArrayList<String> resultList = new ArrayList<String>();
        for (int i = 0; i < destmsisdnArray.length; i++) {
            if (resultList.contains(destmsisdnArray[i])) {
                continue;
            } else {
                resultList.add(destmsisdnArray[i]);
            }
        }
        for (int i = 0; i < resultList.size(); i++) {
            sb.append(resultList.get(i));
            sb.append(",");
        }
        if (sb.length() > 0) {
            tmp = sb.substring(0, sb.length() - 1);
        } else {
            tmp = sb.toString();
        }
        return tmp;
    }

    /**
     * <p>
     * 字符串的转换
     * </p>
     *
     * @param str 字符串
     * @return
     */
    public static String stringGBK(String str) {
        String s = "";
        try {
            if (str == null) {
                return null;
            }
            s = new String(str.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 输入字符串是否中文
     *
     * @param str 字符串
     * @return
     * @author wangjunfeng
     */
    public static boolean isChinese(String str) {

        for (int i = 0; i < str.length(); i++) {
            if (!str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 输入字符串是否有英文
     *
     * @param str 字符串
     * @return
     * @author wangjunfeng
     */
    public static boolean hasEnglish(String str) {

        for (int i = 0; i < str.length(); i++) {
            if (!str.substring(i, i + 1).matches("[a-zA-Z]+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 输入字符串是否有数字
     *
     * @param str 字符串
     * @return
     * @author wangjunfeng
     */
    public static boolean hasNumeral(String str) {

        for (int i = 0; i < str.length(); i++) {
            if (!str.substring(i, i + 1).matches("\\d+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拆分字符串。
     *
     * @param src 字符串
     * @param splitter 分隔符
     * @return
     */
    public static List<String> split2List(String src, String splitter) {
        List<String> results = new ArrayList<String>();

        if (src == null) {
            return results;
        }
        String[] temp = src.split(splitter);
        for (int i = 0; i < temp.length; i++) {
            if (!temp[i].trim().equals("")) {
                results.add(temp[i].trim());
            }
        }

        return results;
    }

    /**
     * 定义字节（汉字、符号和英文字母的混合串）长度
     *
     * @param value 字符串
     * @return
     * @author wangjunfeng
     * @date 2013-1-24
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";

        if (StringUtil.isEmpty(value)) {
            return 0;
        }

        for (int i = 0; i < value.length(); i++) {

            String temp = value.substring(i, i + 1);

            if (temp.matches(chinese)) {

                valueLength += 2;
            } else {

                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 截取指定产度的字节
     *
     * @param value 字符串
     * @param endLength 长度
     * @return
     * @author wangjunfeng
     * @date 2013-1-24
     */
    public static String truncate(String value, int endLength) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";

        StringBuffer sb = new StringBuffer();

        if (length(value) < endLength) {
            return value;
        }

        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
            if (valueLength > endLength) {
                break;
            } else {
                sb.append(temp);
            }
        }
        return sb.toString();
    }

    /**
     * 截取指定产度的字节
     *
     * @param value 字符串
     * @param maxLength 长度
     * @return
     * @author wangjunfeng
     * @date 2013-1-24
     */
    public static String truncateByMaxLength(String value, int maxLength) {
        int endLength = maxLength - 3;
        int valueLength = 0;
        String result = null;
        String chinese = "[\u4e00-\u9fa5]";

        StringBuffer sb = new StringBuffer();

        if (length(value) <= maxLength) {
            return value;
        }

        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
            if (valueLength > endLength) {
                break;
            } else {
                sb.append(temp);
            }
        }
        result = sb.toString() + "...";

        return result;
    }

    /**
     * 将一个List中的元素按照分隔符拼成一个字符串。
     *
     * @param list 列表
     * @param splitter 分隔符
     * @return
     */
    public static String list2Str(List<?> list, String splitter) {
        StringBuilder sb = new StringBuilder();

        if (list.size() > 0) {
            sb.append(list.get(0).toString());
        }
        for (int i = 1; i < list.size(); i++) {
            sb.append(splitter).append(list.get(i));
        }

        return sb.toString();
    }

    /**
     * 取得文件后缀名
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 限制字符串输出500个字符以内
     *
     * @param value 字符串原值
     * @param length 截取的字符串长度
     * @return
     * @author wangjunfeng
     * @date 2012-12-3
     */
    public static String limitTo(String value, int length) {
        if (value.length() > length) {
            value = value.substring(0, length);
            return value + " ...";
        }
        return value;
    }

    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!PREFIX.equals(str.substring(0, 2))) {
            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

    public static String native2Ascii(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(char2Ascii(chars[i]));
        }
        return sb.toString();
    }

    /**
     * Native character to ascii string.
     *
     * @param c native character
     * @return ascii string
     */
    private static String char2Ascii(char c) {
        if (c > 255) {
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX);
            int code = (c >> 8);
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            code = (c & 0xFF);
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            return sb.toString();
        } else {
            return Character.toString(c);
        }
    }

    public static String ascii2Native(String str) {
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf(PREFIX);
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf(PREFIX, begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    /**
     * @param str
     * @return
     * @author wangjunfeng
     * @date 2014年9月17日
     */
    public static String strToNull(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            return str;
        }
    }

    /**
     * 哈萨克斯坦的svg xml
     *
     * @return String
     */
    public static String getHakstSvg() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<!-- Generator: Adobe Illustrator 16.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->"
                + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"
                + "<svg version=\"1.1\" id=\"图层_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\""
                + " width=\"1454px\" height=\"806px\" viewBox=\"0 0 1454 806\" enable-background=\"new 0 0 1454 806\" xml:space=\"preserve\">"
                + "<image overflow=\"visible\" width=\"1454\" height=\"806\" xlink:href=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABa4AAAMmCAYAAAAdUBaEAAAACXBIWXMAAAsTAAALEwEAmpwYAAAE"
                + "HWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjajZVdbBRVGIaf3TkzawLOVQUtSZmgAiGlWcAo"
                + "DQHd7S7bwlo22xZpY6Lb6dndsdPZ8cxs+QlXxETjDah3hsT4d0diYqIBfyJ4ITcYTAgK2JhouID4"
                + "ExISbhTqxWx3B2jFc/XNe77vfb/vPWdmIHWp4vtu0oIZL1TlQtbaNz5hpS6T5DGW0c2yih34mVKp"
                + "CFDxfZf71q0fSQBc2Lj4/n+uZVMysCHxENCYCuwZSBwA/bjtqxBSXcDW/aEfQqoIdKl94xOQehno"
                + "qkVxCHRNRvEbQJcaLQ9A6jhg2vXKFKROAL2TMbwWi6MeAOgqSE8qx7bKhaxVUo2q48pYuw/Y/p9r"
                + "xm0u6K0GlgfTI7uB9ZB4baqS2w30QeKEXcmPAE9A4sqss3e4Fd/xw2wZWAvJNc3psQywAZKDVbVz"
                + "LOJJqnpzcCF+91B99AVgBSS/9SaH97RqL9nBwASwBpJ36nKoCPSAZjnh0GhUq+1QjfKeSFerTslc"
                + "HugF7c3pxu5yxKl9HsyO5Bc4D9UHhlv4uVcqu0pAN2i/SbdQjrS0f/yw1OpB9HjucDHSEjkZ5EcW"
                + "8LA+OhjpCjdUo61acazq7Bxq5X9aV4PlVnzFd0vFqDc9qZrlsShf76uofCHi1EvSG2vx67PsTVSQ"
                + "NJhEYuNxG4syBbJY+CgaVHFwKSDxkCgkbjtnI5NIAqZROMwicQmQlJCoVmWHr4bE4xoKB5uBno9p"
                + "YlHnDzzqsbwB6jTxqC3BE/VyvcXTECtFWmwRabFNFMV2sVX0Y4lnxXNih8iJtOgX29q1pdhEFjWu"
                + "t3lepYnEosxespzBJaSCy694NAgWd+VYd3N9Z+eIesmxzx+9EfPKIWA65lbc0T0P8ly/ql/TL+pX"
                + "9cv6XCdD/1mf0+f0y3fN0rjPZbngzj0zL56VwcWlhmQGiYOHjM28Mc5x9vBXj3Z4LoqTL15YfvZw"
                + "1TvW3UHt80dvyNeHbw1zpLeDpn9K/5m+mH4//VH6d+0d7TPta+2U9oV2Dks7rZ3RvtG+0z7Rvoyd"
                + "1dJ3qH32ZGJ9S7xFvZa4ZtZcZT5u5szV5pNmscNnrjQ3mYPmOjNnrmqfW1wv7p7DOG7bn8W1orzY"
                + "DUg8zDTOEm/VGB4O+5EoAiq4eBy8J6dVKXrEJjF0z+3eKraJ9jRG3sgZGSxjg9FvbDJ2GZmOqrHO"
                + "yBn9xjojf9fttJeYVIbyQAgw0PAPKqdWD63N6fQzVsb3XWkNeXZfr1VxXUs5tXoYWEoGUs3KqT72"
                + "jU9Y0Sf9ZpkEkFhxvoOFz8P2v0D7oYNNNOFEACuf6mDru+GR9+Dk03ZTzbb+EYnE9xBUt2yOnpZn"
                + "Qf9lfv7mWki9Dbffmp//+4P5+dsfgjYHp91/AaCffFXBcLKBAAAAIGNIUk0AAG2YAABzjgAA8nsA"
                + "AITaAABulAAA5RoAADMnAAAZF5lJHH8AAGUmSURBVHja7N15kKRnfdjxlbSHuMzY8RWPBMZXygc2"
                + "g484cRGwjS98aVdI5rANAWlaQhLantkVSLa4hWiMTWELEpcv0K6gEuy4XKlkOGKTBpsiNr7L8Y2E"
                + "tDPbsnGCdqdFrN7eJ3/szO5M7/T0+3a/x/O+7+ePT9nASjvTPdP9vN/5ze/dE0LYAwAAAAAAsfAg"
                + "AAAAAAAgXAMAAAAAgHANAAAAAIBwDQAAAAAAwjUAAAAAAMI1AAAAAAAI1wAAAAAACNcAAMBsvult"
                + "pxphsDAXBgtzoSmfL1B/3sMAhGsAABCuhWsA4RpAuAYAAITr7MN10oAdW+hO+/EDwjUAwjUAAAjX"
                + "wrVwDQjXAMI1AAAgXM8egLNW1t8rbINwDYBwDQAAwrVwLVwDwjWAcA0AAAjX8YfrWIh6IFwDIFwD"
                + "AIBwLVwL14BwDSBcAwAATQ7X0wZa4RoQrgEQrgEAQLgWroVrQLgGEK4BAID6hutpQ21TgrVwDcI1"
                + "AMI1AAAI18K1cA0I1wDCNQAA0KRwPW2wbVqoFrBBuAZAuAYAAOFauBauAeEaQLgGAACaEK7FZgEb"
                + "EK4BhGsAABCuhWvhGhCuARCuAQBAuBauhWtAuAYQrgEAAOFauAaEawCEawAAEK6Fa+EaEK4BhGsA"
                + "AEC4RrgG4RoA4RoAAIRr4Vq4BoRrAOEaAAAQrhGuQbgGQLgGAADhWrAWrgHhGkC4BgAAhGuEa0C4"
                + "BhCuAQBAuBashWtAuAZAuAYAAOEa4RoQrgGEawAAIK8QLVgL14BwDSBcAwAAwjXCNQjXAAjXAAAg"
                + "XIvCAjYgXAMI1wAAgHCNcA14DwMQrgEAQLgWrIVrKEqv1V/ZymMiXAMI1wAAIFwL18I1xBCuwwbh"
                + "WrgGEK4BAEC4FqqFa4hi0lq4Fq4BhGsAABCuhWvhGqKatBauhWsA4RoAAIRroVq4hmJ3V48hXAvX"
                + "AMI1AAAI18K1cA2lTlRfFKrdnFG4BhCuAQCg4cRc4RpK2l09Nlx7zIRrAOEaAACEa0FXuIYyJ61N"
                + "WAvXAMI1AMX/qjkAcRBtEa6JdNJaqBauAYRrAIRrAOEa4RrimrT2WAnXAMI1AMI1gGCNcB19gNv6"
                + "380a6bwOmLQWrgEQrgGEawCEa4TrqKOb14XSJ62Fa+EaQLgGwEUggFANcYVrZ5baT1ivCNfCNYBw"
                + "DYBwDYBwTWXCtTNLoyeshWvhGkC4BsBFIIBADRc5W8au6tgim9ePzCerV6YN16M8xsI1gHANgItA"
                + "AOEa4Tr3iGbS2mS1CWzhGkC4BsDFIIBADVOvCJnlPX2380GeZw/nkVImrXML1yavhWsA4RoA4RpA"
                + "uMakdar4lUc88z1u0trktXANIFwDIFwDCNSYuJ45fInWJq3zDtcmr4VrAOEaAOEaQLim5tPWg4W5"
                + "YR7Ba5Z/j+95k9Ymr4VrAOEaAOEaoETHr9rb3U273V5st9uLAiuxT1o7M5i0Ljpcj/LcCNcAwjUA"
                + "LkIBsg3XYZx2ux3a7bbISh4eiy10eU0waW0CW7gGEK4BcCEKEM9k9cRwLWCTw4qQ+wVrKjhpbfe1"
                + "cA0gXAPgghSgqMlq4ZqC/fNgYe6/WAtCDSatTV4L1wDCNQAuTAFmnLAO0xKuydjnBgtzPzNYmIvq"
                + "bLAxCR68hgjXdl8L1wDCNQDCNUBEE9bCNQWG658vI1zv9n2zEa7Peg0Rrk1gC9cAwjUAwjVAhBPW"
                + "wjU5GwwW5j5UZLhO8n00+nF6bRGu7b4WrgGEawCEa4CIJqyFawrQGyzM7SkiXif9PhKuownUO7mk"
                + "1+p/oILhurGT165BAIRrAIRrgMLCtYBNhoab4TrreD3t95FgHW24/lCv1e/2Wv0/qHK4btrktWsQ"
                + "AOEaAOEaQLimis4OFuYu2QzXZ//kcaW/zwvX0YXrS3qt/hduBN9He63+sNfqn61wvA7CNQDCNQDC"
                + "NSBcWxlC/OH68q3helNZwTqrVSF5B/C6rzTZEq4/r9fqv6zX6vd6rf6ZHaL12QqG7JVRwjUAwjWA"
                + "cA0gXAvXxOUZg4W5J2+YGyzMPXGwMLd/sDC3d8O+wcLcJaPv68Nfe8Jlw994/NxgYe7AYGHu8wYL"
                + "c188WJj7ksHC3Bdu/OcnDBbmnrTx/z9psDD3uI1/3yVb15NsIVzHF64v67X6z+i1+v9rY9J6txh8"
                + "tqIROwjXAAjXAMI1QJ2DdbfocC1gk/Hk9dnBwtyZwcLc6cHC3InBwtxfDBbm/nCwMPehwcLcOwYL"
                + "czcOFuauGXzfk68efP+Tbxh8/5N/dvC8J68MFub+98aff3iwMPdPg4W5/zZYmHvvYGGuP1iY+9xg"
                + "Ye6xwcLcYLAw9/LBwtx3DRbmnrYRtc/u9jHFMsGd9DGsabi+vNfq/16v1R9UfDVIY3dfuwYBEK4B"
                + "EK4B4ToURbgmx3A93IjMg42IfWawMPfPGzH75GBh7sGNSP2ZjTA92Phnhlv+HWdH/vPm3/GXg4W5"
                + "jwwW5t45WJi7RriuRLh+fK/V//OaR+taT167BgEQrgEQroH6TU53Y5m0Hg3VgjU5h+uzk6Ly1J6Z"
                + "/M/mFa4n/fubGqp3CNcrvVb/Q71W/89yCMRn7b4WrgGEawCEa4DpJqe7sUxaC9cUGK6j+ZiE6yjC"
                + "dd6h+KwJbOEaQLgGQLgGTEqnm5zuJmQ1CFQwXJf1cUUeqkeFhqv8BLZrEADhGgDhGqjQpHTZO6qF"
                + "axCuGzphbQe2cA0gXAMgXAPNmbCeNCmd8p8XsEG4rl2wHjNRbcK65hPYrkEAhGsAhGugvAnrSbpV"
                + "m7AWrkG4NlFtAlu4BhCuARCugTgnqLPaLd2t6oS1mzUiXFsRkvGkdR3j8dkYJrCFawCEawDhGjBB"
                + "3XjCNcK1cG3S+rwzvVb/n3ut/sDktXANIFwDIFwDeUxWi9IzhmyxEwTrBu6uPttr9R8rO1zHOnnt"
                + "GgRAuAZAuAZMVgvXIFybsG7eupCoJ69dgwAI1wAI12D3dPa7qRGuQbi2y7pSN2scJVwDCNcACNeA"
                + "CWnhWmSEpodr8TiykC1cAwjXAAjXwAwT1RMmrMVhN2cE4Vq4poK7r12DAAjXAAjXUKdwLQ4L14Bw"
                + "TQ0mr12DAAjXAAjXsOPEckV3UovDFQnWoiII1sK1yWvhGkC4BkC4hrQ7obsl//0I14BwTYMnr12D"
                + "AAjXAAjXmLDeaWK5m3QCO6NJaRPTbr4ICNdUYPK61+oL1wDCNQDCNUQz4dw1KY1wDcK1cC1g91r9"
                + "PZuEawDhGgDhGkoP1yalEayhWcF6ZMp2IuG6eZPXeQZs1yAAwjUAwjXCNQjXIFzPGpSF6wZPXucR"
                + "sF2DAAjXAAjXCNfgZowgXI+btE49iTvlJLcYXC0f6bX6H8wzYLsGARCuARCucXNGARvhGoTrrCag"
                + "V0xYN8pv91r9S/OI165BAIRrAIRrBGzhmsICtqAN9Q/XCYm+1Tfstfqf6rX6H+21+h/YKV7PErBd"
                + "gwAI1wAI1wjXwjXCNQjXJqJJ6+xGvD7Va/X/R6/V//xeq3+ZcA0gXAMgXINVIbh5I5BZwDYRzZQG"
                + "vVb/Lzcmr9/fa/X3C9cAwjUAwjWYtEa4BrIM10Is0/pMr9X/1V6r//Stk9fCNYBwDYBwDcI1bt4I"
                + "TB22hWsy8HCv1X9dr9X/ZuEaQLgGQLgG4RrhGhCuicFjvVb/D3ut/rcK1wDCNQDCNUyz09pua4Rr"
                + "YFvIFq7JyCO9Vv+lvVb/a3ut/hHhGkC4BkC4BhPWCNeAcE3ZzvRa/b/ttfp/0mv13y9cAwjXAAjX"
                + "kGTSWjRFuAaEa/I23LCySbgGEK4BEK4hyYqQpERWhGsQrmFWwjWAcA2AcA1Wi1C9gC0egnBNvcN1"
                + "2slr1yAAwjUAwjVYMYJwDcK1cE1Uk9euQQCEawCEazB5jVUh0EAb33+LW6dhhVVimbx2DQIgXAMg"
                + "XMM0O7LFV4RrqEe4FlOJcvLaNQiAcA2AcA0msBGsQbgG4RpAuAZAuAbhGuFauAbhGuFauAYQrgEQ"
                + "rkG4xk0YgYnhevQHTAI3Zey6dg0CIFwDIFyDcI1wDcK1cE1Uk9euQQCEawCEaxCuEa6hweE66Qof"
                + "AZsiJ69dgwAI1wAI1yBcI1yDcC1cE9XktWsQAOEaAOEa0gTrrnBNFjdldJNGiC9cC9YI1wDCNQDC"
                + "NZi0RrgWrkG4BuEaQLgGQLgG4RorRIBZb94IRey6dg0CIFwDIFyDcI1wDQjXRDV57RoEQLgGQLgG"
                + "u60RrgHBmqgmr12DAAjXAAjXYNIa4RoQrokqYLsGARCuARCuafYEdVIiK4I1CNZQ2s5rANIRrgGE"
                + "a6hbuBZVEa5BuIbodl4DIFwDIFxjJQjkHqyFa4gvWAvXCNcAwjUAwjWYxEa4Fq5BuAbhGkC4BkC4"
                + "BpPYWBECZBWwhW7sugYQrgEQriH2mzmKsgjXIFyDyWsA4RoA4RpMZFP9VSFCNlTnZo3jvn8FbExe"
                + "AwjXAAjXUJWJbJEW4RqEazB5DSBcAyBcgwlsrA4B8l8h4uaOmLwGEK4BEK6h6hPY4izCNQjXYPIa"
                + "QLgGQLgGk9fEvxrEqhCo/2oREK4BhGsAhGsQrhGuAeEa4RoA4RpAuAaEa9yMERCusesaQLgGQLgG"
                + "4RrhGsr0TI+BcI3JawDhGgDhGoRr3HQRiPpmjW7OiMlrAOEaAOEahGuEa7LzLXNh8O0b/9fjgXCN"
                + "yWsA4RoA4RqEa4RroIywbCUIJq8BhGvhGkC4BuEa4RoT3Sa6hWsweQ0gXAMgXEOCoNzN+98vXAvX"
                + "wjXka+nw4V0D9bAzv01WIdtqEIRrAOEaAOEaqhquxVvhWriGkierhesC3eAxEK4BhGsAhGuYZQJ6"
                + "ain/veKtcC1cQ0HBejRQJzVryHZzRuHarmsA4RoA4RrKnoDumqxGuAbhWrjOxsldxPJxpGHyGkC4"
                + "BkC4hrJ2TZusRriOKFTWbbWF53i6x3HaYJ02ZCd9voTrfANxLJG6IkHb5DUgXAvXAMI11HzSGoRr"
                + "4Vq4Fq6F6whDcayxOrKQLVwDwrVwDSBcQ6S7rEVUSgnWgmQ+qyCq+vgmvWmg577ccJ1V4BatywvC"
                + "JyvG5DWAcA2AcI0JaxCuhWvhWrgWrqMN1uuVi84Vi9jCNSBcC9cAwjVUfJc1CNcVuOle1R7n2D+f"
                + "rP7+vD/+rG7KWFTYFq2TxOr1sLbxf3f6M2tj5BuR13NWSswWrgHhWrgGEK7BpDXCtXAtXAvXwnVb"
                + "tJ4Qq3fXTyyeIJ1PyBauAYRrAIRr7LIGN2WMPFinXbERy/OR9PMq+uPNanVJ2psZThvKYw/WTQ3X"
                + "SaPxGrvKOWLbdQ0I18I1gHANJqwRroVr4Vq4Fq6zDdcPjxF/tJ496K5u0YyInesaEeEaEK6FawDh"
                + "GuyyRrimqNUPk8Jp0qDalHCd9uOZJKvnpeorQsZ93rMH60cTiC1aZxNxV3cI16uNn8penyVim7wG"
                + "hGvhGkC4BpPWCNcI18K1cN3OOViXE7CLXAuy28R11SexZ50mnyVgO1sCwrVwDSBcg3CNcE1kIbKo"
                + "gJ3284z15oZpg/Wsz0vVVoNkvTJk+mhdXMAuepf1aoJp7FhXiqwmtZjAmM9vinht8hoQroVrAOEa"
                + "rApBuEa4Fq6F66LDdX4BO5YbMCYNwtGG6jTBepeQvVPANnkNIFwDCNdg8hqE6xlu3hdLWMzq84s1"
                + "XFd11UZVg/Us4TrbaJ1PvI4hWk8biaOJ1VuC9YnF9XBi8XRq4wL2yXQBW7gGhGvhGkC4BuEa4Vq4"
                + "Fq6Fa+F6UrTOI1xnG7CTROvVmCecM/r4pv17pw3ViSJ2+ulr4RoQrpOGaw8qTfpVd48FgHCNcC1U"
                + "VztcT7rJ4LSfd9arTOoSgpsRrh/NWf7RugqT19OG7Fn+/Sdak4P1QxtmCtjp4rVd14BwLVyDcA1g"
                + "1zXCtXAtXAvXwnVZ09bZxOuYJq3ziNd5ONFaDydak2P1Q9vC9foWaYP2uelrk9eAcC1cw7ShpStg"
                + "A5i8Rriue7iuSlic9nkb/TyzDvbj/n1pP17hOo6vr/KnrS+E64dzCNdl3hAxxnh9Ypdo/dBE61uM"
                + "Ru3do/Vm7B6N1z2T14BwLVxDysAiXAOYvEa4Fq6Fa+FauC4wXD+aSbieFIybHK/TRusHR+wWrh9K"
                + "FK63x+uTyeK1yWtAuBauadpE9YSwMvGf8dgCmLxGuBauiw2MacX+8YrIwnX28XpyKF5raLyeLVqf"
                + "GgnY451IGLBXk09dm7wGhGvhGuEkFeEawOQ1wrVwLVwL18J1NCtDkoTrutywcfpofXG4nhStP714"
                + "6ryLJ7DThuvtN2zcnLpO8TwL14BwLVwjmAjXACavEa7juXliU8P1tIE41o9TNK5iuH60JNOvC4l1"
                + "2rqscD1btD4Xrh/YFq9PZRev060LEa4B4Vq4RigRrgGEa4Rr4Vq4Fq6F63LD9bTxOvZwvRZJuE4T"
                + "rR+4/pHwwOIjieN1mnC9JlwDwrVwjUnruoZru7cB4RqqH67HBc6sArZwCvUO1w8nCNdrDQzX005b"
                + "j0br+69/ZNd4Pfu6kOTh2q5rQLgWrhFIqhauTYIDdl2DcC1cQ4bh+uFSw/W060I2gu1i3OF6LeJp"
                + "652i9aYHdlkbkjZcPzRduDZ5DQjXwjXCiHANYPIa4bq8YJ00uGX174UmB+zx0br8cP3wFGtCTixe"
                + "EGu0jjtcn4vSD2xMWd+/zanwwPUX4nXycL35nGz/7zbDdW+KcG3yGhCuhWsEEeEaQLhGuBauQbiO"
                + "PlxvxuCHtoTSmMP1WiTh+lx4PnXeTtPWn7r+sxemrjfi9YWp60nhen2s1cVzN2ecIlybvAaEa+Ea"
                + "k9bFh+vRndWju6vH/O/CNSBcQ4XC9axheVzAFqyh2eF6ezhNG67PTW3vZO28hoTr6y+E609thOtP"
                + "bd11ff2p8OnrhWsA4RriDCHdgj+fbsLPV7gGKvx6ve+CgzsQXxGuhWsoKFz3IgrXD6cI16NrKFZn"
                + "iNVFReyYwvXWNSFbp603JQnXO0Xr1Q07h+u+cA0I18I1Jq2rHa4Tfr7CNVC6e6+9fM+911y+59g1"
                + "B/Yce/6BPceuPmfs69fBfcldJWAzW7iedtVG2n++rIAtWEN24bpXsXB9snUhkO4erftTBeu8AnYs"
                + "4frTO6wJ2Zy2/vtt4frU+XC9ueN652nr7dF6e7g+PXO43vj6XRz9DV0A4Rr8ynk30s/LgQWIKFxf"
                + "fi5cn4vXlx6/au/HRmP1sYP7wrGD+7fYdxHxGuFauAbhOunNGfuZTlcnD9hVWBeSbOJ6pzUhf3/d"
                + "Z8OnrrsQrj+9Ga6vP7XrtPXqDrb+mQzCtetAQLj2oBLRZHW35EnriyagE36c3ZSfp3ANVDtcX3v5"
                + "nl9Yuuwjv7B02UfuaV/Wfecr93783dc+/s/fc83jwrGr94djhzZsDdaH9l9sXMAWr5kyXE8KvtPe"
                + "LDFp8B4XrkVEiDVc90uP1mn2XI9OQ58syCzT14XuuZ4iXP/99Z8Nf3fduXh9/3WPhAeuSxKud47W"
                + "F4XrVvpwPfq+Mu76FDLoLVP3DRCuMVkdh8x2T2f0eXoDAaKZuN6I1uFdN+8Nv/SSJ4X3/Njjw7Gr"
                + "D4Rjhw6MxOkL8fr4oR0C9pZJbDuvEa4B4TpZvC5aNeJ1wnUhY8L1p657JNx/3ZZVIdeni9Zbw/VD"
                + "i6fD2hQ3ZhwTrl0PUmRv8XWGcE31fkJX08nqxJPXk8J1wglt4RqovGPXnlsTck/7su67bt4b3v2S"
                + "veHeaw6ci9YTwvWx8ytEEsRrU9fMEK7HBaxZA9i4f8+sgRwoJ1z3IgnXD1cgXM+yOmS1yHi9eHG8"
                + "vjhcn5oyXG+P1GsjRuP1LOE66XUn5NBbTGIjXNPYcF33C+duzp+vNwoglnB96btu2fuJe69/Yjj2"
                + "Y5dfiNbnw/WBXcP1+f3X4jXCNdC4cN2fKVxXMV6vFhmvFydPXW+G6/t3WhWyZU3I6LT12Gg95nM+"
                + "Oftua9eFxNRbfN0hXFOdXUhT7k6qfbjO+fP1k04glveH3/3bdz8uvO/FB8Kx5ycN1/svWgcyaTLb"
                + "yhCyCteAcB3HupB+LcJ12ni9Gk283pi6Ht1zvcPNGR9MHa3728x6U0aT18QYrn3dIVxTlRe+bkMn"
                + "qqMJ475GgVLfHw7uC7/5kn3h+DWXh2NXTxmurxq3MmS/qWuEa0C4rlG8Xi06Xi9OXhmyeZPG8+F6"
                + "I2Q/cP25GznuFK1H14LsFKtPzhCtU4RrE7CU2WN83SFcE3+4buhEdTSrSABKfH/46PGD+8L7rt2/"
                + "MW19+Q7Reku83iVcJ5q6Fq7Zwa9dPRfec+jxYbl9WLAGZgrXxcTrfiK9CgbsJPE6ixid+t+7uD1g"
                + "77Yy5Hy03vjvH9zy53e6AePohHUWwdrKEDL4TXkDdQjXCNcI10Dj3x9+9z3XPjEcv+bARrROF66P"
                + "jYZrU9dM4e6Xf1d4x48/Pbz16L8Pty/dHM50rhDqgKnDdb67rvu5husYInYe4XptCpPi9Ykd1oZs"
                + "xuutk9a73oxxTLDOMloL11Sk2/j6Q7imMT+xwxsDUJ17Hvze8ecfCMevPjAhWs8erk1ds9tqkKO3"
                + "3hj+9PXfFu69/WqRDpg6WOc3ed2fSlbhM6Z4XVS43vHvW0wwfX39hUnr2YL1oxuKWxVi8tX53CpT"
                + "hGv8xA7hGvC+sMV9h/Zu32s9btp6t3C9cePFc+H6gHDNVDutz3Tmw9pdXyvWAZmE69kDdn9mvRxE"
                + "G6+3BuIMw/WOf+9IjN4pYj90PnCvh9XFflhbHB+r85iyniJcu050PtcpEK7xAkh8N2f0k02gwAnr"
                + "be8L771qT7jv0GXh2NX7U05b7xCuz09cJ7mZo/cB3IwRKCZc7xyyi9OrcMBOG5DHxeukYfrkFlNF"
                + "7fN//5YbMSb+O6cP2KNfp1MG68pOvu5y36yu69705/NYOoVrKYRrhGv8ZBMo9X3gvqsuC8e3Tlwf"
                + "miVcb64ISRKuxWuEa6CccD2zG9IH8Lw/pvzi9Q6xd2PdxmoSu8Trk2Oi8W4fy852/jPTPDazTlZn"
                + "FK4rd32YsjsI19XoNPoEwjV2W7P7BHbefK9AbSc2Ek5w7AvHD+4Nxw/t2xKc04Xr4xeF6913YgvX"
                + "CNZAZYP1SLjuRTR9nWfA3ilaJw7XizuH63TBOr/gPOu/YzRQZxSsKzP5OmV3aOz1aMU6jW6AcI1J"
                + "a/wEFSjx9X4zIh/aN0W0vjhcXxy/J9zM0euwcC1cA1UM1ylXj5T1ceU2db04+9T1rKs5YpFzuI7+"
                + "um3G7tB1btcNEK7xEzzwE1Ro7ER18nB9/Or9qaP1sV2nrYVrhGugmeE61o8zy6nr1Rkmrjejda8G"
                + "Mg7UwrVOoxsgXOMneOAnqOB1fXu43n/OTvH6YIJp60NjovWhCbF7148tAa/flfLuQ08K9x68PHRe"
                + "/p3h51/8deG3XnAgHF16pXAN1CJcVzG6Tlq9MTZeb522biUL16NrQnrCtXDdvHCtGyBcC9cm8oRr"
                + "/AQVajlZnXO4vhCvj129f8Kk9Ui4PrR/+ps5zhKtxevKufvl3xne8ePfEH7m6EvC7Us3h7994zPC"
                + "e+84GF6zfKNIB4wN11UK2HU28YaNu9i6FqRuwbqocB3b9VpG51ThWjdAuMYLITg4gNfxCeF6y9T1"
                + "sfPxev/uu603//eUN3OceIPGg1Pw+l2ZtSBHb70x/Onrvy3ce/vVYdC5Ipy++2nht1/zXSId0MBw"
                + "/WhNprQn32ixjiG/4HAd3fXajOG6cQG0Zr1GNxCuhWumnsxzYYw3IHCgnTpcH9vmQDh29cjaj9H/"
                + "PfUNHbffzPGiqeuDM/AaXpl91mc682Htrq8NZ4Q5oPHh+tEdo3AVQ3bTJtALDtaxT1677hSuEa6F"
                + "a0xY4w0IvK7nGK4PThOnD6S+oeNF4frg3ux4HXcjRkC4rnTEftRaEuG6ipPXrjuFa4Rr4bouE9Im"
                + "rLGzCog2XB/MIlyPj9bCtXAtXAN1vkkjzQnWZYfrWK7bhOtG9hvdQLgWroVrE9b4CSq4yW7R4Xrf"
                + "hcg8S7w+WNK0tXAtWAPCNTQjXEdz3SZcN7rf6AbCtXA9y0+0qvrxjfnJnAlrvAGByescjAnXU8Xr"
                + "/btEa+FauBauAeGa+gRr4Xq2cD1tFzFhrRsgXNflQr9b1Y9PoMYbEAjXxR14R8P1vouD88SAPSlY"
                + "j4vWwrVwDSBcI1w3MFxP20W6Bk90A4Trulzgx3633a7JarwBAeUfgBOE65kVFK2Fa+EaaMRNGicR"
                + "WanRTRij3zU8a7hO2UW6OU9Iz8o9shCuhevUL5RdP4ED4Rrc1KW8eH18t3B9lWhdV5/s7A+3vbK1"
                + "LSYJcIBwjXBdr+u3gjtHV5/x9YdwXbdfpa763XbBT06BnN9H8gvXE6P1thtFCtd18Z9fuDfcf/xA"
                + "WD58s3ANWCmCYF3j67eiw/Wsn6ffdNcPEK5jfYEUrsFPTsHu63HhOod4PT5a5xCuvV5H5ejzvik8"
                + "9Tl3hqX2YcEaEK4Rrmt8/VZS5+jqMr7+EK7rduFe9d1P4I0HyPH9ZHu8PrZNVjutd9htnUW49lod"
                + "lV9/8d7wnOe+JDzx3/2cSWtAuEawrunka8mTy6k/T5PWJq8Rrit/11rhGoRraO7k9b4J8XpfomCd"
                + "KlrPGq+9RkfnvoN7w+/ftT/cdsv1gjUgXCNc1/g6LpK+0dVjdASE67pdqDdl9xP4iSn4Qe504XrH"
                + "tSHJTIrW940YG6DF6kp63zV7w0d/9RtC24oQwM0ZEaxrGQ4jm1zupuS8piMgXFcm/ArX4Cem4H1x"
                + "t3idImAfTyRFuKaaE9eH9oZ77npBeOvRl4XXLt8guAHCNcJ1PcO1cw86gnAtXOf8Ez3hGjL4SbYX"
                + "cajprutdAvZ09grXNddut8NSux3e9qqXht9/3XeEB+/6esENKCRYi64I1/lPvJpcJouO4BpOuK5z"
                + "uHa3XfATVKCQ95V9k+0WqS/689vXfwjX9Q3XR5ZuDb9y+7XhIdEaEK4Rrmt1vaZnoB8I18K1cA12"
                + "VwGRvK/sy8Au+6uvEq3rGK5ftXRLOPtbnx+GP/8vRTcgs2AtqiJcl3+9pmdgAju+zRTCtXAtXIOf"
                + "nELTVmllFK+9NjYxXN++dHM4+xtfEIZvF64B4Rrhuk7Xa3oGOoJwLVwXe0HerU9YAJPX4OBSxPe6"
                + "WM3Owbrdbod9z7on/Ivv/Nkw/PUvCMO3f5n4BswcrsVUhOvyr9f0DMq4l5bJ7ETfj8J1zS/Euyat"
                + "wU9MoY4HmIS8ZpF5uJ57ztvPrQp5h4lrQLhGuK7D9ZpzIzqDcC1cl/MTPeEaTF6DCW3IKFxv3pzx"
                + "7AefHIb3fKn4BlgRQiXDtclrk9bYjS1YC9cxXFAL1+AnoiBcQ4bheqndDmc/9sQw/KUvDsO3inCA"
                + "cI1wXdXrNedF9IZor9+E60h+xVm4Bm8kgPclKhawxTfAihCsDKnu5LVJa/ymt3AtXMdxAe3mjCBc"
                + "g91oXq8QrgHhGpoWrsdetzkfojsI140L15HeJKprwg28gYCA7TWL7FeGvHb5BhEOsCIEK0MquDvY"
                + "+RCT19EPHAnXDbkwFq5BuAbh2usVedyksX2rGAcI1wjXgP6Q/fWacN2QX0UWrsEbBwjXXq+wMgSI"
                + "LFyPI6pSZsAWskF/iKSnCtcNuSAWrsEbBwjXfjUU4RoQrkG4Bv2hKj1VuG7ITZ+Ea7BrCvA+RY4r"
                + "Q5bbh0U5wCoRrBAB9IcMw3XRn29dw7WfyAgCIFxD9SewTWIzdbg2eQ0I1wjXgP6QSz8Urms6aV3a"
                + "Tyhc+OMnn4BJbKwMAZgtYIuoCNhAHftDyq7aTUq4rvaFrXANfvIJ1OcH0wjXgHANwjXoDwaGMno8"
                + "hGvhGvzkEzB5jXANIFwjYAMNC9c5DwrN3GOEa+EavIEAJq8RrgGEa4RroJnhOtrHpfLhuuIXssI1"
                + "mLwGvJ8hXANuzggCNugOtQ3X0+6+rku49pMYF/pg8hpMXoNwDQjXCNeA7hB3L6x/uK7JhWthP4lx"
                + "gQ/ZvYFsXsiIiGDymjiDtXANTBumRw0W5sJgYW7s/y6eImADSXrfTlPHSYzroRXvqok/36qHayHN"
                + "BT4I12DyehyvecI1gHCNcA2U3h+mvE7JO1xH32uE65pPXruAh/zCtYAN8YVsP7AVrgVrYJpQvRmo"
                + "kxKwiTlgOxNAvP0hbbjOWbR9dJNwXfPJaxfuIFyDcI1wDSBcI1wDlQvXjV2xUtlwXfMJ4tR32fSr"
                + "0lB+uBawIT7tdntxpzDhNVC4BoTqaUL1pIAtmiJcAykmpj02dV0V0rAnuOvxAOEaEK4RrgHhGoRr"
                + "oOZBX7iuyxPpJzUgXOclrwvH2D9PsbfeX7cu5IRroNnBOqtQLVwjXAMU12WEa8ALpHAtXAvXwjXC"
                + "NSBcC9cI1wCFDOjWLlybMAbKDtdVC7OxBOuiH59Jf2/dn0fhWrgWroVrEKzzD9bCNcI1QP49pmrh"
                + "2pMMCNfCtXCNcI1wDQjXIFwDNZ20Fq4B4TqnsNvUMF3241PXlSYI1wjXQPpwnXewFq6rGXTHqevn"
                + "6WwAVL3DCNeAF0zhWrgWroVrhGtAuBauhWvhGiCKSWvhGhCucwqignW1w7WA3axwPe4C1mtls8P1"
                + "6NeDGAhWhIwL1sJ1tUJ10uczKQEbIJ/BQTdnBLxwCtfCtXAtXAvXwrVwDcK1cC1cC9cAUU1aVy5c"
                + "m7wGin4BrXr4jPXCsmqfnyhcj5uKjl64jQuSLvDqEa7HSRu+hGsQrAXraofrrJ7nqgdsZwSgapPW"
                + "VQ/XJq+B3F9IhWvhWrgWrhGuhWsQroVr4Vq4Bih20rqy4drkNZB3uK56+HQRK1wL19MHSRd4zQza"
                + "k36lWsCG8oP0JG7GSBbBum5fF1aGZOu+Qx4DKGrSui7h2uQ1IFwL18K1cC1cI1yDcC1cI1wL14Bw"
                + "HUe4NnkNZPWCWpcA6iJXuBauZ7/pngs7koRvr41QXLguOkAK2MJ1nb4uhGvAqhDhGhCuhWvhGuEa"
                + "4RoQrhGuhWsA4Vq4BmYOEIt1CaEubgVs4Tr/X1H32omVISBYC9jxB2vh2s0ZAeFauAaEB+FauEa4"
                + "xvsHIFwL18K1cA0gXAvXQN1+1VuwFq6pzg9uXPhhZQh1DcTCtYBtRUh2z79wDZDtrmvhGhAchGuE"
                + "a+FauEa4RrgWroVr4Vq4Bohq8lq4BoSGioRRF9kCNuV9P7jww8oQ6hasJxGuswmYWRGn3ZTRTRkB"
                + "4Vq4BoRr4RrhWrgWrhGuEa6Fa+FauBauAYRr4RoQGBCwiw69wrWbNOL9hWYH66ThNbaPr2oB2woS"
                + "4dqKEEC4Fq59MQDCAsK1cC1c4/0FhGvhWrgWrp1bAOFauAYEBYTrqode4dqKELJbRZUVr7HEEITz"
                + "+nqc9PVf9WBddBC1UsTNGa0IAYRr4RoQrkG4Fq5BuEa4Fq6Fa+FauAYQroVrQLhGwBauBWvcTLiu"
                + "N/8T7OO+2WHWz4tAnc/NHpu2WiT2H3yUfXNN5xdAuBauAeEahGvhGoRr4Vq4Fq6Fa+FauAYQrscG"
                + "665wDdQ9ACBkFxF6hWvhGj94LTpMCthxBuuswrVQnW8QjX1lRd6huqo/cBCuAeG6WeHaFwEgXCNc"
                + "C9fCNcK1cI1wLVwL18K180vlvP9F+8JvvWxfuO+QxwLhuhbh2qQ1YGUIQnb+obdpoVq4xvtWPKHU"
                + "+3YcoTircC06N/tmgVW92WJWj//o67pgDQjXzQjXnnxAuEa4Fq6Fa7xvCdcI1wjXwrVzCyBclxOu"
                + "RyasTVoDwjWCdgnBt+ohetYA5LUUq66KC5UC9mw3wcw6vFkRImBnefPESR9H3YL1pNf3rHkPBWIL"
                + "15vqHq492YBwjXAtXAvXCNfCNcI1wrVwLVwDDZm8jiZcj5msNmENCNcI2TUOxAI2JA8bTVlRIWDH"
                + "sYJj1nAtGscZVMu+eeKk8FrXYA0gXNcjXHsyAeEa4Vq4Fq7xviVcC9fCNcK1cA1gVUi54dpkNSBc"
                + "Q3YB22MoYNOckF30+1tZocn7eDlBeNaVBWJxMwJ2VjdlFawBTFrHHK49mYBwDcK1cA3CtXAtXCNc"
                + "C9cADZ+0Lj1cm7QGhGvILmB7zIoNR15bqVPQrkqQbOr7uRBMDDdr9AOK3R9H70kA2U5axxKuPZmA"
                + "cA3CtXANwrVwLVwjXAvXACathWug2Rf24ht1CtceKwEb0gbtSSb9+2IJRYI1ZL8qRKAWrAFimLQW"
                + "rgHhGoRrhGuEa+FauEa4Fq6Fa4CZJqyznrQuLVzbbQ1YGQLTB2yPiZs1Qtnvo7GFo7SEawTrycHa"
                + "YyZYA5QxYR1LuPbkAsI1CNfCNQjXwrVwjXAtXANUZKI67wnr0sK1SWtAuAasDAHh2qoR4Zp4gvVo"
                + "uPaYCdYARU5UxxauPemAcA0I1yBcC9fCNcK1cA0Q+aR17cO1SWvAzRkBK0NAuI4tRAnXCNeCtWAN"
                + "EOekddHh2pMOCNeAcA3CtXAtXCNcC9cAJq2Fa8AFtmANWBkC2byP1i1cx34+EBTJM1y7GWOyx817"
                + "AWDSWrgGEK4B4RqEa+FauEa4Fq4BTFrnH67ttgbcjBEQrqH6wbruYWrSCqCizxNCInn+oEawFq4B"
                + "qjBpXVS49uQDwjUgXINwLVwL1wjXwjVAZBPWsU5a5xauTVoDgjXgJo1eixGqqx700hKsQbgGMGFd"
                + "jXDtiwAQrgHhGoRr4Vq4BuEawC5r4Rpw4e1mjICVISBYTxOo8lo9IlhD9X+w5T0DMGldz1UhVoYA"
                + "wjUgXINwLVwL1yBcAwjXcdyc0U0aAcEaEK5BsG568BKswcoQAOE67nBt8hoQrgHhGoRr4Vq4BuEa"
                + "QLiOI1ybvAbcjBEQrkGwbnrA9viDcA0gXMcdru2+BoRrQLgG4Vq49hiBcA0gXMcRrk1gA2kvqJMS"
                + "y4DYwrULXQRrADdpBBCuqx2uTV4DwjUgXINwDSBcAwjXcYRrk9cgRAvSgJUhIFgDCNcAwnXM4drk"
                + "NQjXwjUgXINwDSBcAwjXcYRrk9fgpokAwjXk9z4sPgG4SSOAcD17uDZ5DcI1gHANwjWAcA0gXMcR"
                + "rk1eg3AN4CaNIFwDCNcAwnXM4drkNQjXAMI1CNcAwjWAcB1HuDZ5DcI1gJUh4KaMAMI1gHAdc7g2"
                + "eQ3CNYBwDcI1gHANIFzHEa5NXoNwDWBlCAjWAAI2gHAdc7g2eQ3CNYBwDcI1gHANIFzHEa5NXkP1"
                + "L5zFK0C4dqGLmzEC1C1ge38HhGvhWrgG4RpAuAbhGkC4BhCuyw/Xm28Im/+53W4vemMAK0IA3JwR"
                + "BGsAq0MAhGvhGhCuAYRrhGsAhGtAuBaut4brSb+S440CBGsAwRqruYRrACtDAIRr4RoQrgGEa4Rr"
                + "AOFalwCE6+aE61nfKLxxgJsxArgZI1aEACBcA2wP15uEa+EahGsA4RqEawDhGsDkdRPDtZUiMDkk"
                + "5/X1P+kHRyIWUPcw7byBYA2A939AuBauhWsQrgGEa7zPC9cAwjWAcF1cuC76V3i8sdCkC9qsvu6T"
                + "3gRK0AasAAE3YwSwIgTArmvhWrgG4RpAuEa4BkC4BqjN5HXp4TqWNxpvODThgjbpr7hPktX3nRgG"
                + "CNYgWANYDQIgXAvXwjXCtXANIFwjXAMgXAMI17EH7FnDni/yat2csO7PW9oL2kkrRfK6QBawgaoG"
                + "bO+xuBkjAM4FgF3XwrVwjXAtXAMI1zjXCNcAwjVAAyevhWurRhr5q7R1+wFFVS9oBWzAyhCwGgRA"
                + "sAYQroVr4dqFnnAtXAMI1wjXAAjXAMJ1XcN10hDni78eATfW57OqF7aCNSBYg2ANIFwDCNfCtXAt"
                + "XAvXwjWAcI1wDYBwDSBcNzVcC9j1XpUR2/MpWAPkE6i9fyNUA+D6HmhKuN4kXAvXCNfCtXANCNcg"
                + "XAO4vgcweS1ce4Or6wVeWRd8sTyvVQ3XIhkQa7j2XksZ5xhRCMCqEADhWrgWroVr4Vq4BhCuEa4B"
                + "EK4BhOvpwrWbNzYzPCfV1INL1S94hWvAzRcRrIVqANfzAMK1cO2NTrgWroVrAOEa4RoA1/MAwnXW"
                + "wbquK0bq9oZX9QAde8Cuy+MmXANWheAm0gAI2ADCtXDtjU64Fq6FawDhGuEaANfzAMJ10eFayPar"
                + "r3UO2HUN/CIZIGAjWAMgYAMI18K1NzzhWrgWrgGEa4RrAFzHAwjXaUNzXp9g1d/4Yn0DFKzzDdh1"
                + "X6kijgHCNcI1AK7/AOIK15uEa+FauHZwEa4BhGuEawCEawCT1zGG66L51aNsb7rokJLP81e3x3n0"
                + "8xHHAAEbwRoAARtAuBauhWvhWrgWrgGEa4RrAIRrAOFawC7njXBSaBao414BU/cVIcI1IFwjXANg"
                + "cAmgGruuy955LVwL18K1cC1cAwjXCNcACNcAUU1gNzZc1/UmjuMulCb9eRdYcT9/df+8pw1KQjgg"
                + "XCNcA2BlCIBwLVwL1wjXwjUgXINwDYBwDQjXwrWAne6N0SGBOgX8ScE6qxAOIFwjXAMgXAPsvvNa"
                + "uBauhWuEa+EaEK4RrgEQrgEaPXktXNckXEOTwrabQAJ5B2sXnMwSrIVrANcxzhGAyWvhWrgG4Vq4"
                + "BoRrhGsAhGsA4bre4VrAhvoFbzEOEKyxIgSAoq9DvDcCVoUI18I1IFwDwjXCNQDCNYBwLVwDAjYg"
                + "XINgDYBzBCBcC9fCNSBcA8I1wjUAwjVAjXdbC9fCNTTmACnKAWkDtsMpgjUAbsYIUM6ktXAtXINw"
                + "DSBcI1wDIFwDCNfCNSBcAwI2gjUAdVgJ4rwACNfCtXANCNeAcI1wDYBwDSBcC9felEG4BnBBimAN"
                + "gFANCNfCtXANCNeAcI1wDYBwDVBiqB4lXAvYgHANCNdEHKyFawA3XwQwYS1cC9eAcA0I1wjXAAjX"
                + "AAVPWtc2XFeNcA2CNYCAjRUhAAjXQNPDddU7r3ANCNeAcI1wDYBwDVCzXdbCtXANFHzzFNENKDpg"
                + "j74OueC1IgQAN2UEiH2XtXAtXAPCNSBcI1wDIFwDlDphLVwL14BwDTQkYI973XHhG3eQnsR7K4Dr"
                + "Cj+QBuo4YS1cC9eAcA0I1y50hWsAhGsA4Vq4zi5cC9jg5owAkwJ20tedpl7oxnLhL0gDYJUIIFwL"
                + "18I1IFwDwrVwLVwDIFwDCNfCtXANCNdAHW7uWNdwPe2vYmf1uAnWAOR5/SGKAcK1cC1cA8I1IFwL"
                + "18I1AMI1sC3MjlHXzyvt5ydcC9eAcA0gWGe5miPtzRQnrSjxXgmAcA3NmCiuS7ge1yuFa+FauAbh"
                + "GkC4Fq4BQLiGaobrqk1eXzRVPSFcJ/38hGvhGhCuAYTrMlZzCNUACNcgXGcwmRz9LuopPz/hWrgG"
                + "hGsA4Vq4BkC4BoqeUE44mRzLLuyxH4twLVynCtcCNgjXALOG66Zd2ArHADTp+kPAhupMKEcykT3z"
                + "xy1cC9fCNQjXAMK1cA0AwjVUbNI6ZfjNeyJ75snqGXddC9fCNSBcAyQL2HW9wLWqAwABuy1oQ4Um"
                + "lguayO6W/HEK18I1IFwDCNfCNQDCtXANsU9a5zyRnduEdcrJ68R/v3AtXAPCNUCtQ7ZgDQBu4gh1"
                + "mLSecdK5G8nHk/jjEK6Fa0C4BhCuAUC4BuoVrgufsE768QjXDQnXAjYI1wBC9s4rQawIAQDhGpoY"
                + "rutCuBauAeEaQLgGAOEaEK6Fa+FauAbhGkDIthIEAARrEK4RroVrQLgGEK4BwPWJcA3CtXAtXBcX"
                + "rgVsEK4Byg7ZgjUACNcgXCNcC9fCNQjXAMK1cA0AwjUI18K1cC1cA9MfDAVsQMgWrgFAsAbhGuFa"
                + "uAaEawDhGgAQrkG4Fq6Fa+EaELABtofsrAP1ON5rAECwBuFauBauhWtAuAYQrgFAuAbhWmQWroVr"
                + "ARsEbIDmBWyBGgCEaxCuhWvhWrgGhGsA4RoAhGtAuBauhWvhGpp8gBS1ADdtFK4BQLAG4RrhWrgG"
                + "hGsA4RoAhGtAuBauheuiw7WADVaFANQ1YHsvAYD01x3iIgjXwrVwLVwDwjWAcA0AwjUI1wjXwrVw"
                + "DVaEANQ9YAvXAGBVCAjXwrVwLVwDwjWAcA0AwjUgXAvXwrVwDVaEADQvYLspIwAI1yBcI1wL14Bw"
                + "DSBcA4BwDQjXwrVwLVwDVoQApA3YgjUACNcgXAvXwrVwDQjXAMI1AAjXgHAtXAvXwjVYEQLA6IW1"
                + "FSEAIFyDcI1wLVwDwjWAcA0AwjUgXAvXwvXkcD36hiNcl2bPBo8FVoQA5ByuBWsAEK5BuEa4Fq4R"
                + "rhGuAYRrABCuAeFauBauJ0v6BiRUFxasR3lsEK4Bcg7X3jsAQMAG4RrhWrjG44twDSBcA4BwDQjX"
                + "wrVwPXu4bmpALjpUW8WCcA1QfMD23gEAwjUI1wjXwrVwLVwjXAMI1wAgXAPCtXAtXE++OWPTA2na"
                + "x6HsbySHIQRrAOEaAIRrEK4RroVr4Vq4RrgGEK4BAOEahGvhWrguM1zXJZCO+3zSBuKqfIM5JDkg"
                + "CtcAbtIIAMI1CNcI18K1cC1cI1wDCNcAgHANwrVwLVwXGa6rGkSb+g3mkNTsg6FwDWBlCACUdR0y"
                + "qioRcNLnIcQjXAvXwrVwLVwL1wjXAMI1AAjXwjUI18K1cD19uI4toPrmErAdGAVrAAEbAKwESRus"
                + "064Wq3vYFvKFa+FauBauhWvhGuEaQLgGAOFauBauEa6Fa+F6lnAda0j1TSVkI1wDCNgAUN6KkFjD"
                + "66Qgm9fNntM+LmWH4XF/f16fL8K1cC1cC9cI18I1AMI1AAjXwrVwLVwjXAvXRQZU30xCNm7KCFUI"
                + "n3nxGAvYAGBFSLoVF1U9Bxb9uE37+YrJwrVwLVwL18K1cI1wDcK1x1i4BgDhWrgWrhsYrDdpW8K1"
                + "cF1gOPXNIVxjRQjUMXjGeoGBYA0AsYfrtCG3bueiogL2rI9bVcN1RW8+KVgL18K1cC1cI1wDwrXn"
                + "T7gGAOFauBauhWvhWrhuXLhGwEa4hiasCIn9VzsRrAEglpszJl310bRValk93nk9brGtPpl1hYxw"
                + "LVwL18I1wjXCNbiwEK6FawAQroVr4Vq4Fq6Fa+FauCZ9wBbG3ZQRqMavSno9yPaCy3sNAKS/Lkl7"
                + "Dhr3zxvUyffmj3W/iXnNbzYpXAvXwjXCtXAtXAPCtXANAAjXwrVwLVwL18K1cI3VJFgVAn6FU8AW"
                + "rAGg9itEkr7/GthJd47xmNTrXC9cC9fCtXCNcC1cA8K15024BgDhWrhGuEa4Fq5B2LYyBKwIcbNG"
                + "oRoAmnK9kjZYG9jB+V64Fq6Fa+Ea4Vq4BoRr4RoAEK5BuEa4Fq5BwLYyBBxsrQwRrAGguQM3rndw"
                + "vheuhWvhWrhGuBauAeEa4RoAhGsQrhGuhWsQrq0MAQfavVaGCNYAgEEd3KRRuBauhWvhGuHaQU6g"
                + "AuFauBauAUC4BuEa4Vq4BuHagQ6INlw3fXWIYA0ArnOghud94Vq4Fq5BuHagA4Rr4RoAcJ2D875w"
                + "LVwL18I1CNcOdOAAK2AL1QCA1Yg4/wvXwrVwLVwjXAvXgHAtXAMAwjUI1wjXwjUI18I1OLgK2II1"
                + "AOB6B+d/4Vq4Fq6Fa4RrBzlAuBauAQDXOyBcI1wL1yBcO8hBA276V9VwXdWAPfr4C9YA4HoHhGuE"
                + "a+EahGsHOXBgFa6FawDA9Q7CtXAtXAvXwjXCNQ5y4MAqYAvVAIDrHVwPCNfCtXANwrWDHCBcC9cA"
                + "gOsdEK6Fa+FauAbhOruDnAMdWBFS9YBtJQgA4HoH1wXCtXAtXINw7SAHCNfCNQDgegeEa+FauBau"
                + "Qbj2K3TgVwKFa6EaAHCdQx0CdkHXC8K1cC1cg3DtQAcI18I1AOA6B4Rr4Vq4Fq4RqvErdOBXARsT"
                + "sIVqAEC4xnWDcC1cC9fCNcK1cA0I18I1ACBcg3CNcC1cg3DtYAcx/KpeVpoSrPMO10I1AOD6BgFb"
                + "uBauhWvhGuHawc7BDuFauBauAQDXNyBcI1wL1yBcWxkCbp4oXFsJAgCuP/Li7EuDA7ZwLVwL1yBc"
                + "C9cgXAvXwjUAIFyDcC1cC9fCNcI1hR8gHSYQrkkasNO+fgjVANC80OxMCrkHbOFauBauQbgWrkG4"
                + "RrgGAOFauAbhWrgWroVrhGuEbIjv5idMCNmTnhevuQAQf4h2poTybyovXAvXwrVwjXCNcI1wjXAN"
                + "AMK16wAQrhGuhWsQroVssCKkKQHbihAAKC9EOxtC7QdxhGvhWrgG4dqB2MEX4RrhGgCEayCKcN3d"
                + "pCEJ18I1CNcOyA7EWBHCDDdv9FoKAAZKgMwGcgRr4Vq4BuEa4RrhGuEaAIRrQLgWroVr4RqEawdp"
                + "EKxrF64FbQAwKAJkF66tChGuhWsQrhGuEa4RrgFAuAaEa+FauBauEawRsmFyoBas6xW6vYYC4LwM"
                + "YFWIcC1cC9cI1ziII1wjXAOA8zIgXCNcC9cgXDuYO3yQZbAWfQVsAHAeBqwK0ZKEa+EahGsc1BGu"
                + "Ea4BwHkYKD9cm7wWroVrEK7r8auHsX18DiEI1QjYALipIsDMN6EXroVr4RqEa+FauEa4RrgGAOEa"
                + "EK6Fa+FauEa4LvBAmVXoTfv3ZP339lr9bWIL2A7wZHAwQ8AGADdXBFwnCdfCtXANwrVwLVxTq3Dd"
                + "TUg8Fq4BQLgGhGvhWrgWrhGsizlgjobdcWYN0En/nnF/b9afn3CNYJ38YCdcxxmwY12BBICbLAKU"
                + "uFJxcfM6ZvPPaUzCtXANwrVwLVxT73Cd1QS2CW/hGgDhGkC4Fq6Fa+Ea4TqbcJ02bGf1700bmmcN"
                + "8sI1DdxpnfpX6WYN1ya88w3ZYgoAgjVAelqUcC1cg3AtXAvX5BOup51knjZcTzsZPe2Et0ls4RoA"
                + "4RpAuBauhWvhmrK02+3FLUJesg7LRRkXZrL6vMr6FXwHf8E6g0nraSeZp755Sd7h2iS2kA2AYA0g"
                + "XAvXwrVwTVzhOuRNuBauqXy4vmgaecpJ6aLCdeqPM+NJb+EaAIRrAOFauBauYVq9Vn+lqlG5jICd"
                + "d4gXrikiWBe1m3pMcC4sXGf1Oilcu5kjAII1gHAtXAvXwjXCtXAtXBNXuE48uVxSuB67n1q4Fq4B"
                + "EK4BhGvhWrgWrqlmsF4RruMO5MI1VdhlnTZcTxuWs96ZLVwL2QDEG66d5QDhGuFauMakNcI1wvVM"
                + "u6yLCs7CtXANgHANIFwL18K1cI1JayK8KaQLAgqYtO4WFH67Kf/5rnBd75AtaAMI1s6ngHCNcC1c"
                + "I1yLw8K1CwPhuuhwPfUk94RwLFwL1wAI1wDCtXAtXAvXmLTGyhDstC5WWeF6wqQ3VooA4DwKVDgM"
                + "5/X3CNfCtXANJq2FaxcKXByuF4Vrk9bCNQDCNYBwLVwL18I1Jq2pdMB2oVCvA1lGobWx4XrMShNR"
                + "WcgGIMdg7TwKlL2CI++/R7gWroVrMGktXAvXwrVwbcJauAZAuAZcHwnXwrVwLVxTgQlrk9Y1DdhZ"
                + "BRwXCvU6kAnXdlkL2gAI1oDrI4Rr4Vq4xoQ1wjV1Ctfn422k4Xosk9bCtXANIFwDaELCtXAtXGOX"
                + "NTVaJeKCoVbBetaJ4W5TXz+FayEbADdjBIRqhGvhWrjGpDXCNcVMWndT3mxQuEa4BkC4BoRrhGvh"
                + "WrhGuKb8gO3CodaT1t20u6JjXRUiXCNkA1gNAiBYC9fCtXCNcI1wTU0mrRPuim785LVwLVwDIFwD"
                + "wjXCtXAtXGO3NRHdxNGFRDMmrVMG28ZNXgvXQjYAgjUgWCNcC9fCNSatEa7JZ9J62nDd+Mlr4Vq4"
                + "BkC4BoRrhGvhWrhGuCaioO2ColaT1lMH56ZOXid8PBGyARCsAcEa4Vq4Fq6xKgThmmknracNzVmH"
                + "cJPWCNcAwrXzJSBcI1wL10wXeFeS/rlJf97kNXUK2g5FzZq0ThvE04ptslq4Jk3IFrQBBGtAqEa4"
                + "Fq6F6zLC7krKALxi8hrhmkgnrfMO19MSrhGuAYRrAOEa4Vq4JmXQXZngonBdlQls4ZpZArZDUlwH"
                + "tZQ7mKML17FMZk/4+8VbUoVtcQpAsAYEa4Rr4ZqYQ+5KVVaiCNkI15UP17lPOJcUcIsO1yIswjWA"
                + "cA0I1wjXwrVwXfvVGStV2YUtXCNcC9exhuuCJ61FWARsAKEaiChIC9YI18K1SetirZjARrim4uE6"
                + "6YqPaVeFFB2uRVeEawDhGhCuEa6Fa+G68TcpNIGNcE1mv/KWNlxPu3ojbVCedud2UeHapDUCNsDs"
                + "QTopZz3Ayg+Ea+GaaoZZE9gI1+Qdrme+2WHaf37amy4WdVNGwRrhGkC4BoRrhGvhWrg2aT3lLuxJ"
                + "TGAjXNfvwJZiDUeqlRzeA0xaU37IFr2A2ILytJwfAcEa4Vq4Fq4F2CgmtU1g48Kj8HBduZscVixc"
                + "i6kI14BwLVwDwjXCtXAtXAvXeU9qexwRrmtzk8WucG3SGitEAKG5rNDs/AgI1QjXwrVwLVxXeke2"
                + "CWzhmtzC9cTd1FOGV+FasEa4BoRr50dAuEa4Fq6Fa+E6zh3ZHldceFQvXGcUYIVr4RoBO7rwJl7S"
                + "5MA8er5yfgQEaxCuhWvhusmBdaWgCWyR14UH5YXrcZPZwrVwjXAtXINw7fwICNcI18K1cB1psG56"
                + "WM19Alu4Fq7J/KaMqcP1mD/XbUiYnkQ0RcDOMNi5aSVNWrnhLOj8CAjVCNfCtXBt0rrCE9h+QODC"
                + "g5nD9UXSBtpxf85ENQjXwjXCtXDt/AgI1wjXwrVwbdK60RPYHm8XHsRzsGtKwBauqWPILpuVKcR4"
                + "k0GB2fkREKpBuBauTVqbwPa448JDuBauQbgWrhGucX4EhGuEa+FauJ5xonoch8aCJ7A97i48KO+A"
                + "l3TliHAN1Q3YWYduK1OE5DxuNigo4/wIrmNAuBauhWuBOroJbM+HCw9KD9fnb8ooXINwLVwL18I1"
                + "zo+AcI1wLVwL11aC1HoCOyWPYU0vPFyAxHngG5mwPh+um/I+IFxDfYJ91VdVWJUBAja4fgHhWrjG"
                + "5DUI1w5+DV8RIlyDcC1WC9fFWPcYCNfg+gWEa+FauDZ5DcK1Ax/CNViRko+qvo8I00UG6vWRUD3p"
                + "PyNcg+sXEK6Fa+FauAbh2sEP4RqEa+GazKen16fgsRSuwfULCNfCtXAtXIMLDwc/hGtoVMD23iFc"
                + "4xwJuI4B4Vq4Fq4BFxwOfMI1IFwL1zVe/eGxcI4EXMcgXAvXwrVwDS44HOwoNlh3hWsQrpsSrgVs"
                + "nCMB1zcgXAvX1Q3XKwI2uOBwsDNpDQjXwjU4RwKubxCuhWvh2uQ1uOBwgEO4BoRrK0PAzb7BdQ8I"
                + "18I1wjUI1w5wwrXQB8K1cA3CNeC6B+FauBaud1rJMZZwDcK1Axl2WwPCtXANBiGgWddJrrcQroXr"
                + "2CeahWtwoSFcY9IaEK6FaxCuQbh2HYNwLVzHFa7LmsAWrsGFhgOSSWtAuBawwbkSyPe6yvUZwrVw"
                + "XdVwXdoEtnANLjAcjExaA8K1cA3OlYBwjXAtXAvXYZoJ7Lx2YwvXUP8LDG/OuUwwTyRcA8K1cA3C"
                + "NeA6DuFauK5buC5sMntMEHc4BOGa2UJw16oQQLgWrkG4BlzHIVwL140N11nvxBauoToXGEJysZPO"
                + "acO1yWtAuI4rXAvYOFcCwjXCtXAtXBcfgrMO1yavIYPvy3a7vdhutxcdZGoVrrsTJpmtDAGEa+Ea"
                + "hGvA9R7CtXAtXJu8hnjDddqbcHgTzSYslxyEuxVfcQII11aGgIANCNYI18K1cG3yGoRrB5SKh+vo"
                + "JqztugbhGuEa4RoQrkG4Fq5rG65NXkN8349g8hoQroVrELBBuAbhWriuZLjutfp7MnJJr9W/rNfq"
                + "f9DBEHZf0TOON0VMXgPCtXANwjUgXCNcC9fC9fZoNk2svrTX6j+u1+p/Wa/V/+Zeq3+o1+r/lYMh"
                + "mKjG5DUQX7AWrgRshGvf0yBgg3AtXFdtV3TacL2v1+p/Sa/Vf1av1b+91+r/916r/1Cv1f9cr9U/"
                + "62CISWoT1Zi8BoRr4RqEa0C4RrgWrmsUriOYvN7NB3ut/pf3Wv0X9Fr99/Va/dVeq/9Yr9UfOgxi"
                + "khpMXgNWhAjXIFwDwjXCtXDdjHCdVFEHnj/vtfof7rX6j5isxgS1SWqEa0C4Fq5BuAaEa4Rr4bph"
                + "4TrSyWyT1ZigBuEaEK4bGa4FbIRrQLhGuBauhevZJ7MdzmCGSWtvTiBcg3CNcI1wDQjXCNfCtXBd"
                + "3QlsMGkNwjUgXFsZAsI1IGAjXAvXwnWqCWyHNDBpDcI1CNeClXCNcO11AIRrEK6Fa5PXYNIahGsg"
                + "nmAtWFkZAgI2CNcgXAvXJq9BuAbhGhCuhWsQrgHhGuFauBaud4nXJq9BuAbhGqwIwcoQEK5BuAbh"
                + "WriOMlybvAbhGoRrEK4RrhGuvS6AcA3CtXAdTbg2eQ3CNQjXIFwjXIMVQiBgg3AtXAvXIFyDcA0I"
                + "18I1CNeAcI1wLVwL11aFgHANwjUgTLlJIwjYgHCNcC1cuzkjVDlYb/JmBMI1CNcI1wjXgHANwrVw"
                + "LVyDSWsQrgHh2soQELABARvhWrgWrgHhGoRrEK4RrkG4BuEahGvhWrgG4RqEa8BNGq0MAQEbELAR"
                + "roVr4Vq4BuEahGtAuBauQbgGhGuEa+E67nD9AQcxEK5BuAYEKStDIG3A9roBwjXCtXAtXOcZrj/o"
                + "AAbCNWQUrrsCNgjXCNcI14BwjXAtXAvXWYTrDzuAgXANJq8BAdvKELBCBIRrEK6F65jC9e84cIFw"
                + "DcI1IFwL1yBcg3ANwrVwHUu4vrTX6n/MgQuEaxCuAQHbyhCwQgRwTYNwLVzHEq739lr9TzhogXAN"
                + "wjUgXAvXIFwDrmkQroXrWML1/l6r/4cOWiBcg3ANCNjCNWQdsn3vg3CNcC1cC9fThuvLe63+nzhY"
                + "wfZgvcmbEAjXgHAtXINwDcI1CNfCdfHh+vG9Vv/PHKzApDUI18CkcC1EuUkjuGkjCNcgXAvXwjUI"
                + "1yBcA8K1cA3CNSBcI1wL140M148TrkG4BuEasDLEyhCwMgQQrhGuheuYwvWBXqv/xw5UIFyDcA0I"
                + "18I1CNeAaxqEa+E6lnC9r9fqf9KBCoRryChYd4VrEK4RrkG4BuEa4Vq4Fq5nDdeX9Vr9TzhQgXAN"
                + "wjUgXAvXIFwDrm0QroXrWML1Jb1W/2MOVCBcg1UhgHDtJo0gXAOuaRCuhetYwvWeXqv/Ow5UIFxD"
                + "TpPXJrBBuEa4Rrj2vQ/CNcK1cC1cTxWuP+xABcI1mMAGhOsyAvaSsxeNCNdeP0DARrgWroXracL1"
                + "BxyoQLgGu68B4dquaxCuAeEa4Vq4jilcrzhQgXANJq8B4Vq4BitDAAEb4Vq4Fq5BuAaT14BwLVw7"
                + "eyFcA8I1wrVwLVwL1yBcg8lrQLgWrsHKEEDARrgWroVrEK4Bk9cgXOPmjAjXwjUI1wjXwrVwLVyD"
                + "cA0mrwHhuhrhut1eDu32kVqeq07u8J83/7u11npYa62H1dZ6WF087QwqYAMCNsK1cC1c7xiuVwRs"
                + "uPC94M0HhGtAuC5qVci5eH20UeeuzYAtXAvXXgtAuEa4Fq6F693CtclruBCu9/RafW8+YFUIIFwX"
                + "HLCPNiJUnxSumRCyvc6AgI1wLVwL1+PC9Uqv1f+AAxTCtTcfMGkNCNfCdV4B26oQhGsQrhGuhWvh"
                + "Ok24NnmNcC1cg3ANCNeFuyIMO08N7fbRcKRhK0NA0AYBG+FauBauhWsQrkG4BoTrKD3lfLheFq5B"
                + "uAbhGuFauBauhWsQrqHwndZ2W4NwzUWuDMPOU8OScA1CNgjYCNfCtXA9MVyvCNgI14AJa0C4Lm7i"
                + "WrgG4RqEa4Rr4Vq43j1cm7xGuPbmAyasAeG6sP3WV4Zh5ylhyY5rELRBuEa4Fq6Fa+EahGswYQ0I"
                + "13GsCbkQrpfbtzmTgXANwjXCtXAtXAvXIFyDcA0I12VPXD+lsHC91lo/72RrPZx0/kPIBoRrhGvh"
                + "uqLh2q5rhGtAuAaE69yi9YVVIcslhGvnP4RrQLhGuBauqxiuTV4jXANpd1nbaQ3CNan3W2+G6yO5"
                + "35xxVbimoSHb6w0I1wjXwrVwDVUN1ivCNZisBoTrMm/OuNw+kvvNGbeGa2dAhGtAuEa4Fq6Fa6jI"
                + "pLVwDaknrcU8EK7J5AaNTy1k4hqavjrE6w0I1wjXwrWbNIJwDSatAeGaFOtChGsQrkG4RrgWroVr"
                + "4RqEaxCuAeE6oh3XwjVYGQLCNcK1cC1cp43awjXCNSBcA8JPrqtCrgzL7WXhGoRrEK4RroVr4Vq4"
                + "hovDtTcdEK4B4brccH3E2QyEaxCuEa6Fa+FauIZz4dqbDQjXgHAdx47rZobrkzv8583/bq21HtZa"
                + "62G1tR5WF087uyJcg3CNcC1cC9fCNcI1IFwDwrVwXW7QPrkRr4VrhGsQsBGuhWvhWrhGuAYmheuu"
                + "gA3CNVlE6wvheimDcH2ytT5i+/TyyYqE6pPCNcI1CNcI18K1cD0hWK8I1wjXgMlrQLjOd7/1sHNl"
                + "aLeXw3J7ObPwe3JMCK7SpLVVIWQdrr2OgXCNcC1cm7QG4RpMXgPCNSnidbu9HJZyCte9ioVrEK5B"
                + "uEa4Fq6Fa+EahGsweQ0I1xGF6+X2clhu35ZZuHbWA+EaBGyEa+FauIaKBetN3mzA5DUgXJd9g8al"
                + "beH6qLMaCNcgXCNcC9fCtXCNSWvA5DWQNFwLQPmE6+0T10ec1UC4BuEa4Vq4Fq6Fa4RrwOQ1IFyX"
                + "uy5EuAbhGoRrhGvhWrgWrkG4BpPXgJAd3Z7rzZUhwjXkG7C95oBwjXAtXFc/XCflEIRwDQjXIFwL"
                + "Qhntul5uLzurpbwp5eriaY8FwjUI1wjXwjUmsxGuAeEacBNH4Vq4RrgGYRrhWrgWrqsxme0whHAN"
                + "wrVd1yBcC0NTRevNmzQuJQrXJ0estfphtbUeTrTWw4nW6XCidTo8tHg6rC6u1/5ct9ZaF64RrkG4"
                + "RrgWrn1RmrxGuAZMXgPCdZ7heqm9lHjSeG3E6ki4XmvAuU64RrgGwRrhWrgWroVrhGtAuAaE69zC"
                + "9RUzhOv1sNY6HVY3o3XrdDixuB5OOvOBcA3CNcK1cC1cOwghXAPCNSBgzxKw2+2l0E4Qri9eFXIh"
                + "XK+eXxNyWrgG4RoEa4Rr4Vq4tusa4Rqw6xoQrmdzLlyn33HtbAfCNQjXCNfCNSavEa4Bk9dAwnAt"
                + "FCUN1puP1XQT1852kDxce10CwRrhWrg2ee1whHANwrXJaxCuBaJU4botXINwDcI1wrVwLVybvEa4"
                + "BkxeA0UGbMFo93Cd9Cyzfb91M85vJ8c8Bs61WBkCwjXCtXAtXAvXCNeAcA0I15GF66bH25MT/rfV"
                + "xdPOvgjXIFwjXAvXCNcI14BwDVglMt2KkHbqYHvOeuNC9dZgL1wjYIOAjXAtXAvXwjW1CNabfK+C"
                + "XdeAcF3VcG3iejRe7xzv11rrwjXCNQjXCNfCNcI1Jq0Bk9dAunBtRUj6Setxk8cI17hZIwjYCNfC"
                + "NcI1wjVg8hoQroVrEK5BuEa4Fq6Fa+EahGsweQ0I18L1uHUZaSeRN51srYvfCNhej0C4RrgWrhsV"
                + "rlcEbIRrQLgGhOv8wvUsKzS2hmtnRIRrr0cgXCNcC9cmr0G4BuFauAbhWigqNVyvzhCuTWcjYINg"
                + "DcK1cF23yesVk9gI14Bd14BwHVe4Tv3P7nKzw821I3ZvI1yDcI1wLVwL1yaxQbgGk9dA5YJ10wPR"
                + "6ONQlbPUWms9fHqXcP3Q4umwurgZxcv7OIVzhGsQrBGuhWuynMR2WEK4BuEaEK6F61ij9eJ6eHDx"
                + "dHhg8dTYP/Pg4qnwUOt0eKh1OpyIYHe2eI1wDcI1wrVwjQlshGtAuAasCIl8Rci08Xd1cT2cWDwV"
                + "HtwwduJ6I1ivbhCuqWK49joFgjXCtXDNuAlshyZyCdabfM+BXdeAcN30cH1ywk0ZN1dtrC2uh9XF"
                + "0+GhEbvtzl6NKFxvfq7OwgjXIFwjXAvXmLzGpDVg8hoQriMP12tbbs54csS5//70tgB9YsSkcD3t"
                + "jR/ByhAQrhGuhWvh2uQ1wjVg8hoQrhsWrk9umbZeSzEZnXSS+uQIZ1CEaxCuEa6Fa+Ha5DUI12Dy"
                + "GhCuhesE4XrLKpCEk9HnJrP7YW1CkBarEa5BwEa4Fq6F6yZNXk/icIVwDcI1IFwL11OE6zSrRUxS"
                + "I1yDcA3CtXCNyWyEaxCuAeFauI6GFSC4SSMI1yBcC9fMPpntsIVwDXZdA8K1cC1Yg3ANwjXCtXCN"
                + "CWyEa8DkNSBcJw3WVZq4FqyxMkS8RLgG4Vq4JtsJbIcthGsweQ0I18I1IFyDcI1wLVxj8hrhGjB5"
                + "DYwP1sK1YA1WhiDEntrj8UK4Fq6Fa5PXAjbCNQjXgHAtXAPCNcK1cI1wLVxj8hrhGhCuAStChGsQ"
                + "sBFU6xKyXZsgXAvXmLxGuAbsugbhWriGSp+763YdJlwLqcK1cI1wLVxj8hrhGjB5DVaECNdQk3N3"
                + "3a7DvI4JpE0O2K5JEK6Fa4Rrcpz48L0AJq+JUjchj5VwLVxDNSasV8b8BuwkwjUCqXCNcC1cC9fC"
                + "NSatAZPXxBOuPe9WgwjW0OzztnAtSBJ/yPZcIFwL1wjXCNeAgNm4CWvPu3AtXEM9JqwzujdRtJPY"
                + "wjXCNQjXwjVuzohwDQiYDZ6w9rwL1sI1OF/HOIBUl9c3Z0oB29cNwrVwjUlrhGsg313Xk3Yfdyl1"
                + "R/TYj8WOc+FauIZmT1jPOIktXAuQwrWvG4Rr4Vq4Fq4RroFKT2B3PUalTix3a/J54CaMwjXO067v"
                + "ogzXzjgCdhZfL77eEK6Fa4RrHLSB4iawp57sbejjVNruapPXwrVwDdWYtI50FWThE9jCNcI1CNfC"
                + "NXZbI1wDVG8Su1vRjxvBWsCGip6j6xKunUmoQhj32CBcC9eYtEa4Boh1Z3jmu6tNXgvXwjWYtM5h"
                + "Aju3ASbhGuEahGvhGpPWCNcA8U5gdyv28eJmjAI2NOz8nHe4Tvu654xBHQK2xwThWrjGpDUO3gDR"
                + "7gyPbXe4yWvhWriGZk5alzWJPRKuF4VrhGuEa+FauEa4RrgGwOS1VSECNjg3R3G9uBmusw58desn"
                + "AMK1cI1wTc0nRgAweS1cC9fg3JzbJHbqx6vdbi8K1wDCtXCNcI2JEQBMXgvYjQnYkzjj4dxc+vVj"
                + "bo+XiAQgXAvXDh44gANg8hrhWrjGpHXDf0Nxwi7ssYRrAOFauGbaA4dDKMI1gIAtJgvYjQjbznw4"
                + "L7vJGQDCtXBt0hoHcQBMXiNcC9eYtEa4BhCuhWuEa4RrAExeVzlgi87FhGxnP5yThWsAhGuEaxzI"
                + "ATB5jXAtXGPSGuEaQLgWrrHbGuEaAJPXwjXjArazH87HwjUAwjUmrXEwB8DkNcK1cI3zMcI1gHAt"
                + "XGPSGgdzAExeuzkjO4XrpfZyuP3wHeGNh+9yBsT5WLgGQLjGpDUO5gAI1wjX5TrSPhrefGsnvPeW"
                + "Xw+/c9PHnQFxPhauARCuMWmNgzkAwjWCdXkGnSvCQ2/6+vD+n/oRK0NwPhauARCuvZmatMbd0gGw"
                + "6xrhunyn735aWLnze8PrjtwgXCNcC9cACNfeTIVrHMgBMHmNmzDGeZNGARvnZOEaAOEa4RoHcgBM"
                + "XgvXwrVwjd9IRLgGEK6Fa7uthWuEawBMXlsJwqSA7UyI87FwDYBwTfUnrVci57DtYA6AyWvhGuEa"
                + "52PhGgDhWrhu2KT1SkODvYM5AJi8thqkxitDjrSPOBvifCxcAyBcU6Fwe9FUc0XCvQlsB3MAhGvh"
                + "ml0tbTw/y+12eNutb3c2xPlYuAZAuKZK4drj4WAOAMK1YF1X//imrwovfOnRcN017wsnWo84H+J8"
                + "LFwDIFwTSaiduDe6JqtTTGA7mANg13WufulHn3j+/19qt8NS+7AwXAGPveXK8Mbrfyx83ff+h/Dx"
                + "V/yx8yHOx8I1AMI1key2XhH2HcwBwOT19JYO3xraG6H66NKt4Y7lmwXhivmb135DeObzXhfe9cr/"
                + "GD51wz84Izofe50XrgEQrikwyNZuojrSm1c6mAMgXDd0LcidyzeGX3rVC8Jv/vQPisEVnLq++cWv"
                + "CG+79a3hj17x186Izsde54VrAIRrigzXHiuT174uABCuswvVW92+dEt49C1fLgJX0KBzRfinN391"
                + "+JmbfiK84fCd4S9vfNAZ0fnY67xwDYBwTY4htrGT1SavHcwBsOu6yHD9qqVbwntuf74IXFGfefPX"
                + "hE/c+UPhfbfcEz5800creZ7L6Xy7kjHnY4RrAOFauBauHbhMXjuYA2DyOktvedmzLwrWom89rN/9"
                + "tPDh1zw3LLUPh3a7XenzXNbhuqHnbgNAwjUAwjU5HQQdsExeC9cACNcZe13roHBdQ2c6V4Q/e8O3"
                + "hjcfve7881rlsDrjpHNuwbbiE9vOy8I1AMI1WYVrj4nJawdxAITr7PdYf+DO7xV7a2z0+a7D+S1t"
                + "uHZed14WrgEQrslzctjByuR1qokaXwMA5LjruptQJfZY/8ZP/bDAK1xXanVF2qnmqlzvFHx+d14W"
                + "rgEQrsHktckRAGo2ed2t2sS2dSDEGK69xpR6fvf4C9cACNdg8toFEAA1Cdfnp6mFa4RrvyFX0iR2"
                + "Vud6j79wDYBwDSavhWsA6hKuq7IjW6hm1NLG18Irb10Orz/8hvDgDf/Hea3Z53rPg3ANgHANJq9d"
                + "CAFQk13X0Yfre178r8J7Dj1hW7ReEq7pzIdffPULw7te9aJw5y3Xh7e1fyocv+W9zmvNPtd7HoRr"
                + "AIRrMHntQggAu7GLsXT4VsGasR57y5XhL17zjPArR64Jdxxecl5r9rne8yBcAyBcg8lrF0IACNfF"
                + "rgZ5w5HFcMfyzWItFznzlivCx+/49nD4xpvCwzd8xnmt/ruwx537PQ/CNQDCNZi8diEEgHBdTLhe"
                + "ah8Ob7/tJwVadrX2hq8JVz3/cLjnVS8OR5de6bzWzHO/50G4BkC4BpPXLoQAEK7dfJF4PHr3U8Lh"
                + "n3xp+P3X/dvwttteGno3nHJea+BktsdGuAZAuAaT1y6EABCucwvXdlmTel1IZz68//B3h8+86avC"
                + "nS97UXjt8g2h3W47r4FwDSBcC9dg8jrrCyCTIwBEGK67WQfsd77oK8MdN71EsGZmj9z1FeFMZz78"
                + "we3fEn7l1deG2/JfGeK8BsI1gHAtXEPjJq9dAAFQ68nrzenqI0u3htuXbg53Lt8Yfnr5JgGWmQ06"
                + "V4QPH/mO8KPPvzXcufyKcHTp1tBrrTuvgXANIFwL12Dy2oUQAML17pba7XDH8s3hF1/1wvAnr//X"
                + "gisZrw65Ijzwuq8N//O1zwnvfNWLwz+8YjX0WqdNWoNwDSBcC9dg8lq4BkC43nnSeql9OLx2+cbw"
                + "Gz/1QyIruXqsc2W4/03fGO6742C4c/kVWU1eO6eBcA0gXAvX0NjJaxdEANQyXC+12+F1R24IH7jz"
                + "e8Ijd3+FuEox09dvenr45VdfGx6+4Z+c00C4BhCuhWswee2CCADhevtOayGVMjz6lqeGj7322eH1"
                + "R1qh3W47p4FwDSBcC9dg8toFEQDC9blwffvSzSIqpfnrNz4z/OxtLw1L7cN2W4NwDSBcC9dg8lq4"
                + "BqBGwbqbNFxvTlibtCYmP3/bT4Sl9uHzX5fOZyBcAwjXwjUI1y6MAGjQpLVwTYx+7raXhKUtX5fO"
                + "ZyBcAwjXwjUI1y6MAGhAuBasqZLRr9dRzmcgXAMI18I12HXtwggA4RpiCteLI+dG5zMQrgGEa+Ea"
                + "Gj957cIIgEqE63HRTxSlBkF70fc+CNcAwrVwDcK1cA2AcA3CNQjXAAjXwjXCtXANAImCdXc0XAvV"
                + "NInXAhCuAYRr4RqavuvaDkUAKjlpLW4iXAPCNYBwLVxDfSevBWsAog7XgjXCNSBcAwjXwjUI1wAg"
                + "XINwDcI1AM0M19BksYVrzwkAMQ00bIZrwRrhWpADAIRrQLgGgJjCdbfdbi8K1wjXwjUAIFwDwjUA"
                + "RLNCbjNci5gI1sI1ACBcA8I1AAjXIFwDAAjXIFwL1wAI15vES4Rr4RoAEK6B8sL1SiQBW7gGQLiG"
                + "YpzdYrjF+f9euAYAhGug9At04RoAgmBNnQ2Hnfn+sDO/NuzM//WwM//JYWf+48PO/AeGnfn7hp35"
                + "d08K1cI1ACBcA8I1AAjXkJXHhp35+4ed+f807MzfNOzMP3fYmf/GYWf+qcPO/NywM/+4YWf+cuEa"
                + "ABCuAeFauAZAuIYinBl25v9s2Jk/POzMf+mwM3/pvmfdc8ms3ydeLwAA4RpoQrg+v2fbcwKAcA2Z"
                + "7rH+m2Fn/rphZ/6JvscBAOEaEK5Thmu/cgqAcA2z673hqzeD9ZlhZ37PL9/0vD1f831v8v0NAAjX"
                + "gHAtXAMgXEPxznTmB390xzMfHnbmf2/Ymf+VYWd+z9N/4PW+twEA4RoQroVrAKpM/KTCPrf+5qf+"
                + "8T03/shtX/bct379vmfds3fYmd/zxGe/w/c2ACBcA5UP1yslBWzhGgDhGma8AePHX/1tL332j776"
                + "Cb6XAQDhGqhNuC558lq4BkC4hukMh535vx925l9x6JpbD/g+BgCEa0C4Fq4BEK6h7Gh9/7Az3x52"
                + "5p+Ux9nQ6wIAIFwDwrWLIwCEa0jjH069+Wk//ZqXvfCL8jobel0AAIRrIKZwXfSua+EaAOEa0vnn"
                + "z939lHt/+abnfWle50JnMwBAuAaiCdclTV4L1wAI15DC/3vLlZ96363f8x1P/4HXXypcAwAI1yBc"
                + "C9cACNdQbrS++ymDjxz9Nz/z3Vcd3Z/nudBrAgAgXAPCtQskAIRrSOofh535F+Z9JvSaAAAI14Bw"
                + "7QIJAOEaknh02Jn/9WFn/ovyPhN6TQAAhGtAuHaBBIBwDeOcHXbmPzfszN8/7My/c9iZ3zPszOd+"
                + "JvSaAAAI10Cs4Xol54B9/u9wgQSAcA07ButHh535Px125jvDzvx3DDvz+4s6F3pNAACEayC6cF3Q"
                + "5PWKCyQAYg7Xk94nJ9mcjBVgmcJw2JlfffiNX/32YWf+G4ed+X1Fnwu9JgAAwjUQe7jOa/JauAZA"
                + "uIYdJq0fe8uVf/dXr3n6y17/8hc8oaxz4W5/dt+z7rnUawYAIFwDpYXrnCevhWsAKvm+KFyTV7A+"
                + "07ni0X9841f+4X9devYPX/eixcvK/PqfEK73es0AAIRrIJZwvZLxBLZwDYBwDecMPnf3U0787Wu/"
                + "/md/+ebnfXkMX/8TwvV+rxkAgHANRHeBLlwD4H1RuCYbZzrzw39441f95b23fN8trR+/7vK8vq4z"
                + "DtcHvGYAAMI1EGu4nplwDUCdw/WWeC3Qct7JN3xN+OxdTwufvetp4f/e9RX9P/3pZ3yys/j8H/vW"
                + "H7xzf55fz8I1ACBcAwCAcD06dX2paMsOPjfszH/JsDN/SRFfz1mFawMHAIBwDQAA9QjXJq/ZycOb"
                + "Xx9FfC0L1wCAcA0AAMK1cM1utn19FPV1PGvotuINABCuAQBAuEa4zvRreJZw7d4kAIBwDQAANQjX"
                + "I/FarBWqt8kqBOf1tbv1Y3NTbQBAuAYAAOGahoXrtEE471g9zeQ2AIBwDQAAwjUVDda7heskUbjs"
                + "aC1cAwDCNQAAVDxcr7/5qcJ1g+UZhoVrAADhGgAA4XoqL3/RonDdwJUfZcRh4RoAEK4BAEC4TuTJ"
                + "z3m7cC1cRxWI00x0e20AAIRrAACoYbjeIXSKvw3ZVW26GQBAuAYAAOGaWoZr8RoAQLgGAEC4Fq5J"
                + "FKyLDNcCNgCAcA0AgHAtXBNluBayAQCEawAAhOs84rUYXINwXWasFrMBAIRrAACE66wjthhcITEH"
                + "anEcAEC4BgBAuBauhWmEawBAuAYAAOEa4Vq4BgAQrgEAEK4FbMEa4RoAEK4BAEC4RrgWrgEAhGsA"
                + "ABCuBWuEawBAuAYAAOFauBauhWsAAOEaAACEa4Rr4RoAEK4BAEC4Fq6Fa+EaAEC4BgAA4RrhWrgG"
                + "AIRrAAAQroVr4Vq4BgAQrgEAQLhGsBauAQCEawAAhGvhWrhGuAYAhGsAAKh1uBawhWvhGgBAuAYA"
                + "AOFauEa4BgCEawAAEK6bFq5j+3xFZuEaABCuAQBAuBauhWvhGgBAuAYAAOG6OqFXuBauAQCEawAA"
                + "EK6Fa4RrAEC4BgAA4Xr2kFvX4ClYC9cAAMI1AAAI18I1wjUAIFwDAADVD+pVDdeisnANAAjXAACA"
                + "cC1cC9cAAMI1AAAI18K1cC1cAwAI1wAAIFwL1wjXAIBwDQAACNeCtXANACBcAwCAcC1cI1wDAMI1"
                + "AABQ/3BtRYhwDQAgXAMAgHAtXCNcAwDCNQAAIFwL18I1AIBwDQAAwrVwLVwL1wCAcO1BAAAA4Vqw"
                + "Fq4BAIRrAAAQroVr4Vq4BgAQrgEAQLgWrhGuAQDhGgAAEK6Fa+EaAEC4BgAA4Vq4RrgGAIRrAABA"
                + "uBauhWsAAOEaAACE67oFbBFZuAYAEK4BAEC4Fq7xvQkACNcAACBcnxKwhWvhGgBAuAYAAOFauEa4"
                + "BgCEawAAoPbhOuuQLSIL1wAAwjUAAAjXwjW+NwEA4RoAAITr5hCshWsAAOEaAACEa+Ea4RoAEK4B"
                + "AADhetaQLR4L1wAAwjUAAAjXwjXCNQAgXAMAAAAAgHANAAAAAEDl/P8BAPZB+52M2DB+AAAAAElF"
                + "TkSuQmCC\" transform=\"matrix(0.9999 0 0 0.9999 0.0908 -0.3735)\">"
                + "</image>"
                + "</svg>");
        return buffer.toString();
    }

    public static void main(String[] args) {
        String unicodeStr = "{workContent:'\u6E05\u534E\u5927\u5B66\u5316\u5DE5\u7CFB\u57FA\u672C\u6709\u673A\u5408\u6210\u4E13\u4E1A\u5B66\u4E60'}";
        System.out.println(StringUtil.ascii2Native(unicodeStr));
    }
}
