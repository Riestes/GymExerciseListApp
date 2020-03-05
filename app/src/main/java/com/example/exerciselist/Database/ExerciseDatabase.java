package com.example.exerciselist.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.exerciselist.Daos.ExerciseDao;
import com.example.exerciselist.Daos.MoveDao;
import com.example.exerciselist.Entity.Exercise;
import com.example.exerciselist.Entity.Move;

@Database(entities = {Exercise.class, Move.class}, version = 1)
public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public abstract MoveDao moveDao();

    public static synchronized ExerciseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExerciseDatabase.class, "exercise_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //Populating database
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;
        private MoveDao moveDao;

        private PopulateDbAsyncTask(ExerciseDatabase database) {
            exerciseDao = database.exerciseDao();
            moveDao = database.moveDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // 1
            exerciseDao.insert(new Exercise("Rinta/Olkapää/Ojentajat"));
            // 2
            exerciseDao.insert(new Exercise("Selkä/Hauis/Vatsa"));
            // 3
            exerciseDao.insert(new Exercise("Jalat/Vatsa"));

            // 1
            moveDao.insert((new Move("Penkkipunnerrus", 3, 10, 62.5f, 1)));
            moveDao.insert((new Move("Vinopenkki käsipainoilla", 3, 10, 22.5f, 1)));
            moveDao.insert((new Move("Yhden käden pystypunnerrus", 3, 10, 17.5f, 1)));
            moveDao.insert((new Move("Vipunostot eteen", 3, 10, 10f, 1)));
            moveDao.insert((new Move("Vipunostot sivuille", 3, 10, 10f, 1)));
            moveDao.insert((new Move("Dippipunnerrus", 3, 5, 0, 1)));
            moveDao.insert((new Move("Ojentajapunnerrus taljassa", 3, 10, 55f, 1)));

            // 2
            moveDao.insert((new Move("Maastaveto", 3, 10, 45f, 2)));
            moveDao.insert((new Move("Ylätalja", 3, 10, 55f, 2)));
            moveDao.insert((new Move("Alatalja", 3, 10, 45f, 2)));
            moveDao.insert((new Move("Hauiskääntö käsipainoilla", 3, 10, 12.5f, 2)));
            moveDao.insert((new Move("Hammerkääntö scott-penkissä", 3, 10, 12.5f, 2)));
            moveDao.insert((new Move("Vatsalihasliikkeet", 3, 10, 0f, 2)));

            // 3
            moveDao.insert((new Move("Kyykky", 3, 10, 60f, 3)));
            moveDao.insert((new Move("Polven ojennus", 3, 10, 55f, 3)));
            moveDao.insert((new Move("Polven koukistus", 3, 10, 57.5f, 3)));
            moveDao.insert((new Move("Pohkeet", 3, 10, 80f, 3)));
            moveDao.insert((new Move("Selän ojennukset", 3, 10, 10f, 3)));
            moveDao.insert((new Move("Vatsalihakset", 3, 10, 0, 3)));

            return null;
        }
    }
}
