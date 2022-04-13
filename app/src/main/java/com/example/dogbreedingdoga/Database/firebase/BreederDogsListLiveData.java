package com.example.dogbreedingdoga.Database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.pojo.BreederWithDogs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreederDogsListLiveData extends LiveData<List<BreederWithDogs>> {

    private static final String TAG = "BreederDogsLiveData";

    private final DatabaseReference reference;
    private final String breeder;
    private final BreederDogsListLiveData.MyValueEventListener listener =
            new BreederDogsListLiveData.MyValueEventListener();

    public BreederDogsListLiveData(DatabaseReference ref, String breeder) {
        reference = ref;
        this.breeder = breeder;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toBreederWithDogsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can's listen to query " + reference, databaseError.toException());
        }
    }

    private List<BreederWithDogs> toBreederWithDogsList(DataSnapshot dataSnapshot) {
        List<BreederWithDogs> breederWithDogsList = new ArrayList<>();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//            if(!childSnapshot.getKey().equals(breeder)) {
                BreederWithDogs breederWithDogs = new BreederWithDogs();
                breederWithDogs.breeder = childSnapshot.getValue(Breeder.class);
                breederWithDogs.breeder.setIdBreeder(childSnapshot.getKey());
                breederWithDogs.dogs = toDogs(childSnapshot.child("dogs"),
                        childSnapshot.getKey());
                breederWithDogsList.add(breederWithDogs);
//            }
        }
        return breederWithDogsList;
    }


    private List<Dog> toDogs(DataSnapshot dataSnapshot, String breederId){
        List<Dog> dogs = new ArrayList<>();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
            Dog dog = childSnapshot.getValue(Dog.class);
            dog.setIdDog(childSnapshot.getKey());
            dog.setBreederMail(breederId);
            dogs.add(dog);
        }

        return dogs;
    }




}
