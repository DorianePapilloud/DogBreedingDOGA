package com.example.dogbreedingdoga;

import android.app.Application;

import com.example.dogbreedingdoga.Database.AppDatabase;
import com.example.dogbreedingdoga.Database.Repository.BreederRepository;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DogRepository getAccountRepository() {
        return DogRepository.getInstance();
    }

    public BreederRepository getBreederRepository() {
        return BreederRepository.getInstance();
    }
}