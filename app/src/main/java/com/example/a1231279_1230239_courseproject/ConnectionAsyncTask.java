package com.example.a1231279_1230239_courseproject;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }
@Override
protected void onPreExecute() {
        super.onPreExecute();
    ((IntroActivity) activity).setProgress(true);
}
@Override
protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
}

@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ((IntroActivity) activity).setProgress(false);
        if (result == null) {
            ((IntroActivity)activity).onConnectionFailed();
        } else{
            List<Trip> trips = TripJsonParser.getTripsFromJson(result);
            if( trips != null) {
                ((IntroActivity) activity).onConnectionSuccess(trips);
            }else{
                ((IntroActivity)activity).onConnectionFailed();
            }
        }
    }

}
