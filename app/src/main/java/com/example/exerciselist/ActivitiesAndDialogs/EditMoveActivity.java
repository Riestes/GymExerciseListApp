package com.example.exerciselist.ActivitiesAndDialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exerciselist.R;

public class EditMoveActivity extends AppCompatActivity {

    public static final String EXTRA_MOVE_ID = "com.example.exerciselist.EXTRA_MOVE_ID";
    public static final String EXTRA_MOVE_TITLE = "com.example.exerciselist.EXTRA_MOVE_TITLE";
    public static final String EXTRA_SET_COUNT = "com.example.exerciselist.EXTRA_SET_COUNT";
    public static final String EXTRA_REP_COUNT = "com.example.exerciselist.EXTRA_REP_COUNT";
    public static final String EXTRA_WEIGHT_COUNT = "com.example.exerciselist.EXTRA_WEIGHT_COUNT";
    float count;
    private EditText editMoveTitle;
    private NumberPicker editSetCount;
    private NumberPicker editRepCount;
    private EditText editWeightCount;

    private Button button_increase_2_5;
    private Button button_increase_1;
    private Button button_decrease_2_5;
    private Button button_decrease_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_move);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        button_increase_2_5 = findViewById(R.id.button_increase_2_5);
        button_decrease_2_5 = findViewById(R.id.button_decrease_2_5);
        button_increase_1 = findViewById(R.id.button_increase_1);
        button_decrease_1 = findViewById(R.id.button_decrease_1);

        editMoveTitle = findViewById(R.id.edit_text_edit_move);
        editSetCount = findViewById(R.id.number_picker_set_count_activity);
        editRepCount = findViewById(R.id.number_picker_rep_count_activity);
        editWeightCount = findViewById(R.id.edit_text_weight);

        editSetCount.setMinValue(1);
        editRepCount.setMinValue(1);
        //editWeightCount.setMinValue(1);

        editSetCount.setMaxValue(10);
        editRepCount.setMaxValue(30);
        //editWeightCount.setMaxValue(300);


        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVE_ID)) {
            setTitle("Edit move");
            editMoveTitle.setText(intent.getStringExtra(EXTRA_MOVE_TITLE));
            editSetCount.setValue(intent.getIntExtra(EXTRA_SET_COUNT, 1));
            editRepCount.setValue(intent.getIntExtra(EXTRA_REP_COUNT, 1));

            editWeightCount.setText(String.valueOf(intent.getFloatExtra(EXTRA_WEIGHT_COUNT, 1)));

        }

        count = Float.valueOf(editWeightCount.getText().toString());

        button_increase_2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count += 2.5f;
                editWeightCount.setText(String.valueOf(count));
            }
        });

        button_increase_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count += 1f;
                editWeightCount.setText(String.valueOf(count));
            }
        });

        button_decrease_2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count -= 2.5f;
                editWeightCount.setText(String.valueOf(count));
            }
        });

        button_decrease_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count -= 1f;
                editWeightCount.setText(String.valueOf(count));
            }
        });
    }

    private void saveMove() {

        String moveTitle = editMoveTitle.getText().toString();
        int setCount = editSetCount.getValue();
        int repCount = editRepCount.getValue();
        float weightCount = Float.valueOf(editWeightCount.getText().toString());

        if (moveTitle.trim().isEmpty()) {
            Toast.makeText(this, "Please enter values for fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_MOVE_TITLE, moveTitle);
        data.putExtra(EXTRA_SET_COUNT, setCount);
        data.putExtra(EXTRA_REP_COUNT, repCount);
        data.putExtra(EXTRA_WEIGHT_COUNT, weightCount);

        int id = getIntent().getIntExtra(EXTRA_MOVE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_MOVE_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu_button:
                saveMove();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
