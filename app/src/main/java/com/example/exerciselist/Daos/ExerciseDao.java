package com.example.exerciselist.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.exerciselist.Entity.Exercise;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();
}
