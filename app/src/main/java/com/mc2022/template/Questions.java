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
        return false;
    }

    public String getsympton(int index) {
        return (String) map.values().toArray()[index];
    }

    public void setvalue(String val,Boolean bal){
        map.put(val,bal);
    }

    public String selected_value() {
        StringBuilder s = null;
        for (String val: map.keySet()){
            if (map.get(val)==true)
                s.append(val);
                s.append(", ");
        }
        s.deleteCharAt(s.length()-1);
        return String.valueOf(s);
    }


}
