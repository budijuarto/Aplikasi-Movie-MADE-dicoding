package com.example.tabactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class TrainingActivity extends AppCompatActivity
        implements View.OnClickListener {


    private static final String TAG = "TrainingActivity";
    private Button btnFav;
    private AnimeTraining currentData;
    public static final String EXTRA_NOTE = "training";
    public static final String EXTRA_POSITION = "extra_position";
    private MovieHelper movieHelper;
    private TextView trainingName, trainingLongDesc;
    private ImageView trainingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_training);
            btnFav = findViewById(R.id.btnFav);
            btnFav.setOnClickListener(this);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

            initViews();

            Intent intent = getIntent();
            try {
                AnimeTraining incomingTraining = intent.getParcelableExtra("training");
                trainingName.setText((incomingTraining.getName()));
                trainingLongDesc.setText(incomingTraining.getOverview());
                Glide.with(this)
                        .asBitmap()
                        .load("https://image.tmdb.org/t/p/w185" + incomingTraining.getPoster())
                        .into(trainingImage);

                if (movieHelper.isFavorit(incomingTraining.getName())) {
                    setFavorite(true);
                } else {
                    setFavorite(false);
                }


            } catch (NullPointerException e) {
                e.getMessage();
            }

        }
        private void initViews () {
            Log.d(TAG, "initViews: started");
            trainingName = findViewById(R.id.trainingName);
            trainingLongDesc = findViewById(R.id.trainingLongDesc);
            trainingImage = findViewById(R.id.trainingImage);

        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnFav) {
            Intent intent = getIntent();
            AnimeTraining incomingTraining = intent.getParcelableExtra("training");

            String category = intent.getStringExtra("category");
            String title = incomingTraining.getName();
            String description = incomingTraining.getOverview();
            String poster = incomingTraining.getPoster();
            Log.d("data", title + description + poster);
            currentData = new AnimeTraining();
            currentData.setName(title);
            currentData.setOverview(description);
            currentData.setPoster(poster);
            Log.d("class", currentData.getName());


            if (movieHelper.isFavorit(title)) {

                long result = movieHelper.insertNote(currentData, category);

                if (result > 0) {
                    Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
                }
                setFavorite(false);
            } else {
                long result = movieHelper.deleteNote(title);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(), "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
                setFavorite(true);
            }

            }
        }


    private void  setFavorite(boolean foundOrNotFound){

        if (foundOrNotFound) {
            btnFav.setText("Add favorite");
        } else {
            btnFav.setText("Remove favorite");
        }

    }
    }

