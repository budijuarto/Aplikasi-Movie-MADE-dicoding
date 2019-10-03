package com.example.tabactivity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.NoteViewHolder> {
    private final ArrayList<AnimeTraining> listNotes = new ArrayList<>();
    private final Activity activity;
    private String categoryType;


    public DatabaseAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<AnimeTraining> getListNotes() {
        return listNotes;
    }

    public void SetCategoryType(String categoryType){

        this.categoryType = categoryType;
    }


    public void setListNotes(ArrayList<AnimeTraining> listNotes) {

        if (listNotes.size() > 0) {
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void addItem(AnimeTraining note) {
        this.listNotes.add(note);
        notifyItemInserted(listNotes.size() - 1);
    }

    public void updateItem(int position, AnimeTraining note) {
        this.listNotes.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.listNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listNotes.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_list_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvTitle.setText(listNotes.get(position).getName());
        Glide.with(activity)
//                .asBitmap()
                .load("https://image.tmdb.org/t/p/w185" + listNotes.get(position).getPoster())
                .into(holder. poster);
        holder.tvDescription.setText(listNotes.get(position).getOverview());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, TrainingActivity.class);
                intent.putExtra(TrainingActivity.EXTRA_POSITION, position);
                intent.putExtra(TrainingActivity.EXTRA_NOTE, listNotes.get(position));
                intent.putExtra("category", categoryType);
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription;
        final ImageView poster;
        final CardView cvNote;

        NoteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.trainingName);
            tvDescription = itemView.findViewById(R.id.trainingShortDescription);
            poster= itemView.findViewById(R.id.trainingImage);
            cvNote = itemView.findViewById(R.id.cvmovie);
        }
    }
}