package com.example.exerciselist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.exerciselist.Entity.Move;
import com.example.exerciselist.Repository.MoveRepository;

import java.util.List;

public class MoveViewModel extends AndroidViewModel {

    private MoveRepository moveRepository;


    public MoveViewModel(@NonNull Application application) {
        super(application);
        moveRepository = new MoveRepository(application);
    }

    public void insert(Move move) {
        moveRepository.insert(move);
    }

    public void update(Move move) {
        moveRepository.update(move);
    }

    public void delete(Move move) {
        moveRepository.delete(move);
    }

    public LiveData<List<Move>> getMovesForExercise(int exerciseId) {
        return moveRepository.getMovesForExercise(exerciseId);
    }
}
