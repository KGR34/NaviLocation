package com.varsitycollege.simplelocation;

public class GlobalUser {

    public static String userID;
    public static String unit_metric;

    public static String replaceID(String userID){

        userID = userID.replace(".","");

        return userID;
    }
}
