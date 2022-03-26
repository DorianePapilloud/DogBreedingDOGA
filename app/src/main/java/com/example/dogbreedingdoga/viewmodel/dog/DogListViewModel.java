package com.example.dogbreedingdoga.viewmodel.dog;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Repository.BreederRepository;
import com.example.dogbreedingdoga.Database.Repository.DogRepository;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

import java.util.List;


public class DogListViewModel extends AndroidViewModel {

    private Application application;

    private DogRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Dog>> observableOwnDogs;

    public DogListViewModel(@NonNull Application application,
                            final String breederId,
                            DogRepository dogRepository) {
        super(application);

        this.application = application;

        repository = dogRepository;

        observableOwnDogs = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableOwnDogs.setValue(null);


        LiveData<List<Dog>> ownDogs = repository.getAllDogsByBreeder(breederId, application);

        // observe the changes of the entities from the database and forward them
        observableOwnDogs.addSource(ownDogs, observableOwnDogs::setValue);
    }

    /**
     * A creator is used to inject the dog's id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String breederId;

        private final BreederRepository breederRepository;

        private final DogRepository dogRepository;

        public Factory(@NonNull Application application, String breederId) {
            this.application = application;
            this.breederId = breederId;
            breederRepository = ((BaseApp) application).getBreederRepository();
            dogRepository = ((BaseApp) application).getDogRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DogListViewModel(application, breederId, dogRepository);
        }
    }


    /**
     * Expose the LiveData Dogs query so the UI can observe it.
     */
    public LiveData<List<Dog>> getOwnDogs() {
        return observableOwnDogs;
    }

    public void deleteDog(Dog dog, OnAsyncEventListener callback) {
        repository.delete(dog, callback, application);
    }

}
