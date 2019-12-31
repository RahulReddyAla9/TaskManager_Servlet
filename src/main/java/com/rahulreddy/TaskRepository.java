package com.rahulreddy;

import java.util.List;

public interface TaskRepository {
    public void toAddTask(Task task);
    public List<Task> toDisplayTask();
    public boolean toDeleteTask(int taskId);
    public Task toSearchById(int taskId);
    public List<Task> listByStatus(TaskStatus status);
    public void updateStatus(int taskId, TaskStatus status);
    public int totalTasks();
    public List<Task> getPendingTasks();
    public List<Task> getTodayTasks();
}
