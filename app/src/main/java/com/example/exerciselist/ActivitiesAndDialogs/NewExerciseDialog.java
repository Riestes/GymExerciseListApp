package com.example.exerciselist.ActivitiesAndDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.exerciselist.R;

public class NewExerciseDialog extends AppCompatDialogFragment {

    private EditText editTextNewExercise;
    private NewExerciseDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_exercise, null);

        builder.setView(view)
                .setTitle("Add new exercise")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextNewExercise.getText().toString();
                        listener.applyTitle(title);
                    }
                });

        editTextNewExercise = view.findViewById(R.id.edit_text_exercise_title_dialog);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewExerciseDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() +
                    "must implement NewExerciseDialogListener");
        }
    }

    public interface NewExerciseDialogListener {
        void applyTitle(String title);
    }
}
