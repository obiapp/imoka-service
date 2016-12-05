package org.imoka.server.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class JfxUtil {

    public static final String IMGPATH = "/META-INF/resources/img/";
    public static final String STYLEPATH = "/META-INF/resources/css/";
    public static final String PERSISTENCE_UNIT_NAME = "imoka-serverPU";

    public static Boolean debug = true;

    /**
     * Logging message on server
     *
     * @param msg message to be display
     */
    public static void out(String msg) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(df.format(new Date()) + " >> " + msg);
    }

    /**
     * Logging message on server
     *
     * @param msg message to be display
     * @param Group a group
     */
    public static void out(String msg, String Group) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(Group + " " + df.format(new Date()) + " >> " + msg);
    }


}
