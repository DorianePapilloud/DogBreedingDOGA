package com.example.dogbreedingdoga.Database.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.firebase.BreederLiveData;
import com.example.dogbreedingdoga.Database.pojo.BreederWithDogs;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class BreederRepository {

    private static final String TAG = "CreateAccountFragment";

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

    public void signIn(final String email, final String pwd,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(listener);
    }


    public LiveData<Breeder> getBreeder(final String breederId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Breeders")
                .child(breederId);
        return new BreederLiveData(reference);
    }


    public void register(final Breeder breeder, final OnAsyncEventListener callback){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                breeder.getEmail(),
                breeder.getPassword()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                breeder.setIdBreeder(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(breeder, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

//    public void insert(final Breeder breeder, final OnAsyncEventListener callback) {
//       String id = FirebaseDatabase.getInstance().getReference("breeders").push().getKey();
//       FirebaseDatabase.getInstance()
//               .getReference("breeders")
//               .child(id)
//               .setValue(breeder, (databaseError, databaseReference) -> {
//                   if(databaseError != null) {
//                       callback.onFailure(databaseError.toException());
//                   } else {
//                       callback.onSuccess();
//                   }
//               });
//    }

    public void insert(final Breeder breeder, final OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("breeders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(breeder, (databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        callback.onFailure(null);
                                        Log.d(TAG, "Rollback successful: Breeder account deleted");
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

    public void update(final Breeder breeder, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("breeders")
                .child(breeder.getIdBreeder())
                .updateChildren(breeder.toMap(), (databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Breeder breeder, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("breeders")
                .child(breeder.getIdBreeder())
                .removeValue((databaseError, databaseReference) -> {
                    if(databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
