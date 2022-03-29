package com.example.dogbreedingdoga.Database.async.breeder;

import android.app.Application;
import android.os.AsyncTask;

import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class UpdateBreeder extends AsyncTask<Breeder, Void, Void>{


    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateBreeder(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Breeder... params) {
        try {
            for (Breeder breeder : params)
                ((BaseApp) application).getDatabase().breederDao()
                        .updateBreeder(breeder);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                this.callback.onSuccess();
            } else {
                this.callback.onFailure(exception);
            }
        }
    }

}
