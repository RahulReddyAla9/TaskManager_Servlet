package com.rahulreddy;

import java.util.List;

public class TaskManager {

    TaskRepository repository=new DataBaseRepository();

    public void toAddTask(Task task) {repository.toAddTask(task); } //Adding Task

    public List<Task> toDisplayTask(){                              //Displaying All Tasks
        return repository.toDisplayTask();
    }

    public void toDeleteTask(int taskId){                           //Delete a specific task using Id
        repository.toDeleteTask(taskId);
    }

    public Task toSearchById(int taskId){                           //search a task by Id
        return repository.toSearchById(taskId);
    }

    public List<Task> listByStatus(TaskStatus status){              //display a task using status
        return repository.listByStatus(status);
    }

    public void updateStatus(int taskId,TaskStatus status) {        //updating status of a specific task using Id
        repository.updateStatus(taskId, status);
    }

    public int totalTasks(){return repository.totalTasks();}        //Returns total  Number of Tasks

    public List<Task> getPendingTasks(){return repository.getPendingTasks(); }  //returns pending tasks(CREATED/
                                                                                // IN_PROGRESS)

    public List<Task> getTodayTasks(){ return repository.getTodayTasks();}    //returns today's tasks based on the date
}

