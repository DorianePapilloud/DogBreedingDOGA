package com.example.dogbreedingdoga.Database;


import android.os.AsyncTask;
import android.util.Log;

import com.example.dogbreedingdoga.Database.AppDatabase;
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

//    private static void addClient(final AppDatabase db, final String email, final String firstName,
//                                  final String lastName, final String password) {
//        ClientEntity client = new ClientEntity(email, firstName, lastName, password);
//        db.clientDao().insert(client);
//    }

    private static void addDog(final AppDatabase db, final String nameDog, final String breedDog, final String dateOfBirth, final Gender gender,
                               final String breederMail, final Boolean pedigree, final boolean isAvailable) {
        Dog dog = new Dog(nameDog, breedDog, dateOfBirth, gender, breederMail, pedigree, isAvailable);
        db.dogDao().insertDog(dog);
    }

    private static void populateWithTestData(AppDatabase db) {
//        db.clientDao().deleteAll();
//
//        addClient(db,
//                "m.p@fifa.com", "Michel", "Platini", "michel1"
//        );
//        addClient(db,
//                "s.b@fifa.com", "Sepp", "Blatter", "sepp1"
//        );
//        addClient(db,
//                "e.s@fifa.com", "Ebbe", "Schwartz", "ebbe1"
//        );
//        addClient(db,
//                "a.c@fifa.com", "Aleksander", "Ceferin", "aleksander1"
//        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addDog(db,
                "DoggyOne", "Sam", "today", Gender.Female, "test@test.fr",true, true
        );
        addDog(db,
                "DoggyTwo", "Sam","today", Gender.Female, "test@test.fr",true, true
        );
        addDog(db,
                "DoggyThree", "Sam","today", Gender.Female, "test@test.fr",true, true
        );
        addDog(db,
                "DoggyFour", "Sam","today", Gender.Female, "test@test.fr",true, true
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
