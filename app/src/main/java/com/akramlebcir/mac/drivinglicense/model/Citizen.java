package com.akramlebcir.mac.drivinglicense.model;

import java.util.ArrayList;

public class Citizen {
    private String uid;
    private String sixe;
    private String placeofbirth;
    private String picture;
    private String nationality;
    private String firstname;
    private String lastname;
    private String dateofbirth;
    private String bloodtype;
    private String address;
    private int age;
    private DriverLicense driverLicense;

    public Citizen() {

    }

    public Citizen(String uid, String sixe, String placeofbirth, String picture, String nationality, String firstname, String lastname, String dateofbirth, String bloodtype, String address, int age, DriverLicense driverLicense) {
        this.uid = uid;
        this.sixe = sixe;
        this.placeofbirth = placeofbirth;
        this.picture = picture;
        this.nationality = nationality;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateofbirth = dateofbirth;
        this.bloodtype = bloodtype;
        this.address = address;
        this.age = age;
        this.driverLicense = driverLicense;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSixe() {
        return sixe;
    }

    public void setSixe(String sixe) {
        this.sixe = sixe;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }
}
