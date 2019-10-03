package com.example.tabactivity.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabactivity.AnimeTraining;
import com.example.tabactivity.MyAsyncTaskLoader;
import com.example.tabactivity.R;
import com.example.tabactivity.adapter.TrainingRecViewAdapter;

import java.util.ArrayList;

//import com.example.tabactivity.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<AnimeTraining>> {

    View v, w;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private TrainingRecViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movies, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        progressBar = v.findViewById(R.id.progress_bar);


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new TrainingRecViewAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        adapter.SetCategoryType("movie");


        adapter.notifyDataSetChanged();
        Bundle bundle = new Bundle();

        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(0, bundle, this);

        return v;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Loader<ArrayList<AnimeTraining>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoader(getContext(),1);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<AnimeTraining>> loader, ArrayList<AnimeTraining> data) {
        adapter.setTrainings(data);

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<AnimeTraining>> loader) {
        adapter.setTrainings(null);
    }



}
