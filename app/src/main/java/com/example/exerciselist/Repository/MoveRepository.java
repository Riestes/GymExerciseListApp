package com.example.exerciselist.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.exerciselist.Daos.MoveDao;
import com.example.exerciselist.Database.ExerciseDatabase;
import com.example.exerciselist.Entity.Move;

import java.util.List;

public class MoveRepository {

    private MoveDao moveDao;

    public MoveRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        moveDao = database.moveDao();
    }

    /**
     * Insert move
     *
     * @param move
     */
    public void insert(Move move) {
        new InsertMoveAsyncTask(moveDao).execute(move);
    }

    private static class InsertMoveAsyncTask extends AsyncTask<Move, Void, Void> {
        private MoveDao moveDao;

        private InsertMoveAsyncTask(MoveDao moveDao) {
            this.moveDao = moveDao;
        }

        @Override
        protected Void doInBackground(Move... moves) {
            moveDao.insert(moves[0]);
            return null;
        }
    }

    /**
     * Update move
     *
     * @param move
     */
    public void update(Move move) {
        new UpdateMoveAsyncTask(moveDao).execute(move);
    }

    private static class UpdateMoveAsyncTask extends AsyncTask<Move, Void, Void> {
        private MoveDao moveDao;

        private UpdateMoveAsyncTask(MoveDao moveDao) {
            this.moveDao = moveDao;
        }

        @Override
        protected Void doInBackground(Move... moves) {
            moveDao.update(moves[0]);
            return null;
        }
    }

    /**
     * Delete move
     *
     * @param move
     */
    public void delete(Move move) {
        new DeleteMoveAsyncTask(moveDao).execute(move);
    }

    private static class DeleteMoveAsyncTask extends AsyncTask<Move, Void, Void> {
        private MoveDao moveDao;

        private DeleteMoveAsyncTask(MoveDao moveDao) {
            this.moveDao = moveDao;
        }

        @Override
        protected Void doInBackground(Move... moves) {
            moveDao.delete(moves[0]);
            return null;
        }
    }

    /**
     * Read all moves with specific exercise id
     *
     * @param exerciseId
     * @return
     */
    public LiveData<List<Move>> getMovesForExercise(int exerciseId) {
        return moveDao.findMovesForExercise(exerciseId);
    }
}
