package com.demo.util;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;
import com.demo.db.*;
import com.demo.util.* ;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import com.itextpdf.text.pdf.fonts.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tools {

    public static final int pageSize = 20;

    public static final String getCurrentDT() {
        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }

    public static String display(Object o){
        if(o == null){
            return "--";
        }else{
            return String.valueOf(o);
        }
    }
    public static int intConvert(Integer o){
        if(o == null){
            return -999;
        }else{
            return o.intValue();
        }
    }

    public static final String getCurrentWeek() {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static final String getCurrentDT2() {
        DateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(new Date());
    }

    public static final String getCurrentDT3() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static final Date getFirstWeekDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
    }

    public static final Date getLastWeekDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return currentDate.getTime();
    }

    public static final Date getFirstYearDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DATE, 1);
        currentDate.set(Calendar.MONTH, 0);
        return currentDate.getTime();
    }

    public static final Date getLastYearDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DATE, 31);
        currentDate.set(Calendar.MONTH, 11);
        return currentDate.getTime();
    }


    private static final int getSeasonFisrtMonth(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        int month = currentDate.get(Calendar.MONTH) + 1;
        if(month <= 3){
            return 1;
        }else if(month >= 4 && month <= 6){
            return 4;
        }else if(month >= 7 && month <= 9){
            return 7;
        }else{
            return 10;
        }
    }

    private static final int getSeasonLastMonth(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        int month = currentDate.get(Calendar.MONTH) + 1;
        if(month <= 3){
            return 3;
        }else if(month >= 4 && month <= 6){
            return 6;
        }else if(month >= 7 && month <= 9){
            return 9;
        }else{
            return 12;
        }
    }

    public static final Date getFirstSeasonDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DATE, 1);
        currentDate.set(Calendar.MONTH, getSeasonFisrtMonth(givingDt) - 1);
        return currentDate.getTime();
    }

    public static final Date getLastSeasonDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DATE, 20);
        currentDate.set(Calendar.MONTH, getSeasonLastMonth(givingDt)-1);
        return getLastMonthDayOfTheGivingDate(currentDate.getTime());
    }

    public static final Date getFirstMonthDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMinimum(Calendar.DAY_OF_MONTH));
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    public static final Date getLastMonthDayOfTheGivingDate(Date givingDt){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(givingDt);
        currentDate.setTime(givingDt);
        currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime();
    }


    public static final String getDT(Date dt) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public static final String getDT2(Date dt) {
        DateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(dt);
    }

    public static final Date parseDT(String dt) {
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dt);
        }catch(Exception ex){
            return null;
        }
    }

    public static final Date parseDT2(String dt) {
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dt);
        }catch(Exception ex){
            return null;
        }
    }

    public static final long getOffsetMins(Date dt) {
        long offset = (new Date().getTime() - dt.getTime());
        return offset / (1000 * 60);
    }


    public static String getPictureURL(String webContext, String usrId, String picId, String picName) {
        try {
            String extensionType = FileUploadHelper.getExtensionType(picName);
            return "/upload/"
                    + FileUploadHelper.getBasePathOfOriginalFileURI(webContext, usrId, picId, picId + extensionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getThumbnailPictureURL(String webContext, String usrId, String picId, String picName) {
        try {
            String extensionType = FileUploadHelper.getExtensionType(picName);
            return "/upload/"
                    + FileUploadHelper.getBasePathOfThumbnailFileURI(webContext, usrId, picId, picId + extensionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSoundResource(String webContext, String usrId, String picId, String sodDesc) {
        try {
            String extensionType = FileUploadHelper.getExtensionType(sodDesc);
            return "/upload/"
                    + FileUploadHelper.getBasePathOfOriginalFileURI(webContext, usrId, picId, picId + extensionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final String getChinese(String s) {
        return s;
    }


}
