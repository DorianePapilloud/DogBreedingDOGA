package com.example.dogbreedingdoga.Database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DogLiveData extends LiveData<Dog> {


    private static final String TAG ="ClientLiveData";

    private final DatabaseReference reference;
    private final DogLiveData.MyValueEventListener listener = new DogLiveData.MyValueEventListener();
    private final String breeder;

    public DogLiveData(DatabaseReference ref) {
        this.reference = ref;
        breeder = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onActive() {
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG,"onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot){
            if (dataSnapshot.exists()) {
                Dog dog = dataSnapshot.getValue(Dog.class);
                dog.setIdDog(dataSnapshot.getKey());//(dataSnapshot.child("dogs").child(breeder).getKey());
                dog.setBreederId(breeder);
                setValue(dog);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }





}
