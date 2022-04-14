package com.example.dogbreedingdoga.Database.pojo;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import java.util.List;


/**
 * https://developer.android.com/reference/android/arch/persistence/room/Relation
 */
public class DogsFromBreeder {
    public Breeder breeder;
    public List<Dog> dogs;
}