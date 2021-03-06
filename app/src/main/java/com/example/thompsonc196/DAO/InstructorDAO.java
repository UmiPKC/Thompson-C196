package com.example.thompsonc196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thompsonc196.Entity.Instructor;

import java.util.List;

@Dao
public interface InstructorDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM instructors ORDER BY instructorID ASC")
    List<Instructor> getAllInstructors();

    @Query("SELECT * FROM instructors WHERE instructorID = :instructorID")
    Instructor getInstructorByID(int instructorID);
}
