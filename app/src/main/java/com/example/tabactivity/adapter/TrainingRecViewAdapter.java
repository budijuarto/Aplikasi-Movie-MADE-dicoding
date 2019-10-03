package com.example.tabactivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabactivity.AnimeTraining;
import com.example.tabactivity.CustomOnItemClickListener;
import com.example.tabactivity.R;
import com.example.tabactivity.TrainingActivity;

import java.util.ArrayList;

public class TrainingRecViewAdapter extends  RecyclerView.Adapter<TrainingRecViewAdapter.ViewHolder>{
    private static final String TAG = "TrainingRecadapater";

    private Context mContext;
    private String categoryType;
    private ArrayList<AnimeTraining> trainings = new ArrayList<>();


    public TrainingRecViewAdapter(Context mContext){

        this.mContext = mContext;
    }

    public void SetCategoryType(String categoryType){

        this.categoryType = categoryType;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.training_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "OnBindViewHolder : called");
        viewHolder.txtName.setText(trainings.get(i).getName());
        viewHolder.txtShortDesc.setText(trainings.get(i).getOverview());

        Glide.with(mContext)
//                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185" + trainings.get(i).getPoster())
                .into(viewHolder.image);

//        viewHolder.parent.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
//            @Override
//            public void onItemClicked(View view, int position) {
//                Intent intent = new Intent(mContext, TrainingActivity.class);
//                intent.putExtra(TrainingActivity.EXTRA_POSITION, position);
//                intent.putExtra(TrainingActivity.EXTRA_NOTE, trainings.get(position));
//                mContext.startActivity(intent);
//            }
//        }));

        viewHolder.parent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: navigate to the other activity
                Intent intent = new Intent(mContext, TrainingActivity.class);
                intent.putExtra("training", trainings.get(i));
                intent.putExtra("category", categoryType);
                mContext.startActivity(intent);

            }
        });

    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName, txtShortDesc;
        private ImageView image;
        private CardView parent;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            txtName = itemView.findViewById(R.id.trainingName);
            txtShortDesc =itemView.findViewById(R.id.trainingShortDescription);
            image  = itemView.findViewById(R.id.trainingImage);
            parent = itemView.findViewById(R.id.cvmovie);
        }
    }

    public void setTrainings(ArrayList<AnimeTraining> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trainings.size() ;
    }

}
