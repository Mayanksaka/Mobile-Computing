package com.mc2022.template.Controller;

import com.mc2022.template.Model.Database;

import java.util.Map;

public class Controller {
    Database model = new Database();
    int index=-1;

    public void setquestions(int index){
        this.index=index;
    }
    public int getindex(){return index;}
    public boolean islastquestion(){
        if(index== model.getdata().size()-1)
            return true;
        return false;
    }
    public boolean istestneeded() {
        int count=0;
        for (Object sym : model.getdata().values()) {
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
        for (Object val: model.getdata().keySet()){
            if ((Boolean)model.getdata().get(val)==true){
                s.append(val);
                s.append(", ");}
        }
        if(String.valueOf(s).length()==0)
            return "nothing";
        else
            s.deleteCharAt(s.length()-2);
        return String.valueOf(s);
    }

    public Boolean getselectedasnwer(String s){
        return (Boolean) model.getdata().get(s);
    }

    public String sympton(){
        index=(index + 1) % model.getdata().size();
        return model.getquestion(index);

    }

    public String sympton(int index){
        return model.getquestion(index);
    }
    public void selectedanswer(String answer){
        if(answer.equals("Yes"))
            model.setanwer(index,true);

        else
            model.setanwer(index,false);
    }

    public void setanswer(int index, Boolean val){
        model.setanwer(index, val);
    }

}
