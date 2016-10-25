package com.ip;

import java.io.*;
import java.util.*;

/**
 * Created by KunalSaini on 25-Oct-16.
*/

public class HilbertIndex {



    public static void FormSet(String filename,String filepath) throws Exception
    {
        String csvFile=filepath;
        BufferedReader br = null;
        String line = "";
        int count=0;
        String cvsSplitBy = ",";
        Set<String> set=new HashSet<String> ();
        br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            count++;
            String[] country = line.split(cvsSplitBy);
            String Keyword[]=(country[6].toString()).split("\\.");
            List<String> list = new LinkedList<String>(Arrays.asList(Keyword));
            set.addAll(list);
        }
        TreeSet myTreeSet = new TreeSet();
        myTreeSet.addAll(set);
        HilbertIndex(filename,filepath,myTreeSet);
    }


    public static void HilbertIndex(String filename, String filepath, TreeSet myTreeSet) throws Exception
    {
        PrintWriter writer = new PrintWriter("./src/"+filename+".txt", "UTF-8");
        String csvFile=filepath;
        BufferedReader br = null;
        String line = "";
        int count=0;
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                count++;
                String[] country = line.split(cvsSplitBy);
                String Keyword[]=(country[6].toString()).split("\\.");
                Set<String> set=new HashSet<String> ();
                List<String> list = new LinkedList<String>(Arrays.asList(Keyword));
                set.addAll(list);
                TreeSet TreeSet = new TreeSet();
                TreeSet.addAll(set);
                String Index="";
                for(Object c:myTreeSet)
                {
                    if(TreeSet.contains(c)) {
                        Index = Index + "1";
                    }
                    else {
                        Index = Index + "0";
                    }
                }
                writer.println(count+" "+Index);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        writer.close();
    }


    public static void main(String []args) throws Exception {
        HilbertIndex.FormSet("restaurants","./src/restaurants.csv");
    }
}
