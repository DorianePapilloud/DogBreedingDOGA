package com.example.dogbreedingdoga.Database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DogLiveData extends LiveData<Dog> {


    private static final String TAG ="ClientLiveData";

    private final DatabaseReference reference;
    private final DogLiveData.MyValueEventListener listener = new DogLiveData.MyValueEventListener();

    public DogLiveData(DatabaseReference ref) { this.reference = ref; }

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
                dog.setIdDog(dataSnapshot.getKey());
                setValue(dog);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Dog> toDogList(DataSnapshot dataSnapshot){
        List<Dog> dogs = new ArrayList<>();
        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
            Dog dog = childSnapshot.getValue(Dog.class);
            dog.setIdDog(childSnapshot.getKey());
            dogs.add(dog);
        }
        return dogs;
    }




}
