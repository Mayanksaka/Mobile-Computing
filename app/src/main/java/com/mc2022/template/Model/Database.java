package com.mc2022.template.Model;

import java.util.HashMap;
import java.util.Map;

public class Database implements Idatabase{
    private String Username;
    private Map<String, Boolean> data = new HashMap<String, Boolean>() {{
        put("Fever",false);
        put("Runny Nose",false);
        put("Scratchy Throat",false);
        put("Body Ache",false);
        put("Headache",false);
    }};

    @Override
    public String getquestion(int index) {
        return String.valueOf(this.data.keySet().toArray()[index]);
    }

    @Override
    public void setanwer(int index,Boolean answer) {
        this.data.put(getquestion(index),answer);
    }

    @Override
    public void setname(String name) {
        this.Username=name.trim().replaceAll(" +", " ");;
    }

    @Override
    public String getname() {
        return this.Username;
    }

    @Override
    public Map getdata() {
        return this.data;
    }
}
