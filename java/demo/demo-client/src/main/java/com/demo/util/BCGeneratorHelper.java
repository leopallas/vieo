package com.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class BCGeneratorHelper {

    public static String generateExpressBCD(String comId) {
        int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 6; i++) {
            result = result * 10 + array[i];
        }

        String bcdCode = comId + result;
        return bcdCode;
    }

    public static String generateTaskBCD() {
        String rd = "00" + (int) (Math.random() * 100);
        rd = rd.substring(rd.length() - 2);
        String prefix = Long.toHexString(Long.parseLong(getDT() + rd)).toUpperCase();
        String bcdCode = prefix;

        return bcdCode;
    }

    private static String getDT() {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("HHmmss");
        return df.format(cal.getTime());
    }

    public static void main(String args[]) {
        for (int i = 0; i < 10; i++) {
            System.out.println(BCGeneratorHelper.generateTaskBCD());
        }
    }

}
