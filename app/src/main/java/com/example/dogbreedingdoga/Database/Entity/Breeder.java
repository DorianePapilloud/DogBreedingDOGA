package com.example.dogbreedingdoga.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "breeder", primaryKeys = {"email"})
public class Breeder {

    @NonNull
    @ColumnInfo (name = "email")
    String email;
    @ColumnInfo (name = "nameBreeder")
    String nameBreeder;
    @ColumnInfo (name = "surnameBreeder")
    String surnameBreeder;
    @ColumnInfo (name = "addressBreeder")
    String addressBreeder;
    @ColumnInfo (name = "phone")
    String phone;
    @ColumnInfo (name = "password")
    String password;
    @ColumnInfo (name = "pictureBreeder")
    String pictureBreeder;
    @ColumnInfo (name = "descriptionBreeder")
    String descriptionBreeder;


    public Breeder(@NonNull String email, String nameBreeder, String surnameBreeder,
                   String password) {

        this.nameBreeder = nameBreeder;
        this.surnameBreeder = surnameBreeder;
        this.email = email;
        this.password = password;
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
}
