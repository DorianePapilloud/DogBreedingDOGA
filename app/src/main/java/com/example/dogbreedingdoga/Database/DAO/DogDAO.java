package com.example.dogbreedingdoga.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;

import java.util.List;

@Dao
public interface DogDAO {

    @Query("SELECT * FROM dog")
    LiveData<List<Dog>> getAllDogs();

    @Query("SELECT * FROM dog WHERE breederMail LIKE :breederMail")
    LiveData<List<Dog>> getDogForOneBreeder(String breederMail);

    @Query("SELECT * FROM dog WHERE idDog LIKE :dogId")
    LiveData<Dog> getDogById(int dogId);

    @Query("SELECT email FROM dog, breeder WHERE idDog LIKE :dogId AND dog.breederMail LIKE breeder.email")
    LiveData<Breeder> getDogsBreeder(int dogId);

    @Insert
    void insertDog(Dog dog);

    @Delete
    void deleteDog(Dog dog);

    @Update
    void updateDog(Dog dog);
}
