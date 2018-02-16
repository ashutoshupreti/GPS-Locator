package com.example.ashutosh.gpstracker;

/**
 * Created by ashutosh on 14/02/18.
 */

// Class to store contact information in a database

public class Contacts  {

    private int _id;
    private String contactNumber;
    private String contactName;

    public Contacts(){
        // empty constructor
    }

    public Contacts(String contactNumber, String contactName) {
        this.contactNumber = contactNumber;
        this.contactName = contactName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
