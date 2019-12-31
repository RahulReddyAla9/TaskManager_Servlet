package com.rahulreddy;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Task implements Comparable<Task>{
    private int id;
    private String name;
    private String description;
    private Date dueDate;
    private TaskStatus status;

    //Constructor
    public Task(){ }

    //Another Constructor
    Task(int id, String name, String description, Date dueDate, TaskStatus status) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat time=new SimpleDateFormat("dd/MM/yyyy");
        return "Task" +
                 + id +
                " { name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + time.format(dueDate) +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(Task task) {
        if(this.getDueDate().compareTo(task.getDueDate())==0)
            return 0;
        else if(this.getDueDate().compareTo(task.getDueDate())<0)
            return -1;
        else
            return 1;
    }
}
