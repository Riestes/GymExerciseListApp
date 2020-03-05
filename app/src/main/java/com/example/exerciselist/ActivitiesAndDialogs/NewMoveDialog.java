package com.example.exerciselist.ActivitiesAndDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.exerciselist.R;

public class NewMoveDialog extends AppCompatDialogFragment {

    float count = 20f;
    private EditText editTextNewMove;
    private NumberPicker numberPickerSetCount;
    private NumberPicker numberPickerRepCount;
    private EditText editWeightCount;

    private Button button_increase_2_5;
    private Button button_increase_1;
    private Button button_decrease_2_5;
    private Button button_decrease_1;

    private NewMoveDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_move, null);

        builder.setView(view)
                .setTitle("Add new move")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextNewMove.getText().toString();
                        int setCount = numberPickerSetCount.getValue();
                        int repCount = numberPickerRepCount.getValue();
                        float weight = Float.valueOf(editWeightCount.getText().toString());
                        listener.applyMove(title, setCount, repCount, weight);
                    }
                });

        editTextNewMove = view.findViewById(R.id.edit_text_move_title_dialog);
        numberPickerSetCount = view.findViewById(R.id.number_picker_set_count_dialog);
        numberPickerSetCount.setMinValue(1);
        numberPickerSetCount.setMaxValue(10);

        numberPickerRepCount = view.findViewById(R.id.number_picker_rep_count_dialog);
        numberPickerRepCount.setMinValue(1);
        numberPickerRepCount.setMaxValue(30);

        button_increase_2_5 = view.findViewById(R.id.button_increase_2_5);
        button_decrease_2_5 = view.findViewById(R.id.button_decrease_2_5);
        button_increase_1 = view.findViewById(R.id.button_increase_1);
        button_decrease_1 = view.findViewById(R.id.button_decrease_1);

        editWeightCount = view.findViewById(R.id.edit_text_weight);

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

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewMoveDialog.NewMoveDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() +
                    "must implement NewMoveDialogListener");
        }
    }

    public interface NewMoveDialogListener {
        void applyMove(String title, int setCount, int repCount, float weight);
    }
}
