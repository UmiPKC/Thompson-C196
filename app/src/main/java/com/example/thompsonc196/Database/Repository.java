package com.example.thompsonc196.Database;

import android.app.Application;

import com.example.thompsonc196.DAO.AssessDAO;
import com.example.thompsonc196.DAO.CourseDAO;
import com.example.thompsonc196.DAO.InstructorDAO;
import com.example.thompsonc196.DAO.TermDAO;
import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Course;
import com.example.thompsonc196.Entity.Instructor;
import com.example.thompsonc196.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private AssessDAO mAssessDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private InstructorDAO mInstructorDAO;
    private List<Assessment> mAllAssess;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<Assessment> mCourseAssess;
    private List<Course> mTermCourses;
    private List<Instructor> mAllInstructors;
    private Instructor selectedInstructor;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mAssessDAO = db.assessDAO();
        mCourseDAO = db.courseDAO();
        mTermDAO = db.termDAO();
        mInstructorDAO = db.instructorDAO();
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

    public List<Assessment> getCourseAssess(int courseID) {
        databaseExecutor.execute(()-> {
            mCourseAssess = mAssessDAO.getCourseAssess(courseID);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mCourseAssess;
    }

    public List<Course> getAllCourses() {
        databaseExecutor.execute(()-> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<Course> getTermCourses(int termID) {
        databaseExecutor.execute(()-> {
            mTermCourses = mCourseDAO.getTermCourses(termID);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTermCourses;
    }

    public List<Term> getAllTerms() {
        databaseExecutor.execute(()-> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Instructor> getAllInstructors() {
        databaseExecutor.execute(()-> {
            mAllInstructors = mInstructorDAO.getAllInstructors();
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;
    }

    public Instructor getInstructorByID(int instructorID) {
        databaseExecutor.execute(()-> {
            selectedInstructor = mInstructorDAO.getInstructorByID(instructorID);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return selectedInstructor;
    }
    //overload CRUD functions with Course and Term versions
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

    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Term term) {
        databaseExecutor.execute(()->{
           mTermDAO.insert(term);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Instructor instructor) {
        databaseExecutor.execute(()->{
            mInstructorDAO.insert(instructor);
        });

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Instructor instructor) {
        databaseExecutor.execute(()->{
            mInstructorDAO.update(instructor);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Instructor instructor) {
        databaseExecutor.execute(()->{
            mInstructorDAO.delete(instructor);
        });
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
