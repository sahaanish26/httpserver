package com.example.httpserver.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import static java.util.TimeZone.getTimeZone;

public class Utility {




    /**
     *
     * @param file
     * @return checksum value calculated based on content of the file.
     * https://stackoverflow.com/questions/304268/getting-a-files-md5-checksum-in-java
     *  Standard Java Runtime Environment way:
     */
    public static String checksum(File file) {
        try {
            InputStream fin = new FileInputStream(file);
            java.security.MessageDigest md5er =
                    MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int read;
            do {
                read = fin.read(buffer);
                if (read > 0)
                    md5er.update(buffer, 0, read);
            } while (read != -1);
            fin.close();
            byte[] digest = md5er.digest();
            if (digest == null)
                return null;
            String strDigest = "";
            for (int i = 0; i < digest.length; i++) {
                strDigest += Integer.toString((digest[i] & 0xff)
                        + 0x100, 16).substring(1).toUpperCase();
            }
            return strDigest;
        } catch (Exception e) {
            return null;
        }
    }



    /**
     *
     * @param date
     * @return String value of date compliant to RFC1123
     * we can either use DateTimeFormatter or have this method sync if we want to optimize on object creation
     *  SimpleDateFormat is not threadsafe so same instance  can not be used
     */
    public static String formatToRFC1123(Date date){
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(getTimeZone("MST"));

        return dateFormat.format(date);

    }
    /**
     *
     * @param date as String
     * @return Date  compliant to RFC1123
     * we can either use DateTimeFormatter or have this method sync if we want to optimize on object creation
     *  SimpleDateFormat is not threadsafe so same instance  can not be used
     */
    public static Date RFC1123ToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(getTimeZone("MST"));

        return  dateFormat.parse(date.trim());



    }


}
