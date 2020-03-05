package com.example.exerciselist.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "move_table",
        foreignKeys = @ForeignKey(entity = Exercise.class,
                parentColumns = "id",
                childColumns = "exerciseId",
                onDelete = CASCADE))
public class Move {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String move_name;
    private int set_count;
    private int rep_count;
    private float weight_count_kg;

    private int exerciseId;

    public Move(String move_name, int set_count, int rep_count, float weight_count_kg, int exerciseId) {
        this.move_name = move_name;
        this.set_count = set_count;
        this.rep_count = rep_count;
        this.weight_count_kg = weight_count_kg;
        this.exerciseId = exerciseId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getMove_name() {
        return move_name;
    }

    public int getSet_count() {
        return set_count;
    }

    public int getRep_count() {
        return rep_count;
    }

    public float getWeight_count_kg() {
        return weight_count_kg;
    }

    public int getExerciseId() {
        return exerciseId;
    }
}
