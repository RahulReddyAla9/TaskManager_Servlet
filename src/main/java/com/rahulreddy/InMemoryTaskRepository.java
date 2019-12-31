package com.rahulreddy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {
    List<Task> list=new ArrayList<>();
    public void toAddTask(Task task) {
        list.add(task);
    }
    public List<Task> toDisplayTask(){
        return list;
    }
    public boolean toDeleteTask(int id){
        for(Task x:list){
            if(x.getId()==id){
                list.remove(x);
                return true;
            }
        }
        return false;
    }
    public Task toSearchById(int id){
        for(Task t:list){
            if(t.getId()==id){
                return t;
            }
        }
        return null;
    }
    public List<Task> listByStatus(TaskStatus status){
        List<Task> list= new ArrayList<>();
        for(Task t:list){
            if(t.getStatus().equals(status)){
                list.add(t);
            }
        }
        return list;
    }
    public void updateStatus(int id,TaskStatus status){
        for(Task t: list ){
            if(t.getId()==id){
                t.setStatus(status);
            }
        }
    }

    @Override
    public int totalTasks() {
        return list.size();
    }

    @Override
    public List<Task> getPendingTasks() {
        List<Task> listOfPendingTasks= new ArrayList<>(); //list is returning pending tasks
        for (Task t:list){
            if(t.getStatus().equals(TaskStatus.valueOf("CREATED"))||t.getStatus().equals(TaskStatus.valueOf("IN_PROGRESS")))
                listOfPendingTasks.add(t);
        }
        return listOfPendingTasks;
    }

    public List<Task> getTodayTasks() {
        List<Task> todayTasks= new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        Date date=new Date();
        String today=simpleDateFormat.format(date);
        for (Task t:list){
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
