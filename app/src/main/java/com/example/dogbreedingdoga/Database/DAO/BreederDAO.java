package com.example.dogbreedingdoga.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dogbreedingdoga.Database.Entity.Breeder;

import java.util.List;

@Dao
public interface BreederDAO {

@Query("SELECT * FROM breeder")
    LiveData<List<Breeder>> getAllBreeders();

@Query("SELECT * FROM breeder WHERE email LIKE :idBreeder")
    LiveData<Breeder> getById(String idBreeder);

@Query("SELECT * FROM breeder WHERE email IN (:breederIds)")
    LiveData<List<Breeder>> loadAllBreedersByIds(int[] breederIds);

@Query("SELECT * FROM breeder WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    LiveData<Breeder> getBreederByLogin(String email, String password);

@Query("SELECT * FROM breeder WHERE email LIKE :email LIMIT 1")
    LiveData<Breeder> getBreederByEmail(String email);

@Insert
    void insertBreeder(Breeder breeder);

@Delete
    void deleteBreeder(Breeder breeder);

@Update
    void updateBreeder(Breeder breeder);


}
