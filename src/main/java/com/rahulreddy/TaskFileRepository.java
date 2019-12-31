package com.rahulreddy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskFileRepository implements TaskRepository{


        private static final String TASKS_JSON_FILE = "/home/rahulr/Desktop/tasks.json";

        private ObjectMapper objectMapper = new ObjectMapper();
        List<Task> tasks;
        File file= new File(TASKS_JSON_FILE);

        public TaskFileRepository() {
            tasks=readFromFile();
        }

        private void writeToFile(List<Task> tasks) {
            try {
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new FileWriter(TASKS_JSON_FILE), tasks);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        private List<Task> readFromFile() {
            if (file.exists()) {
                try {
                    return objectMapper.readValue(file, TaskList.class);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            } else {
                return new ArrayList<>();
            }
        }
    @Override
    public void toAddTask(Task task) {
        tasks.add(task);
        writeToFile(tasks);
    }

    @Override
    public List<Task> toDisplayTask() {
        return readFromFile();
    }

    @Override
    public boolean toDeleteTask(int taskId) {
        for (Task task:tasks){
            if(task.getId()==taskId){
                tasks.remove(task);
                writeToFile(tasks);
                return true;
            }
        }
        return false;
    }

    @Override
    public Task toSearchById(int taskId) {
        for (Task t:tasks){
            if(t.getId()==taskId){
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Task> listByStatus(TaskStatus status) {
        List<Task> list= new ArrayList<>();
        for(Task t:list){
            if(t.getStatus().equals(status)){
                list.add(t);
            }
        }
        return list;
    }

    @Override
    public void updateStatus(int taskId, TaskStatus status) {
        for(Task t: tasks ){
            if(t.getId()==taskId){
                t.setStatus(status);
                writeToFile(tasks);
            }
        }
    }

    @Override
    public int totalTasks() {
        return tasks.size();
    }

    @Override
    public List<Task> getPendingTasks() {
        List<Task> list= new ArrayList<>(); //list is returning pending tasks
        for (Task t:tasks){
            if(t.getStatus().equals(TaskStatus.valueOf("CREATED")) ||
                    t.getStatus().equals(TaskStatus.valueOf("IN_PROGRESS")))
                list.add(t);
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public List<Task> getTodayTasks() {
        List<Task> todayTasks= new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date=new Date();
        String today=simpleDateFormat.format(date);
        for (Task t:tasks){
            try {
                if (t.getDueDate().equals(simpleDateFormat.parse(today)))
                    todayTasks.add(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return todayTasks;
    }
}
