package com.rahulreddy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int Task_id;
    public static void main(String[] args) throws ParseException {
        //System.out.println("hey");
        Scanner scanner=new Scanner(System.in);
        TaskManager taskManager=new TaskManager();
        while(true){
            System.out.println("-------------------------------------------------" +
                    "------------------------------------------------------------" +
                    "----------------");
            System.out.println("Enter number choice");
            System.out.println("1.ToAdd\t2.ToDisplayList\t3.ToDelete" +
                    "\t4.ToSearchById\t5.listByStatus\t6.DisplayById" +
                    "\t7.updateStatus\t8.getPendingTasks\t9.Today's Tasks\t10.Exit");
            int choice=scanner.nextInt();
            selection(choice,taskManager);

        }
    }
    static void selection(int choice,TaskManager taskManager) throws ParseException {
    Scanner scanner=new Scanner(System.in);

        switch (choice){
            case 1://add task
                System.out.println("Enter name to add:");
                String name=scanner.nextLine();
                //System.out.println("No such task!");
                System.out.println("Enter details:");
                String description=scanner.nextLine();
                System.out.println("Enter Date in dd/mm/yyyy format:");
                Date date=new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                System.out.println("Enter Status(CREATED|IN_PROGRESS|DONE):");
                TaskStatus status= TaskStatus.valueOf(scanner.nextLine());
                Task_id=100000 + new Random().nextInt(900000);
                Task task=new Task(Task_id,name,description,date,status);
                taskManager.toAddTask(task);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 2://display all tasks
                List<Task> list=taskManager.toDisplayTask();
                displayAllDetails(list);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 3://
                System.out.println("Enter id to delete:");
                int id=scanner.nextInt();
                System.out.println(id + " is deleted");
                taskManager.toDeleteTask(id);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 4:
                System.out.println("Enter id to Search:");
                int Id=scanner.nextInt();
                Task listOfTasks=taskManager.toSearchById(Id);
                if(listOfTasks==null) {
                    System.out.println("No such task!");
                }
                else {
                    System.out.println("Task " + Id + " Found!");
                    System.out.println(listOfTasks);

                }
                System.out.println("\n\nTotalTasks: " + taskManager.totalTasks());
                break;
            case 5:
                System.out.println("Enter Status: ");
                List<Task> listOfStatus=taskManager.listByStatus(TaskStatus.valueOf(scanner.next()));
                displayAllDetails(listOfStatus);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 6:
                List<Task> listByIdAndName= taskManager.toDisplayTask();
                displayIdAndName(listByIdAndName);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 7:
                System.out.println("Enter Task id: ");
                int iD=scanner.nextInt();
                System.out.println("Enter Status to be Updated: ");
                String str=scanner.next();
                taskManager.updateStatus(iD,TaskStatus.valueOf(str));
                System.out.println("\nSTATUS has been Updated!");
                break;
            case 8:
                System.out.println("Displaying Pending Tasks: \n");
                List<Task> pendingTasks=taskManager.getPendingTasks();
                displayAllDetails(pendingTasks);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 9:
                System.out.println("gettingToday's Tasks: ");
                List<Task> today= taskManager.getTodayTasks();
                displayAllDetails(today);
                System.out.println("\n\nTotalTasks: "+taskManager.totalTasks());
                break;
            case 10:
                System.exit(0);
            default:
                System.out.println("Invalid Input");
        }

    }
    public static void displayAllDetails(List<Task> list){
        if(list.size()==0) {
            System.out.println("No tasks!");
        }
        else {
          //  System.out.println(list);
            for (Task t : list) {
                System.out.println(t);
            }
        }
    }
    public static void displayIdAndName(List<Task> list){
        for(Task t: list){
            System.out.println("Task: "+t.getId()+
                    "\nName: "+t.getName());
        }
    }
}
