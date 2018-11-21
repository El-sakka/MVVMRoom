package com.example.sakkawy.room.ViewModel;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sakkawy.room.Model.TaskEntry;
import com.example.sakkawy.room.Repository.TaskRepository;

public class AddTaskViewModel extends ViewModel {

    private TaskRepository mRepository;
    int taskId;

    public AddTaskViewModel(Application application, int taskId) {
        mRepository = TaskRepository.getInstance(application);
        this.taskId = taskId;
    }

    public LiveData<TaskEntry> getTask(){
        return mRepository.getTaskById(taskId);
    }
    public void insetTask(TaskEntry taskEntry){
        mRepository.addTask(taskEntry);
    }
    public void updateTask(TaskEntry taskEntry){
        mRepository.updateTask(taskEntry);
    }

}