package com.example.exerciselist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.exerciselist.Entity.Exercise;
import com.example.exerciselist.Repository.ExerciseRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {

    private ExerciseRepository exerciseRepository;
    private LiveData<List<Exercise>> allExercises;


    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        exerciseRepository = new ExerciseRepository(application);

        allExercises = exerciseRepository.getAllExercises();
    }

    public void insert(Exercise exercise) {
        exerciseRepository.insert(exercise);
    }

    public void update(Exercise exercise) {
        exerciseRepository.update(exercise);
    }

    public void delete(Exercise exercise) {
        exerciseRepository.delete(exercise);
    }

    public void deleteAllExercises() {
        exerciseRepository.deleteAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }
}
