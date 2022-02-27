package com.example.thompsonc196.Database;

import android.app.Application;

import com.example.thompsonc196.DAO.AssessDAO;
import com.example.thompsonc196.DAO.CourseDAO;
import com.example.thompsonc196.DAO.TermDAO;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private AssessDAO mAssessDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private List<Assessment> mAllAssess;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessDAO = db.assessDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
    }

    public List<Assessment> getAllAssess() {
        databaseExecutor.execute(()-> {
            mAllAssess = mAssessDAO.getAllAssess();
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssess;
    }
    public void insert(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessDAO.insert(assessment);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
