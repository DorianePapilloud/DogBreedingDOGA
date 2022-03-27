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
public abstract class DogDAO {

    @Query("SELECT * FROM dog")
    public abstract LiveData<List<Dog>> getAllDogs();

    @Query("SELECT * FROM dog WHERE breederMail = :breederMail")
    public abstract LiveData<List<Dog>> getDogsForOneBreeder(String breederMail);

    @Query("SELECT * FROM dog WHERE idDog LIKE :dogId")
    public abstract LiveData<Dog> getDogById(long dogId);

    @Query("SELECT email FROM dog, breeder WHERE idDog LIKE :dogId AND dog.breederMail LIKE breeder.email")
    public abstract LiveData<Breeder> getDogsBreeder(long dogId);

    @Query("SELECT * FROM dog WHERE breederMail LIKE :breederMail AND availability LIKE :availability")
    public abstract LiveData<List<Dog>> getDogsByBreederByAvailability(String breederMail, boolean availability);

    @Insert
    public abstract long insertDog(Dog dog);

    @Delete
    public abstract void deleteDog(Dog dog);

    @Update
    public abstract void updateDog(Dog dog);

    @Query("DELETE FROM dog")
    public abstract void deleteAll();
}
