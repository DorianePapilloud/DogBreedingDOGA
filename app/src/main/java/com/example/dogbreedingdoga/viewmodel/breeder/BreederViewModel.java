package com.example.dogbreedingdoga.viewmodel.breeder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Repository.BreederRepository;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class BreederViewModel extends AndroidViewModel {
    private BreederRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Breeder> observableBreeder;

    public BreederViewModel(@NonNull Application application,
                            final String breederMail, BreederRepository breederRepository) {
        super(application);

        this.application = application;

        repository = breederRepository;

        observableBreeder = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBreeder.setValue(null);

        LiveData<Breeder> breeder = repository.getBreeder(breederMail, application);

        // observe the changes of the client entity from the database and forward them
        observableBreeder.addSource(breeder, observableBreeder::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String breederId;

        private final BreederRepository repository;

        public Factory(@NonNull Application application, String breederId) {
            this.application = application;
            this.breederId = breederId;
            repository = ((BaseApp) application).getBreederRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BreederViewModel(application, breederId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<Breeder> getBreeder() {
        return observableBreeder;
    }

    public void createClient(Breeder breeder, OnAsyncEventListener callback) {
        repository.insert(breeder, callback, application);
    }

    public void updateClient(Breeder breeder, OnAsyncEventListener callback) {
        repository.update(breeder, callback, application);
    }

    public void deleteBreeder(Breeder breeder, OnAsyncEventListener callback) {
        repository.delete(breeder, callback, application);

    }
}
