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

public class DogViewModel extends AndroidViewModel {
    private DogRepository dogRepository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Dog> observableDog;
    private final MediatorLiveData<Breeder> observableBreeder;

    public DogViewModel(@NonNull Application application,
                        final int dogId, DogRepository dogRep) {
        super(application);

        this.application = application;

        dogRepository = dogRep;


        //==== observable for dog =====
        observableDog = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableDog.setValue(null);

        LiveData<Dog> dog = this.dogRepository.getDog(dogId, application);

        //==== observable for dog's breeder ====
        observableBreeder = new MediatorLiveData<>();
        observableBreeder.setValue(null);

        //determine the breeder of this dog
        LiveData<Breeder> breeder = this.dogRepository.getDogsBreeder(dogId, application);

        // observe the changes of the dog and its breeder entities from the database and forward them
        observableDog.addSource(dog, observableDog::setValue);
        observableBreeder.addSource(breeder, observableBreeder::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int dogId;
        private final String breederId;

        private final DogRepository repository;

        public Factory(@NonNull Application application, int dogId, String breederId) {
            this.application = application;
            this.dogId = dogId;
            this.breederId = breederId;
            repository = ((BaseApp) application).getDogRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DogViewModel(application, dogId, repository);
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
        dogRepository.insert(dog, callback, application);
    }

    public void updateDog(Dog dog, OnAsyncEventListener callback) {
        dogRepository.update(dog, callback, application);
    }

    public void deleteDog(Dog dog, OnAsyncEventListener callback) {
        dogRepository.delete(dog, callback, application);

    }
}
