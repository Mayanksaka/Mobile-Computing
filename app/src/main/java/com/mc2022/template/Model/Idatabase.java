package com.mc2022.template.Model;

import java.util.Map;

public interface Idatabase {
    public String getquestion(int index);
    public void setanwer(int index, Boolean answer);
    public void setname(String name);
    public String getname();
    public Map getdata();
}
