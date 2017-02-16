package com.rajsuvariya.diffutilstest.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by kuliza-319 on 16/2/17.
 */

public class ContactApiResult {
    @SerializedName("contacts")
    ArrayList<Contact> data;

    public ContactApiResult(ArrayList<Contact> data) {
        this.data = data;
    }

    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }
}
