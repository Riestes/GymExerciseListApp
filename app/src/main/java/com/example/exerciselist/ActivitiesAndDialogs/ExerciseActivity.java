package com.example.exerciselist.ActivitiesAndDialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciselist.Adapter.MoveAdapter;
import com.example.exerciselist.Entity.Move;
import com.example.exerciselist.R;
import com.example.exerciselist.ViewModel.MoveViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity implements NewMoveDialog.NewMoveDialogListener {

    // For debugging
    private static final String TAG = "ExerciseActivity";

    public static final String EXTRA_TITLE = "com.example.exerciselist.EXTRA_TITLE";
    public static final String EXTRA_ID = "com.example.exerciselist.EXTRA_ID";
    public static final int EDIT_MOVE_REQUEST = 1;

    private MoveViewModel moveViewModel;

    private TextView editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        FloatingActionButton buttonAddExercise = findViewById(R.id.fab_add_move);
        buttonAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMoveDialog();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_moves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MoveAdapter moveAdapter = new MoveAdapter();
        recyclerView.setAdapter(moveAdapter);


        /**
         * This will get intent from the MainActivity
         * getExtras EXTRA_ID is put to the getMovesForExercise to show moves
         * that belongs to the selected exercise
         */
        final Intent intent = getIntent();
        int exerciseId = intent.getExtras().getInt(EXTRA_ID);
        Log.d(TAG, "DEBUG: getInt EXTRA_ID is: " + exerciseId);

        moveViewModel = ViewModelProviders.of(this).get(MoveViewModel.class);
        moveViewModel.getMovesForExercise(exerciseId).observe(this, new Observer<List<Move>>() {
            @Override
            public void onChanged(List<Move> moves) {
                moveAdapter.setMoves(moves);
            }
        });

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
        setTitle("Exercise");


        moveAdapter.setOnItemClickListener(new MoveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Move move) {

                Intent intent = new Intent(ExerciseActivity.this, EditMoveActivity.class);

                int moveId = move.getId();
                intent.putExtra(EditMoveActivity.EXTRA_MOVE_ID, moveId);
                intent.putExtra(EditMoveActivity.EXTRA_MOVE_TITLE, move.getMove_name());
                intent.putExtra(EditMoveActivity.EXTRA_SET_COUNT, move.getSet_count());
                intent.putExtra(EditMoveActivity.EXTRA_REP_COUNT, move.getRep_count());
                intent.putExtra(EditMoveActivity.EXTRA_WEIGHT_COUNT, move.getWeight_count_kg());

                startActivityForResult(intent, EDIT_MOVE_REQUEST);
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
                final Move item = moveAdapter.remove(position);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                moveViewModel.delete(item);
                                Toast.makeText(getApplicationContext(), item.getMove_name() + " deleted!", Toast.LENGTH_SHORT).show();
                                break;


                            case DialogInterface.BUTTON_NEGATIVE:
                                moveAdapter.add(item, position);
                                Toast.makeText(getApplicationContext(), item.getMove_name() + " not deleted!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                builder.setMessage("Delete " + item.getMove_name() + "?").setPositiveButton("Delete", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_MOVE_REQUEST && resultCode == RESULT_OK) {

            final Intent intent = getIntent();
            int exerciseId = intent.getExtras().getInt(EXTRA_ID);

            Log.d(TAG, "DEBUG: EXERCISEID: " + exerciseId);

            int id = data.getIntExtra(EditMoveActivity.EXTRA_MOVE_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Exercise can't be updated!", Toast.LENGTH_SHORT).show();
                return;
            }

            String moveTitle = data.getStringExtra(EditMoveActivity.EXTRA_MOVE_TITLE);
            int setCount = data.getExtras().getInt(EditMoveActivity.EXTRA_SET_COUNT);
            int repCount = data.getExtras().getInt(EditMoveActivity.EXTRA_REP_COUNT);
            float weightCount = data.getExtras().getFloat(EditMoveActivity.EXTRA_WEIGHT_COUNT);

            Move move = new Move(moveTitle, setCount, repCount, weightCount, exerciseId);
            move.setId(id);
            moveViewModel.update(move);

            Toast.makeText(this, "Move updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Move not updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void openNewMoveDialog() {
        NewMoveDialog newMoveDialog = new NewMoveDialog();
        newMoveDialog.show(getSupportFragmentManager(), "new move dialog");
    }

    @Override
    public void applyMove(String title, int setCount, int repCount, float weight) {

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Move not added due to empty input", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        int id = intent.getExtras().getInt(EXTRA_ID);

        Move move = new Move(title, setCount, repCount, weight, id);
        moveViewModel.insert(move);
    }
}
