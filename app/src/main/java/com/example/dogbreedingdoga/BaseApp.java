package com.example.dogbreedingdoga;

import android.app.Application;
import android.content.Context;


import com.example.dogbreedingdoga.Database.AppDatabase;
import com.example.dogbreedingdoga.Database.Repository.BreederRepository;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.helper.LocaleHelper;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    public DogRepository getDogRepository() {
        return DogRepository.getInstance();
    }

    public BreederRepository getBreederRepository() {
        return BreederRepository.getInstance();
    }

    // To manage language
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}