package com.example.tabactivity.fragment_favourite;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.tabactivity.AnimeTraining;
import com.example.tabactivity.DatabaseAdapter;
import com.example.tabactivity.LoadNotesCallback;
import com.example.tabactivity.MovieHelper;
import com.example.tabactivity.MyAsyncTaskLoader;
import com.example.tabactivity.R;
import com.example.tabactivity.adapter.TrainingRecViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class MovFavFragment extends Fragment implements  LoadNotesCallback, LoaderManager.LoaderCallbacks<ArrayList<AnimeTraining>>
        {

    View v, w;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private DatabaseAdapter adapter;
    private MovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_mov_fav, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progress_bar);


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new DatabaseAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        adapter.SetCategoryType("movie");

        Bundle bundle = new Bundle();

        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(0, bundle, this);


        movieHelper = MovieHelper.getInstance(getActivity());

        movieHelper.open();

        if (savedInstanceState == null) {
            new LoadNotesAsync(movieHelper, this).execute();
        } else {
            ArrayList<AnimeTraining> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListNotes(list);
            }
        }

        return v;


    }

            @Override
            public void onResume() {
                new LoadNotesAsync(movieHelper, this).execute();
                super.onResume();
            }

            @Override
            public void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
                outState.putParcelableArrayList(EXTRA_STATE, adapter.getListNotes());
            }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<AnimeTraining>> {
        private final WeakReference<MovieHelper> weakNoteHelper;
        private final WeakReference<LoadNotesCallback> weakCallback;

        LoadNotesAsync(MovieHelper noteHelper, LoadNotesCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<AnimeTraining> doInBackground(Void... voids) {

            return weakNoteHelper.get().getAll("movie");
        }

        @Override
        protected void onPostExecute(ArrayList<AnimeTraining> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

            @Override
            public void preExecute() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void postExecute(ArrayList<AnimeTraining> notes) {
                progressBar.setVisibility(View.INVISIBLE);
                adapter.setListNotes(notes);
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                movieHelper.close();
            }
    @NonNull
    @Override
    public Loader<ArrayList<AnimeTraining>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoader(getContext(),1);
    }

            @Override
            public void onLoadFinished(@NonNull Loader<ArrayList<AnimeTraining>> loader, ArrayList<AnimeTraining> data) {

            }

            @Override
            public void onLoaderReset(@NonNull Loader<ArrayList<AnimeTraining>> loader) {

            }

            public class LoadNotesAsyncTv {
                public LoadNotesAsyncTv(MovieHelper movieHelper, TvFavFragment tvFavFragment) {
                }
            }


//    @Override
//    public void onLoadFinished(@NonNull Loader<ArrayList<AnimeTraining>> loader, ArrayList<AnimeTraining> data) {
//        adapter.setTrainings(data);
//
//        progressBar.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<ArrayList<AnimeTraining>> loader) {
//        adapter.setTrainings(null);
//    }



}
