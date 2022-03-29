package com.example.dogbreedingdoga.Database.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.example.dogbreedingdoga.Database.async.dog.CreateDog;
import com.example.dogbreedingdoga.Database.async.dog.DeleteDog;
import com.example.dogbreedingdoga.Database.async.dog.UpdateDog;

import java.util.List;

public class DogRepository {

    private static DogRepository instance;

    private DogRepository() {

    }

    public static DogRepository getInstance() {
        if (instance == null) {
            synchronized (DogRepository.class) {
                if (instance == null) {
                    instance = new DogRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Dog> getDog(final long idDog, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogById(idDog);
    }

    public LiveData<List<Dog>> getDogs(Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getAllDogs();
    }

    public LiveData<List<Dog>> getAllDogsByBreeder(final String breederMail, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogsForOneBreeder(breederMail);
    }

    public LiveData<Breeder> getDogsBreeder(final long idDog, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogsBreeder(idDog);
    }

    public LiveData<List<Dog>> getDogsByBreederByAvailability(final String breederMail, boolean availability, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogsByBreederByAvailability(breederMail, availability);
    }

    public LiveData<List<Dog>> getDogsByBreederByGenderByAvailability(final String breederMail, Gender gender, boolean availability, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogsByBreederByGenderByAvailability(breederMail, gender, availability);
    }

    public void insert(final Dog dog, OnAsyncEventListener callback,
                       Application application) {
        new CreateDog(application, callback).execute(dog);
    }

    public void update(final Dog dog, OnAsyncEventListener callback,
                       Application application) {
        new UpdateDog(application, callback).execute(dog);
    }

    public void delete(final Dog dog, OnAsyncEventListener callback,
                       Application application) {
        new DeleteDog(application, callback).execute(dog);
    }

}
