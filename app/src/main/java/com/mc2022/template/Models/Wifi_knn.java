package com.mc2022.template.Models;

import java.util.ArrayList;
import java.util.List;

public class Wifi_knn {

    public static String getresult(ArrayList<Integer> arr, List<Model_wardrive> data){
        int small_1=0;
        int small_2=0;
        int small_3=0;
        double smallestdistance=9999;
        double smallest_2=9999;
        double smallest_3=9999;

        for(int i=0;i <data.size(); i++){
            double difference= Math.sqrt(Math.pow(arr.get(0)-data.get(i).getSsid_1(),2)+Math.pow(arr.get(1)-data.get(i).getSsid_2(),2)+Math.pow(arr.get(2)-data.get(i).getSsid_3(),2)+Math.pow(arr.get(3)-data.get(i).getSsid_4(),2));
            if(difference<smallestdistance){
                smallestdistance=difference;
                small_1=i;
            }
            
            if (difference < smallestdistance)
            {
                smallest_3 = smallest_2;
                smallest_2 = smallestdistance;
                smallestdistance = difference;
                small_3=small_2;
                small_2=small_1;
                small_1=i;


            }
         
                /* Check if current element is less than
                smallest_2 then update second and third */
            else if (difference < smallest_2)
            {
                smallest_3 = smallest_2;
                smallest_2 = difference;

                small_3=small_2;
                small_2=i;
            }
         
                /* Check if current element is less than
                then update third */
            else if (difference < smallest_3){
                smallest_3 = difference;
                small_3=i;
            }

        }
        String[] res= {data.get(small_1).getRoom_number(),data.get(small_2).getRoom_number(),data.get(small_3).getRoom_number()};
        if(res[0].equals(res[1]) && res[0].equals(res[2])){
            return res[0];
        }
        if(res[0].equals(res[1])){
            return res[0];
        }
        if(!res[1].equals(res[2])){
            return res[1];
        }
        if(res[0].equals(res[2])){
            return res[0];
        }
        return res[0];
    }


}
