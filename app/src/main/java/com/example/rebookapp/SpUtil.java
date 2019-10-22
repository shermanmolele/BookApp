package com.example.rebookapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SpUtil {
    public SpUtil () {}

    public static final  String  PREF_NAME = "BooksPreferences";
    public static final  String  POSITION = "position";
    public static final  String  QUERY = "query";

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static String getPrefString (Context context, String s){
        return getPrefs(context).getString(s, "");
    }

    public static int getPrefInt (Context context, String s){
        return getPrefs(context).getInt(s,0);
    }

    public static void setPrefString (Context context, String s, String v){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(s, v);
        editor.apply();
    }
    public static void setPrefInt (Context context, String s, int v){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(s, v);
        editor.apply();
    }

    public static ArrayList<String> getQueryList (Context context) {
        ArrayList<String> queryList = new ArrayList<>();
        for (int i =1; i<5; i++){
            String query = getPrefs(context).getString(QUERY + String.valueOf(i), "");
            if (!query.isEmpty()){
               query.replace(",", " ");
               queryList.add(query.trim());
            }

        }return queryList;
    }
}
