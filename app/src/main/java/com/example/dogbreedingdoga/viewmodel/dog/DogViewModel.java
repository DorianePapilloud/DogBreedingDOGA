package com.example.dogbreedingdoga.viewmodel.dog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DogViewModel extends AndroidViewModel {
    private DogRepository dogRepository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Dog> observableDog;
    private final MediatorLiveData<Breeder> observableBreeder;

    public DogViewModel(@NonNull Application application,
                        DogRepository dogRep) {
        super(application);

        this.application = application;

        dogRepository = dogRep;


        //==== observable for dog =====
        observableDog = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableDog.setValue(null);

//        if(dogId != null){
//            LiveData<Dog> dog = dogRepository.getDog(dogId);
//            observableDog.addSource(dog, observableDog::setValue);
//        }

        //==== observable for dog's breeder ====
        observableBreeder = new MediatorLiveData<>();
        observableBreeder.setValue(null);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

//        private final String dogId;
        private final String breederId;

        private final DogRepository repository;

        public Factory(@NonNull Application application,  String breederId) {
            this.application = application;
//            this.dogId = dogId;
            this.breederId = breederId;
            repository = ((BaseApp) application).getDogRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DogViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData DogEntity query so the UI can observe it.
     */
    public LiveData<Breeder> getDogsBreeder() {
        return observableBreeder;
    }

    public LiveData<Dog> getDog() {return  observableDog;}


    public void createDog(Dog dog, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getDogRepository()
                .insert(dog, callback);
    }

    public void updateDog(Dog dog, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getDogRepository()
        .update(dog, callback);
    }

    public void deleteDog(Dog dog, OnAsyncEventListener callback) {
        ((BaseApp) getApplication()).getDogRepository()
                .delete(dog, callback);

    }
}
