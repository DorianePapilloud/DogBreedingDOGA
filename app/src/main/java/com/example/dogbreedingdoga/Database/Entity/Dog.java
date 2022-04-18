package com.example.dogbreedingdoga.Database.Entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dogbreedingdoga.Database.Gender;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Dog implements Comparable {

    String idDog;
    String nameDog;
    String breedDog;
    String breederId;
    @Nullable
    String dateOfBirth;
    Gender gender;
    @Nullable
    int idMother;
    @Nullable
    int idFather;
    String breederMail;
    Boolean pedigree;
    @Nullable
    String profilePicture;
    @Nullable
    String specificationsDog;
    boolean isAvailable ;


    public Dog(@Nullable String idDog, String nameDog, String breedDog, String dateOfBirth, Gender gender,
               Boolean pedigree, boolean isAvailable) {

        this.idDog = idDog;
        this.nameDog = nameDog;
        this.breedDog = breedDog;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;

        this.pedigree = pedigree;
        this.isAvailable = isAvailable;
    }

    public Dog(String nameDog, String breedDog, String dateOfBirth, Gender gender,
               Boolean pedigree, boolean isAvailable) {

        this.nameDog = nameDog;
        this.breedDog = breedDog;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;

        this.pedigree = pedigree;
        this.isAvailable = isAvailable;
    }

    public Dog() {
    }

    public String getBreederMail() {
        return breederMail;
    }

    public void setBreederMail(String breederMail) {
        this.breederMail = breederMail;
    }


    public void setBreederId(String breederId) {
        this.breederId = breederId;
    }

    @Exclude
    public String getIdDog() {
        return idDog;
    }

    public void setIdDog(String idDog) {
        this.idDog = idDog;
    }

    public String getNameDog() {
        return nameDog;
    }

    public void setNameDog(String nameDog) {
        this.nameDog = nameDog;
    }

    public String getBreedDog() { return breedDog; }

    public void setBreedDog(String breedDog) { this.breedDog = breedDog; }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getIdMother() {
        return idMother;
    }

    public void setIdMother(int idMother) {
        this.idMother = idMother;
    }

    public int getIdFather() {
        return idFather;
    }

    public void setIdFather(int idFather) {
        this.idFather = idFather;
    }

    public Boolean getPedigree() {
        return pedigree;
    }

    public void setPedigree(Boolean pedigree) {
        this.pedigree = pedigree;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSpecificationsDog() {
        return specificationsDog;
    }

    public void setSpecificationsDog(String specificationsDog) {
        this.specificationsDog = specificationsDog;
    }

    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { isAvailable = available; }


    @Override
    public String toString() {return nameDog +", " +breederMail; }

    @Override
    public int compareTo(@NonNull Object o) {
        return toString().compareTo(o.toString());
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name Dog", nameDog);
        result.put("Breed Dog", breedDog);
        result.put("Date of Birth", dateOfBirth);
        result.put("Gender Dog", gender);
        result.put("Pedigree Dog", pedigree);
        result.put("Available", isAvailable);
        return result;
    }

}
