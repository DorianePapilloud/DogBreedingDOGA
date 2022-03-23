package com.example.dogbreedingdoga.Database.async.dog;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class UpdateDog extends AsyncTask<Dog, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateDog(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Dog... params) {
        try {
            for (Dog dog : params)
                ((BaseApp) application).getDatabase().dogDao()
                        .updateDog(dog);
        } catch (Exception e) {
            this.exception = e;
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
