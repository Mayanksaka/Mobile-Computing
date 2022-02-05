package com.mc2022.template;

import java.util.HashMap;
import java.util.Map;

public class Questions {
    Map<String, Boolean> map = new HashMap<String, Boolean>() {{
        put("Fever",false);
        put("Runny Nose",false);
        put("Scratchy Throat",false);
        put("Body Ache",false);
        put("Headache",false);
    }};

    public boolean istestneeded() {
        int count=0;
        for (Boolean sym : map.values()) {
            if (sym==true)
                count++;
        }
        if (count>=3)
            return true;
        else
            return false;
    }

    public String getsympton(int index) {
        return String.valueOf(map.keySet().toArray()[index]);
    }

    public void setvalue(int index,Boolean bal){
        map.put(getsympton(index),bal);
    }

    public String selected_value() {
        StringBuilder s = new StringBuilder();
        for (String val: map.keySet()){
            if (map.get(val)==true){
                s.append(val);
                s.append(", ");}
        }
        if(String.valueOf(s).length()==0)
            return "nothing";
        else
            s.deleteCharAt(s.length()-2);
            return String.valueOf(s);
    }


}
