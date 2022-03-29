package com.example.dogbreedingdoga.Database.async.dog;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Dog;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class UpdateDog extends AsyncTask<Dog, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateDog(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
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
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}
