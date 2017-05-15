package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SmartPhoneList {

    @SerializedName("products")
    private ArrayList<SmartPhone> smartphone = new ArrayList<>();

    public ArrayList<SmartPhone> getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(ArrayList<SmartPhone> smartphone) {
        this.smartphone = smartphone;
    }
}
