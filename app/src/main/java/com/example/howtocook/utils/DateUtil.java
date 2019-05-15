package com.example.howtocook.utils;

import java.util.Date;

public class DateUtil {

    public static String getcurrentDate(){
        Date date = new Date(System.currentTimeMillis());
        String time = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+" "+date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
        return time;
    }
}
