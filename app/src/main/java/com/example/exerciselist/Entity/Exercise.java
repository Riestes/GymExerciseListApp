package com.example.exerciselist.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String exercise_name;

    public Exercise(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getExercise_name() {
        return exercise_name;
    }
}
