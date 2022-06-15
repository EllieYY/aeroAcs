package com.wim.aero.acs.util;

import com.google.common.collect.Lists;
import com.wim.aero.acs.model.scp.ScpSeq;
import com.wim.aero.acs.protocol.card.CardAdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @title: StringUtil
 * @author: Ellie
 * @date: 2022/04/12 11:04
 * @description:
 **/
public class StringUtil {
    /**
     * 字符串转unicode
     *
     * @param str
     * @return
     */
    public static String stringToUnicode(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }


    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / n) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }


    public static <T> List<List<T>> fixedGrouping2(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = (source.size() / n);
        for (int i = 0; i < size; i++) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }


    public static void main(String[] args) {
        List<CardAdd> srcList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            CardAdd card = new CardAdd();
            card.setScpNumber(i+1);
            card.setCardNumber("92051148" + i);
            srcList.add(card);
        }
//        List<String> srcList = Arrays.asList("12345", "23456", "34567", "45678", "56789", "67890", "78901");
        List<List<CardAdd>> targetList1 = fixedGrouping(srcList, 100);

        for (List<CardAdd> tar:targetList1) {
            System.out.println(tar.toString());
        }
    }
}
