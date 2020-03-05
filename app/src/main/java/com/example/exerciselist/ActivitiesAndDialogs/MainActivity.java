package com.example.exerciselist.ActivitiesAndDialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciselist.Adapter.ExerciseAdapter;
import com.example.exerciselist.Entity.Exercise;
import com.example.exerciselist.R;
import com.example.exerciselist.ViewModel.ExerciseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewExerciseDialog.NewExerciseDialogListener {

    // For debugging
    private static final String TAG = "MainActivity";

    public static final int OPEN_EXERCISE_REQUEST = 1;
    private ExerciseViewModel exerciseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton buttonAddExercise = findViewById(R.id.fab_add_exercise);
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewExerciseDialog();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        registerForContextMenu(recyclerView);

        final ExerciseAdapter adapter = new ExerciseAdapter();
        recyclerView.setAdapter(adapter);

        exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        exerciseViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                final int position = viewHolder.getAdapterPosition();
                final Exercise item = adapter.remove(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                exerciseViewModel.delete(item);
                                Toast.makeText(getApplicationContext(), item.getExercise_name() + " deleted!", Toast.LENGTH_SHORT).show();
                                break;


                            case DialogInterface.BUTTON_NEGATIVE:
                                adapter.add(item, position);
                                Toast.makeText(getApplicationContext(), item.getExercise_name() + " not deleted!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                builder.setMessage("Delete " + item.getExercise_name() + "?").setPositiveButton("Delete", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();
            }
        }).attachToRecyclerView(recyclerView);

        /**
         * When exercise is clicked
         */
        adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exercise exercise) {

                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);

                /**
                 * This will send clicked exercise id and title to the new activity
                 */
                int exerciseId = exercise.getId();
                intent.putExtra(ExerciseActivity.EXTRA_ID, exerciseId);
                intent.putExtra(ExerciseActivity.EXTRA_TITLE,
                        exercise.getExercise_name());
                Log.d(TAG, "DEBUG: Exercise name is: " + exercise.getExercise_name() + " and exercise ID is: " + exerciseId);
                startActivityForResult(intent, OPEN_EXERCISE_REQUEST);
            }
        });
    }

    /**
     * Menu for deleting all
     *
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_exercises:
                exerciseViewModel.deleteAllExercises();
                Toast.makeText(this, "All exercises deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    **/

    public void openNewExerciseDialog() {
        NewExerciseDialog newExerciseDialog = new NewExerciseDialog();
        newExerciseDialog.show(getSupportFragmentManager(), "new exercise dialog");
    }

    @Override
    public void applyTitle(String title) {

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Exercise not added due to empty input", Toast.LENGTH_SHORT).show();
            return;
        }

        Exercise exercise = new Exercise(title);
        exerciseViewModel.insert(exercise);
    }
}
