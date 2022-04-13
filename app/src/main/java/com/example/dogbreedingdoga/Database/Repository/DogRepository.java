package com.example.dogbreedingdoga.Database.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.Gender;
import com.example.dogbreedingdoga.Database.firebase.BreederLiveData;
import com.example.dogbreedingdoga.Database.firebase.DogLiveData;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DogRepository {

    private static final String TAG = "CreateAccountFragment";

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

    public LiveData<Dog> getDog(final String idDog) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Dogs")
                .child(idDog);
        return new DogLiveData(reference);
    }

//    public LiveData<List<Dog>> getDogs(Application application) {
//        return ((BaseApp) application).getDatabase().dogDao().getAllDogs();
//    }
//
//    public LiveData<List<Dog>> getAllDogsByBreeder(final String breederMail, Application application) {
//        return ((BaseApp) application).getDatabase().dogDao().getDogsForOneBreeder(breederMail);
//    }


    /// a controler /////////////////////////////////////77
    public LiveData<Breeder> getDogsBreeder(final String idBreeder) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Dogs")
                .child(idBreeder);
        return new BreederLiveData(reference);
    }

    public LiveData<List<Dog>> getDogsByBreederByAvailability(final String breederMail, boolean availability) {
        return ;
    }

    public LiveData<List<Dog>> getDogsByBreederByGenderByAvailability(final String breederMail, Gender gender, boolean availability, Application application) {
        return ((BaseApp) application).getDatabase().dogDao().getDogsByBreederByGenderByAvailability(breederMail, gender, availability);
    }

    //////////////////////////////////////////////// this need to be changed //////////////////////////////////////////////
    public void insert(final Dog dog, final OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("dogs")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(dog, (databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: Dog deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure", task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void update(final Dog dog, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("dogs")
                .child(dog.getIdDog())
                .updateChildren(dog.toMap(), (databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Dog dog, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("dogs")
                .child(dog.getIdDog())
                .removeValue((databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
