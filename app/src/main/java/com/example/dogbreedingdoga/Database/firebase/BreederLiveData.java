package com.example.dogbreedingdoga.Database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.adapter.ListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BreederLiveData extends LiveData<Breeder> {

    private static final String TAG ="ClientLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public BreederLiveData(DatabaseReference ref) { this.reference = ref; }

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
                Breeder breeder = dataSnapshot.getValue(Breeder.class);
                breeder.setIdBreeder(dataSnapshot.getKey());
                setValue(breeder);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Breeder> toBreederList(DataSnapshot dataSnapshot){
        List<Breeder> breeders = new ArrayList<>();
        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
            Breeder breeder = childSnapshot.getValue(Breeder.class);
            breeder.setIdBreeder(childSnapshot.getKey());
            breeders.add(breeder);
        }
            return breeders;
    }

}
