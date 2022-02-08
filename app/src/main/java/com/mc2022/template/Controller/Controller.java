package com.mc2022.template.Controller;

import com.mc2022.template.Model.Database;

import java.util.Map;

public class Controller {
    Database model = new Database();
    Map map = model.getdata();

    public boolean istestneeded() {
        int count=0;
        for (Object sym : map.values()) {
            if ((Boolean) sym==true)
                count++;
        }
        if (count>=3)
            return true;
        else
            return false;
    }

    public String selected_value() {
        StringBuilder s = new StringBuilder();
        for (Object val: map.keySet()){
            if ((Boolean)map.get(val)==true){
                s.append(val);
                s.append(", ");}
        }
        if(String.valueOf(s).length()==0)
            return "nothing";
        else
            s.deleteCharAt(s.length()-2);
        return String.valueOf(s);
    }

    public void Onnext(){

    }
    public String question(int index){
        return "Do you have "+model.getquestion(index)+" ?";

    }

}
