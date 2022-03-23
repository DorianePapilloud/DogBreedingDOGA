package com.example.dogbreedingdoga.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.dogbreedingdoga.Database.Gender;

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
    String dateOfBirth;
    @ColumnInfo(name = "gender")
    Gender gender;
    @ColumnInfo (name = "mother")
    int idMother;
    @ColumnInfo (name = "father")
    int idFather;
    @ColumnInfo (name = "breederId")
    int breederId;
    @ColumnInfo (name = "pedigree")
    Boolean pedigree;
    @ColumnInfo (name = "pictureDog")
    String profilePicture;
    @ColumnInfo (name = "specificationsDog")
    String specificationsDog;


    public Dog(String nameDog, String dateOfBirth, Gender gender, int breederId, Boolean pedigree, String profilePicture, String specificationsDog) {

        this.nameDog = nameDog;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
//        this.mother = mother;
//        this.father = father;
        this.breederId = breederId;
        this.pedigree = pedigree;
        this.profilePicture = profilePicture;
        this.specificationsDog = specificationsDog;
    }

//    public Dog(String nameDog) {
//        this.nameDog = nameDog;
//    }


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

    public int getBreederId() {
        return breederId;
    }

    public void setBreederId(int breederId) {
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
