package com.ip;




import java.io.*;


public class TopK {

    public static void main(String []args) throws FileNotFoundException, UnsupportedEncodingException {
        String csvFile = "./src/houses.csv";


        //Query

        int k=10;
        String mall="reading.love.since.ball.castle.pet.name";
        String school="examples.many.outfits.whenever.see.something.like.look.back.book.lists.designers.page.etc.situation.bought.book.preparing.workshop.combine.two.existing.teams.different.companies";
        String cafe="love.peace.mary.lou.girl.likes.family.invited.dinner.also.finds.one.dragon.trusts.see.names.dragon.tiamat.taking";
        String restaurants="love.peace.friend.loaned.book.read.book.club.loved.much.entire.book.club.groupthing.actually.hear.saying.put.ring";
        int r=1000;
        double lambda= 0.5;

        //Code
        PrintWriter writer = new PrintWriter("./src/Ranking.txt", "UTF-8");
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int count=0;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                double a=0;//cafe1(country[2],country[3],cafe,r);
                double b=mall1(country[2],country[3],mall,r);
                double c=school1(country[2],country[3],school,r);
                double d=restaurants1(country[2],country[3],restaurants,r);
                count++;
                //System.out.println(count);

                double sum=a+b+c+d;
                //System.out.println("SUM: "+sum);
                writer.println(country[4]+" "+sum);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       writer.close();



    }

    private static double restaurants1(String s, String s1, String restaurants, int r) {
        String csvFile3 = "./src/restaurants.csv";
        BufferedReader br = null;
        String line = "";
        int count=0;
        String cvsSplitBy = ",";
        double max=-1.0;
        Double lat=Double.parseDouble(s);
        Double lon=Double.parseDouble(s1);

        try {

            br = new BufferedReader(new FileReader(csvFile3));
            while ((line = br.readLine()) != null) {

                String[] country = line.split(cvsSplitBy);
                Double lat1=Double.parseDouble(country[3]);
                Double lon1=Double.parseDouble(country[4]);
                Double rate=Double.parseDouble(country[2]);
                count++;
                if(Main.get_intersection(country[6],restaurants)>0 && (Main.euclid_distance(lat,lon,lat1,lon1,"K")*1000)<r)
                {
                    double out=Main.preference_score(country[6],restaurants,rate,0.5);
                //    System.out.println("OUT: "+out);
                    if(out>max)
                        max=out;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }


    private static double school1(String s, String s1, String school, int r) {
        String csvFile4 = "./src/schools.csv";
        BufferedReader br = null;
        String line = "";
        int count=0;
        String cvsSplitBy = ",";
        double max=-1.0;
        Double lat=Double.parseDouble(s);
        Double lon=Double.parseDouble(s1);

        try {

            br = new BufferedReader(new FileReader(csvFile4));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                Double lat1=Double.parseDouble(country[3]);
                Double lon1=Double.parseDouble(country[4]);
                count++;
                Double rate=Double.parseDouble(country[2]);
                if(Main.get_intersection(country[6],school)>0 && Main.euclid_distance(lat,lon,lat1,lon1,"K")*1000<r)
                {
                    double out=Main.preference_score(country[6],school,rate,0.5);
                    if(out>max)
                        max=out;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }

    private static double mall1(String s, String s1, String mall, int r) {
        String csvFile2 = "./src/mall.csv";
        BufferedReader br = null;
        String line = "";
        int count=0;
        String cvsSplitBy = ",";
        double max=-1.0;
        Double lat=Double.parseDouble(s);
        Double lon=Double.parseDouble(s1);

        try {

            br = new BufferedReader(new FileReader(csvFile2));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                Double lat1=Double.parseDouble(country[3]);
                Double lon1=Double.parseDouble(country[4]);
                Double rate=Double.parseDouble(country[2]);
                count++;
                if(Main.get_intersection(country[6],mall)>0 && Main.euclid_distance(lat,lon,lat1,lon1,"K")*1000<r)
                {
                    double out=Main.preference_score(country[6],mall,rate,0.5);
                    if(out>max)
                        max=out;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }


    private static double cafe1(String s, String s1, String cafe, int r) {
        String csvFile1 = "./src/cafe.csv";

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        double max=-1.0;
        int count=0;
        Double lat=Double.parseDouble(s);
        Double lon=Double.parseDouble(s1);

        try {

            br = new BufferedReader(new FileReader(csvFile1));
            while ( (line = br.readLine()) != null) {
                String[] country = line.split(cvsSplitBy);
                Double lat1=Double.parseDouble(country[3]);
                Double lon1=Double.parseDouble(country[4]);
                Double rate=Double.parseDouble(country[2]);
                count++;
                String getdata=country[6].toString();
                if((Main.euclid_distance(lat,lon,lat1,lon1,"K")*1000)<r)
                {

                    if(Main.get_intersection(getdata,cafe)>0) {
                        double out = Main.preference_score(country[6].toString(), cafe, rate, 0.5);
                        if (out > max)
                            max = out;
                    }
                }

            }
        br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return max;
    }


}
