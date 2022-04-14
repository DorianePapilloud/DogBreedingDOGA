package com.example.dogbreedingdoga.Database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.pojo.DogsFromBreeder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreederDogsListLiveData extends LiveData<List<Dog>> {

    private static final String TAG = "BreederDogsLiveData";

    private final DatabaseReference reference;
//    private final String breeder;
    private final BreederDogsListLiveData.MyValueEventListener listener =
            new BreederDogsListLiveData.MyValueEventListener();

    public BreederDogsListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toDogsFromBreederList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can's listen to query " + reference, databaseError.toException());
        }
    }

    private List<Dog> toDogsFromBreederList(DataSnapshot dataSnapshot) {
        List<Dog> dogsFromBreederList = new ArrayList<>();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                Dog dogsFromBreeder = childSnapshot.getValue(Dog.class);
            dogsFromBreeder.setBreederId(childSnapshot.getKey());
            dogsFromBreederList.add(dogsFromBreeder);
//                dogsFromBreeder.breeder = childSnapshot.getValue(Breeder.class);
//                dogsFromBreeder.breeder.setIdBreeder(childSnapshot.getKey());
//                dogsFromBreeder.dogs = toDogs(childSnapshot.child("dogs"),
//                        childSnapshot.getKey());
//                dogsFromBreederList.add(dogsFromBreeder);
        }
        return dogsFromBreederList;
    }

    private List<CourseEntity> toCourseList(DataSnapshot snapshot) {
        List<CourseEntity> courses = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CourseEntity entity = childSnapshot.getValue(CourseEntity.class);
            entity.setCourseID(childSnapshot.getKey());
            courses.add(entity);
        }
        return courses;
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
