package com.example.tabactivity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.loader.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<AnimeTraining>> {
    private ArrayList<AnimeTraining> mData;
    private boolean mHasResult = false;
    private int type;
    private ProgressBar progressBar;



    public MyAsyncTaskLoader(Context context, int type) {
        super(context);
//       super(type);
        this.type = type;
//        this.progressBar=progressBar;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<AnimeTraining> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "MASUKAN API KEY ANDA";

    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY

    @Override
    public ArrayList<AnimeTraining> loadInBackground() {

//        progressBar.setVisibility(View.VISIBLE);
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<AnimeTraining> weatherItemses = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=23c2462ff1b73186fbb5c8719a910a6b";
        if (type != 1)  url = "http://api.themoviedb.org/3/discover/tv?api_key=23c2462ff1b73186fbb5c8719a910a6b";
        Log.d("URL", url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d("API RESULT", result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        AnimeTraining weatherItems = new AnimeTraining(weather, type);
                        weatherItemses.add(weatherItems);
                    }
                    Log.d("RESULT", "finish");

                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    e.printStackTrace();
                    Log.d("API RESULT", "Catch");

//                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("API RESULT", "FAIL");

//                progressBar.setVisibility(View.GONE);
            }


        });
        return weatherItemses;
    }

}
