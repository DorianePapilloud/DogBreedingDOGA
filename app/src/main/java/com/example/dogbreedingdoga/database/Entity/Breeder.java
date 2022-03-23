package com.example.dogbreedingdoga.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "breeder", indices = @Index(value = {"email"}, unique = true))
public class Breeder {

    @PrimaryKey
    int idBreeder;
    @ColumnInfo (name = "nameBreeder")
    String nameBreeder;
    @ColumnInfo (name = "surnameBreeder")
    String surnameBreeder;
    @ColumnInfo (name = "addressBreeder")
    String addressBreeder;
    @ColumnInfo (name = "phone")
    String phone;
    @ColumnInfo (name = "email")
    String email;
    @ColumnInfo (name = "password")
    String password;
    @ColumnInfo (name = "pictureBreeder")
    String pictureBreeder;
    @ColumnInfo (name = "descriptionBreeder")
    String descriptionBreeder;


    public Breeder(int idBreeder, String nameBreeder, String surnameBreeder,
                   String addressBreeder, String phone, String email, String password,
                   String pictureBreeder, String descriptionBreeder) {
        this.idBreeder = idBreeder;
        this.nameBreeder = nameBreeder;
        this.surnameBreeder = surnameBreeder;
        this.addressBreeder = addressBreeder;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.pictureBreeder = pictureBreeder;
        this.descriptionBreeder = descriptionBreeder;
    }

    public int getIdBreeder() {
        return idBreeder;
    }

    public void setIdBreeder(int idBreeder) {
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
}
