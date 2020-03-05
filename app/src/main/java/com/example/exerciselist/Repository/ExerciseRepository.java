package com.example.exerciselist.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.exerciselist.Daos.ExerciseDao;
import com.example.exerciselist.Database.ExerciseDatabase;
import com.example.exerciselist.Entity.Exercise;

import java.util.List;

public class ExerciseRepository {

    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        exerciseDao = database.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
    }


    /**
     * Insert exercise
     *
     * @param exercise
     */
    public void insert(Exercise exercise) {
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        private InsertExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }

    /**
     * Update exercise
     *
     * @param exercise
     */
    public void update(Exercise exercise) {
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        private UpdateExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.update(exercises[0]);
            return null;
        }
    }

    /**
     * Delete exercise
     *
     * @param exercise
     */
    public void delete(Exercise exercise) {
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        private DeleteExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }

    /**
     * Delete all exercises
     */
    public void deleteAllExercises() {
        new DeleteAllExercisesAsyncTask(exerciseDao).execute();
    }

    private static class DeleteAllExercisesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;

        private DeleteAllExercisesAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.deleteAllExercises();
            return null;
        }
    }

    /**
     * Read all exercises
     *
     * @return
     */
    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }


}
