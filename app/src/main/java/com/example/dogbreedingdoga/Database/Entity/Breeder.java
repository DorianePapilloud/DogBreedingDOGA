package com.example.dogbreedingdoga.Database.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Breeder {

    String idBreeder;
    String email;
    String nameBreeder;
    String surnameBreeder;
    String addressBreeder;
    String phone;
    String password;
    String pictureBreeder;
    String descriptionBreeder;


    public Breeder(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public Breeder() {
    }

    @Exclude
    public String getIdBreeder() {
        return idBreeder;
    }

    public void setIdBreeder(String idBreeder) {
        this.idBreeder = idBreeder;
    }

    public String getNameBreeder() {
        return nameBreeder;
    }

    public void setNameBreeder(String nameBreeder) {
        this.nameBreeder = nameBreeder;
    }

    public String getSurnameBreeder() {
        return surnameBreeder;
    }

    public void setSurnameBreeder(String surnameBreeder) {
        this.surnameBreeder = surnameBreeder;
    }

    public String getAddressBreeder() {
        return addressBreeder;
    }

    public void setAddressBreeder(String addressBreeder) {
        this.addressBreeder = addressBreeder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureBreeder() {
        return pictureBreeder;
    }

    public void setPictureBreeder(String pictureBreeder) {
        this.pictureBreeder = pictureBreeder;
    }

    public String getDescriptionBreeder() {
        return descriptionBreeder;
    }

    public void setDescriptionBreeder(String descriptionBreeder) {
        this.descriptionBreeder = descriptionBreeder;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!(object instanceof Breeder)) return false;
        Breeder b = (Breeder) object;
        return b.getEmail().equals(this.getEmail());
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("Name Breeder", nameBreeder);
        result.put("Surname Breeder", surnameBreeder);
        result.put("Address", addressBreeder);
        result.put("Phone", phone);
        result.put("Password", password);
//        result.put("Picture Breeder", pictureBreeder);
//        result.put("Description Breeder", descriptionBreeder);
        return result;
    }

}
