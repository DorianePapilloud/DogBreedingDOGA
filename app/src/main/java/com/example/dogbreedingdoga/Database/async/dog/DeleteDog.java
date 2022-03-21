package com.example.dogbreedingdoga.Database.async.dog;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class DeleteDog extends AsyncTask<Dog, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteDog(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Dog... params) {
        try {
            for (Dog dog : params)
                ((BaseApp) application).getDatabase().dogDao()
                        .deleteDog(dog);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
