package com.example.dogbreedingdoga.Database.async.breeder;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import com.example.dogbreedingdoga.BaseApp;
import com.example.dogbreedingdoga.Database.Entity.Breeder;
import com.example.dogbreedingdoga.Database.util.OnAsyncEventListener;

public class CreateBreeder extends AsyncTask<Breeder, Void, Void> {

    private Context context;
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateBreeder(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Breeder... params) {
        try {
            for (Breeder breeder : params)
                ((BaseApp) application) .getDatabase().breederDao()
                        .insertBreeder(breeder);
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
