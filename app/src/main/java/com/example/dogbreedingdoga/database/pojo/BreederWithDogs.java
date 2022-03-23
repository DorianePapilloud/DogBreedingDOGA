package com.example.dogbreedingdoga.Database.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import java.util.List;


/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class BreederWithDogs {
    @Embedded
    public Breeder breeder;

    @Relation(parentColumn = "email", entityColumn = "owner", entity = Dog.class)
    public List<Dog> dogs;
}