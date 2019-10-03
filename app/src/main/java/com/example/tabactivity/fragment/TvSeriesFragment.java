package com.example.tabactivity.fragment;


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
import com.example.tabactivity.MyAsyncTaskLoader;
import com.example.tabactivity.R;
import com.example.tabactivity.adapter.TrainingRecViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvSeriesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<AnimeTraining>>{

    View v, w;

    private RecyclerView recyclerView;

    private TrainingRecViewAdapter adapter;
    private ProgressBar progressBar;

    private List<AnimeTraining> lstAnime;

//    public MoviesFragment() {
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Loader<ArrayList<AnimeTraining>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoader(getContext(),2);
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


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tv, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);

        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new TrainingRecViewAdapter(this.getContext());
        recyclerView.setAdapter(adapter);

        adapter.SetCategoryType("tv");
        adapter.notifyDataSetChanged();

        Bundle bundle = new Bundle();
        getLoaderManager().initLoader(0, bundle, this);

        return v;





    }


}
