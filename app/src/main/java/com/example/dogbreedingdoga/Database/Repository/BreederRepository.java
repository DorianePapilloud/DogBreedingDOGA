package com.example.dogbreedingdoga.Database.Repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.async.breeder.CreateBreeder;
import com.example.dogbreedingdoga.Database.async.breeder.DeleteBreeder;
import com.example.dogbreedingdoga.Database.async.breeder.UpdateBreeder;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;


public class BreederRepository {

    private static BreederRepository instance;

    private BreederRepository() {
    }

    public static BreederRepository getInstance() {
        if (instance == null) {
            synchronized (DogRepository.class) {
                if (instance == null) {
                    instance = new BreederRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Breeder> getBreeder(final String breederId, Application application) {
        return ((BaseApp) application).getDatabase().breederDao().getById(breederId);
    }

    public void insert(final Breeder breeder, OnAsyncEventListener callback,
                       Application application) {
        new CreateBreeder(application, callback).execute(breeder);
    }

    public void update(final Breeder breeder, OnAsyncEventListener callback,
                       Application application) {
        new UpdateBreeder(application, callback).execute(breeder);
    }

    public void delete(final Breeder breeder, OnAsyncEventListener callback,
                       Application application) {
        new DeleteBreeder(application, callback).execute(breeder);
    }
}
