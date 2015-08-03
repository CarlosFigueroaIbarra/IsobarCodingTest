package com.roundarch.codetest.part3;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Part3Service extends Service {
    private static String URL = "http://gomashup.com/json.php?fds=geo/usa/zipcode/state/IL";

    private final String TAG = this.getClass().getSimpleName();

    // TODO - we can use this as the broadcast intent to filter for in our Part3Fragment
    public static final String ACTION_SERVICE_DATA_UPDATED = "com.roundarch.codetest.ACTION_SERVICE_DATA_UPDATED";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO - this interface needs to be implemented to allow consumers
        // TODO - access to the data we plan to download
        return new Part3ServiceBinder();
    }

    public void updateData() {
        // TODO - start the update process for our data

        new AsyncTask<String, Void, ArrayList<MyLocation>>() {
            public void onPostExecute(ArrayList<MyLocation> result) {
                broadcastDataUpdated(result);
            }

            @Override
            protected ArrayList<MyLocation> doInBackground(String... params) {
                String response;
                Model model;
                ArrayList<MyLocation> locations = null;

                try {
                    response = sendGetRequest(URL);
                    Log.i(TAG, "RESPONSE :: " + response);
                    response = response.replace("(", "").replace(")", "");
                    model = parseJSON(response, Model.class);

                    if (model != null) {
                        locations = model.getMyLocations();
                    }
                } catch(Exception e) {
                }

                return locations;
            }
        }.execute();
    }

    private void broadcastDataUpdated(ArrayList<MyLocation> result) {
        // TODO - send the broadcast
        Intent intent = new Intent();
        intent.setAction(ACTION_SERVICE_DATA_UPDATED);
        intent.putParcelableArrayListExtra("res", result);
        sendBroadcast(intent);
    }

    public final class Part3ServiceBinder extends Binder {
        // TODO - we need to expose our public IBinder API to clients
        public Part3Service getService() {
            return Part3Service.this;
        }
    }

    // TODO - eventually we plan to request JSON from the network, so we need
    // TODO - to implement a way to perform that off the main thread.  Then, once we
    // TODO - have the data we can parse it as JSON (using standard Android APIs is fine)
    // TODO - before finally returning to the main thread to store our data on the service.
    // TODO - Keep in mind that the service will keep a local copy and will need an interface
    // TODO - to allow clients to access it.

    // TODO - if you need a simple JSON endpoint, you can obtain the ZIP codes for the state
    // TODO - of Illinois by using this URL:
    //
    // TODO - http://gomashup.com/json.php?fds=geo/usa/zipcode/state/IL

    public String sendGetRequest(String url) {

        String out = "";
        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response;

        try {
            request = new Request.Builder()
                    .url(url)
            .header("Accept", "application/json")
                    .get()
                    .build();
            response = client.newCall(request).execute();
            out = response.body().string();
        } catch (IOException e) {
            Log.i(getClass().getName(), "error :: " + e);
        } catch (Exception e) {
            Log.i(getClass().getName(), "error :: " + e);
        }

        return out;
    }

    /**
     * Parses JSON String and transforms it to the desired type
     * @param json : Json String
     * @param type : Type of Object to generate
     */
    public <T> T parseJSON(String json, Class<T> type) {
        T r = null;
        try {
            r = new Gson().fromJson(json, type);
        } catch (Exception e) {
            Log.i(getClass().getName(), "error while parsing :: " + e);
        }
        return r;
    }

}
