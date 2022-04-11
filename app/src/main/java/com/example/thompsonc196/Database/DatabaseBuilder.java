package com.example.thompsonc196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.thompsonc196.DAO.AssessDAO;
import com.example.thompsonc196.DAO.CourseDAO;
import com.example.thompsonc196.DAO.TermDAO;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Term;

@Database(entities={Assessment.class, Course.class, Term.class}, version=2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessDAO assessDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "c196Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
