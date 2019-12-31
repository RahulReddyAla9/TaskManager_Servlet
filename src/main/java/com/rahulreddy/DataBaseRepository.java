package com.rahulreddy;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseRepository implements TaskRepository {
    //private Connection con;
    public Statement statement;
    public DataBaseRepository(){
        ConnectionEstablishment();
    }

    public void ConnectionEstablishment() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanager", "user", "password");
            statement=con.createStatement();
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toAddTask(Task task) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
        String date=simpleDateFormat.format(task.getDueDate());
        try {
            //Statement stmt=con.createStatement();
            String q="insert into task values("+task.getId()+",\""+task.getName()+"\",\""
                    +task.getDescription()+"\",\""+date
                    +"\",\""+task.getStatus()+"\")";
            System.out.println(q);
            statement.executeUpdate(q);
//         con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> toDisplayTask() {
        List<Task> getList= new ArrayList<>();
        try {
            //Statement statement= con.createStatement();
            String s="select * from task";
            ResultSet resultSet=statement.executeQuery(s);
            while (resultSet.next()){
                getList.add(createTask(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getList;
    }

    @Override
    public boolean toDeleteTask(int taskId) {
        int totalTasksBeforeDelete=totalTasks();

        int totalTasksAfterDelete;
        try{
           // Statement stmt=con.createStatement();
            String s="delete from task where id="+taskId;
            statement.executeUpdate(s);

            totalTasksAfterDelete=totalTasks();
            if(totalTasksBeforeDelete==totalTasksAfterDelete+1)
                return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Task toSearchById(int taskId) {

        Task task=new Task();
        try{

            //Statement stmt = con.createStatement();
            String s="select * from task where id="+taskId;
            ResultSet resultSet=statement.executeQuery(s);

            if(resultSet.next()){
                task=createTask(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getString(5));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return task;

    }

    @Override
    public List<Task> listByStatus(TaskStatus status) {
        List<Task> tasks=new ArrayList<>();
        try{
           // Statement stmt = con.createStatement();
            String s="select * from task where status='"+status+"'";
            ResultSet resultSet=statement.executeQuery(s);
            while(resultSet.next()){
                Task task=createTask(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getString(5));
                tasks.add(task);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;

    }

    @Override
    public void updateStatus(int taskId, TaskStatus status) {
        try{
           // Statement stmt = con.createStatement();
            String s="update task set status='"+status+"' where id="+taskId;
            statement.executeUpdate(s);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int totalTasks() {
        int getTotalTasks=0;
        try{
            //Statement stmt = con.createStatement();
            String s="select count(*) from task";
            ResultSet resultSet=statement.executeQuery(s);
            if(resultSet.next())
                getTotalTasks = resultSet.getInt(1);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return getTotalTasks;
    }

    @Override
    public List<Task> getPendingTasks() {
        List<Task> tasks=new ArrayList<>();
        try{
          //  Statement stmt = con.createStatement();
            String s="select * from task where status='IN_PROGRESS' or status='CREATED' order by date asc";
            ResultSet resultSet=statement.executeQuery(s);

            while(resultSet.next()){
                Task task=createTask(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getString(5));
                tasks.add(task);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Task> getTodayTasks() {
        List<Task> tasks = new ArrayList<>();
        Date date=new Date();
        String currentDate = dateToString(date, "yyyy/MM/dd");
        try {
          //  Statement stmt = con.createStatement();
            String s="select * from task where date='"+currentDate + "'";
            ResultSet resultSet = statement.executeQuery(s);
            while (resultSet.next()) {
                Task task =createTask(resultSet.getInt(1),
                        resultSet.getString(2),resultSet.getString(3),
                        resultSet.getDate(4),resultSet.getString(5));
                tasks.add(task);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }
//    public void print(ResultSet resultSet){
//        try{
//            System.out.println("ID: "+resultSet.getInt(1) +
//                    "\nTask Name: "+resultSet.getString(2)+
//                    "\nTask Description: "+resultSet.getString(3)+
//                    "\nDue Date: "+resultSet.getDate(4)+
//                    "\nTask Status: "+resultSet.getString(5));
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
    public Task createTask(int id, String name, String description, Date date,String status){
        Task task=new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setDueDate(date);
        task.setStatus(TaskStatus.valueOf(status));
        return task;
    }
    public String dateToString(Date date,String formatOfDate){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(formatOfDate);
        return simpleDateFormat.format(date);
    }

}
