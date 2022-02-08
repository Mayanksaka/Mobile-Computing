package com.mc2022.template.Controller;

public interface Iselfassismentcontroller {
    public int getindex();
    public boolean istestneeded();
    public void selectedanswer(String answer);
    public void setanswer(int index, Boolean val);
    public boolean islastquestion();
    public String sympton();
}
