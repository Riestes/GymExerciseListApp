package com.example.exerciselist.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.exerciselist.Entity.Move;

import java.util.List;

@Dao
public interface MoveDao {

    @Insert
    void insert(Move move);

    @Update
    void update(Move move);

    @Delete
    void delete(Move move);

    @Query("SELECT * FROM move_table WHERE exerciseId=:exerciseId")
    LiveData<List<Move>> findMovesForExercise(int exerciseId);
}
