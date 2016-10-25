package com.ip;



import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.*;

public class Main {

    static String[] s1;
    static String[] s2;

    static Set<String> set;

    static List<String> wordList1,wordList2;

    static int k;

    static String base1="https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    static String base2="&destinations=";

    public static Double getDistance(String lat1, String lon1, String lat2, String lon2) throws Exception {
        String origin=lat1+","+lon1;
        String destination=lat2+","+lon2;
        String line, outputString = "";
        URL url = new URL(base1+origin+base2+destination);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((line = reader.readLine()) != null) {
            outputString += line;
        }

        JSONObject jsonObject = new JSONObject(outputString);
        JSONArray rows = jsonObject.getJSONArray("rows");
        JSONObject inside=rows.getJSONObject(0);
        JSONArray inside2=inside.getJSONArray("elements");
        JSONObject inside3=new JSONObject(inside2.getJSONObject(0).toString());
        JSONObject inside4=new JSONObject(inside3.getJSONObject("distance").toString());
        String s[]=inside4.get("text").toString().split(" ");
        Double d=Double.parseDouble(s[0]);
        return d*1000;
    }


    public static int get_intersection(String key,String queryKey)
    {
           String s1[]=key.split("\\.");
           String s2[]=queryKey.split("\\.");
        List<String> list1 = new LinkedList<String>(Arrays.asList(s1));
        List<String> list2 = new LinkedList<String>(Arrays.asList(s2));


        k=list2.size();

        list2.removeAll(list1);

        return (k-list2.size());
    }


    public static int get_union(String key,String queryKey)
    {
        s1=key.split("\\.");
        s2=queryKey.split("\\.");
        wordList1 = Arrays.asList(s1);
        wordList2 = Arrays.asList(s2);
        set = new HashSet<String>();
        set.addAll(wordList1);
        set.addAll(wordList2);
      //  System.out.println("union: "+set.size());
        return set.size();
    }



    public static double euclid_distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;   //Miles
        if (unit == "K") {       //Kilometer
            dist = dist * 1.609344;
        } else if (unit == "N") {   //Nautical Miles
            dist = dist * 0.8684;
        }
        //System.out.println("Distance: "+dist);
        return (dist);
    }



    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static double preference_score(String key,String queryKey,double rate,double lambda){
//        System.out.println("Calculating");
        return (lambda*rate+(1-lambda)*(get_intersection(key,queryKey)/get_union(key,queryKey)));
    }



}
