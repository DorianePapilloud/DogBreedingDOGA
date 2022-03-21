package com.example.dogbreedingdoga.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.dogbreedingdoga.Database.Gender;

import java.util.Date;

@Entity(tableName = "dog", foreignKeys = @ForeignKey(entity = Breeder.class,
                                    parentColumns = "idBreeder",
                                    childColumns = "breederId",
                                    onDelete = ForeignKey.CASCADE))
public class Dog {

    @PrimaryKey (autoGenerate = true)
    int idDog;
    @ColumnInfo(name = "nameDog")
    String nameDog;
    @ColumnInfo(name = "dateOfBirth")
    Date dateOfBirth;
    @ColumnInfo(name = "gender")
    Gender gender;
    @ColumnInfo (name = "mother")
    Dog mother;
    @ColumnInfo (name = "father")
    Dog father;
    @ColumnInfo (name = "breederId")
    Breeder breederId;
    @ColumnInfo (name = "pedigree")
    Boolean pedigree;
    @ColumnInfo (name = "pictureDog")
    String profilePicture;
    @ColumnInfo (name = "specificationsDog")
    String specificationsDog;


    public Dog(String nameDog, Date dateOfBirth, Gender gender, Dog mother, Dog father, Breeder breederId, Boolean pedigree, String profilePicture, String specificationsDog) {

        this.nameDog = nameDog;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
        this.breederId = breederId;
        this.pedigree = pedigree;
        this.profilePicture = profilePicture;
        this.specificationsDog = specificationsDog;
    }

    public Dog(String nameDog) {
        this.nameDog = nameDog;
    }


    public int getIdDog() {
        return idDog;
    }

    public void setIdDog(int idDog) {
        this.idDog = idDog;
    }

    public String getNameDog() {
        return nameDog;
    }

    public void setNameDog(String nameDog) {
        this.nameDog = nameDog;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Dog getMother() {
        return mother;
    }

    public void setMother(Dog mother) {
        this.mother = mother;
    }

    public Dog getFather() {
        return father;
    }

    public void setFather(Dog father) {
        this.father = father;
    }

    public Breeder getBreederId() {
        return breederId;
    }

    public void setBreederId(Breeder breederId) {
        this.breederId = breederId;
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
}
