package com.example.dogbreedingdoga.Database;


import android.os.AsyncTask;
import android.util.Log;

import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.Entity.Dog;



/**
 * Generates dummy data
 */
class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addBreeder(final AppDatabase db, final String email, final String firstName,
                                   final String lastName, final String password) {
        Breeder breeder = new Breeder(email, password);
        breeder.setNameBreeder(firstName);
        breeder.setSurnameBreeder(lastName);
        db.breederDao().insertBreeder(breeder);
    }

    private static void addDog(final AppDatabase db, final String nameDog, final String breedDog, final String dateOfBirth, final Gender gender,
                               final String breederMail, final Boolean pedigree, final boolean isAvailable) {
        Dog dog = new Dog(nameDog, breedDog, dateOfBirth, gender, breederMail, pedigree, isAvailable);
        db.dogDao().insertDog(dog);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.breederDao().deleteAll();

        addBreeder(db,
                "jean@test.com", "Jean", "Valjean", "123456"
        );
        addBreeder(db,
                "gavroche@test.com", "Gavroche", null, "123456"
        );
        addBreeder(db,
                "fantine@test.com", "Fantine", "", "123456"
        );
        addBreeder(db,
                "victor@hugo.eng", "Victor", "Hugo", "123456"
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addDog(db,
                "DoggyOne", "Kenoby", "today", Gender.Female, "jean@test.com",true, true
        );
        addDog(db,
                "DoggyTwo", "Javert","today", Gender.Male, "jean@test.com",false, true
        );
        addDog(db,
                "DoggyThree", "Husky","today", Gender.Female, "fantine@test.com",false, true
        );
        addDog(db,
                "DoggyFour", "Berger allemand","today", Gender.Male, "fantine@test.com",true, true
        );

    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
    }
