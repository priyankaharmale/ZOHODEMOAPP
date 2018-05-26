package com.hnweb.demo.atticzipper;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HNWeb-01 on 6/2/2016.
 */
public class AppConstants {


    public static final String INSERTTDATAGREENBOX_URL = "https://crm.zoho.com/crm/private/json/Leads/insertRecords?";


    // SharePreference
    public static final String HNWEB_SHAREPREFERENCE = "HNWEB_SHAREPREFERENCE";

    public static final String KEY_Id = "KEY_Id";
    public static final String KEY_COMPANYNAME = "KEY_COMPANYNAME";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_LICENCE = "KEY_LICENCE";
    public static final String KEY_ADDRESS = "KEY_ADDRESS";
    public static final String KEY_CITY = "KEY_CITY";
    public static final String KEY_STATE = "KEY_STATE";
    public static final String KEY_ZIPCODE = "KEY_ZIPCODE";
    public static final String KEY_CELLPHONE = "KEY_CELLPHONE";
    public static final String KEY_LANDLINE= "KEY_LANDLINE";
    public static final String KEY_EMAILADDRESS = "KEY_EMAILADDRESS";

    public static final String KEY_YOURWEBSITE = "KEY_YOURWEBSITE";
    public static final String KEY_PRIMARYBUSINESS = "KEY_PRIMARYBUSINESS";









    public static String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

}
