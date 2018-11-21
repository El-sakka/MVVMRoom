package com.example.sakkawy.room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.sakkawy.room.DataBase.AppDatabase;
import com.example.sakkawy.room.DataBase.TaskDao;
import com.example.sakkawy.room.Executors.AppExecutors;
import com.example.sakkawy.room.Model.TaskEntry;

import java.util.List;

public class TaskRepository {
    private static final String TAG = "TaskRepository";

    private static final Object LOCK = new Object();
    private static TaskRepository sInstance;
    private AppDatabase mDb;
    private final TaskDao taskDao;
    private final AppExecutors appExecutors;

    public TaskRepository(Application application) {
        mDb = AppDatabase.getsInstance(application.getApplicationContext());
        taskDao = mDb.taskDao();
        appExecutors = AppExecutors.getInstance();
    }

    public synchronized static TaskRepository getInstance(Application application) {
        Log.d(TAG, "Getting the repository");
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new TaskRepository(application);
                Log.d(TAG, "made new repository");
            }
        }
        return sInstance;
    }

    public void addTask(final TaskEntry taskEntry){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(taskEntry);
            }
        });
    }

    public void updateTask(final TaskEntry taskEntry){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updataTask(taskEntry);
            }
        });
    }

    public void deleteTask(final TaskEntry taskEntry){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "running on background Thread");
                taskDao.deleteTask(taskEntry);
            }
        });
    }

    public LiveData<List<TaskEntry>> getAllTasks(){
        return taskDao.loadAllTasks();
    }

    public LiveData<TaskEntry> getTaskById(int taskId){
        return taskDao.loadTaskById(taskId);
    }
}
