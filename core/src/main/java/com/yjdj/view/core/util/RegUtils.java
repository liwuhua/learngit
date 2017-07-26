package com.yjdj.view.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangkai on 2017/5/26.
 * 正则工具包
 */
public class RegUtils {
    public static final String dateReg = "^\\d{4}/\\d{1,2}/\\d{1,2}$";
    public static final String dateReg2 = "^\\d{4}-\\d{1,2}-\\d{1,2}$";

    /**
     * 正则匹配字符串
     * @param str
     * @param reg
     */
    public static boolean match(String str,String reg){
        //编译正则表达式
        Pattern pattern = Pattern.compile(reg);
        
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static void main(String[] args){
        System.out.println(RegUtils.match("2017/05/2",RegUtils.dateReg));
        System.out.println(RegUtils.match("2017-0-2",RegUtils.dateReg2));
    }

}
