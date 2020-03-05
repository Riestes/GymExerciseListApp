package com.example.exerciselist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciselist.Entity.Exercise;
import com.example.exerciselist.R;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<Exercise> exercises = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    @NonNull
    @Override
    public ExerciseAdapter.ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExerciseAdapter.ExerciseHolder holder, int position) {
        final Exercise currentExercise = exercises.get(position);
        holder.textViewExerciseTitle.setText(currentExercise.getExercise_name());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public Exercise remove(int position) {
        if (position < getItemCount() && position >= 0) {
            Exercise exercise = exercises.remove(position);
            notifyItemRemoved(position);
            return exercise;
        }
        return null;
    }

    public void add(Exercise exercise, int position) {
        exercises.add(position, exercise);
        notifyItemInserted(position);
    }

    /**
     * Pass exercises to the RecyclerView
     *
     * @param exercises
     */
    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        private TextView textViewExerciseTitle;

        public ExerciseHolder(View itemView) {
            super(itemView);

            textViewExerciseTitle = itemView.findViewById(R.id.text_view_exercise_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(exercises.get(position));
                    }
                }
            });
        }
    }
}
