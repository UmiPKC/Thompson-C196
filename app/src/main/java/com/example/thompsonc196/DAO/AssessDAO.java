package com.example.thompsonc196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thompsonc196.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessID ASC")
    List<Assessment> getAllAssess();

    @Query("SELECT * FROM assessments WHERE courseID = :courseID")
    List<Assessment> getCourseAssess(int courseID);
}
