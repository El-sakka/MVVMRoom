package com.example.sakkawy.room.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sakkawy.room.Model.TaskEntry;
import com.example.sakkawy.room.Repository.TaskRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    LiveData<List<TaskEntry>> tasks;

    private final TaskRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = TaskRepository.getInstance(this.getApplication());
    }

    public void insertTask(TaskEntry taskEntry){
        mRepository.addTask(taskEntry);
    }

    public void deleteTask(TaskEntry taskEntry){
        mRepository.deleteTask(taskEntry);
    }
    public void updateTask(TaskEntry taskEntry){
        mRepository.updateTask(taskEntry);
    }
    //Since room DAO returns LiveData, it runs on background thread.
    public LiveData<List<TaskEntry>> loadAllTasks(){
        return mRepository.getAllTasks();
    }

    public LiveData<TaskEntry> getTaskById(int taskId){
        return mRepository.getTaskById(taskId);
    }

}
