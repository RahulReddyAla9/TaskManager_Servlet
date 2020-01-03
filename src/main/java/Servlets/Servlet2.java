package Servlets;

import com.rahulreddy.Task;
import com.rahulreddy.TaskManager;
import com.rahulreddy.TaskStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet2 extends HttpServlet {
    public Servlet2(){}
    TaskManager taskManager=new TaskManager();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        int taskId=Integer.parseInt(req.getParameter("id"));
        Task task=taskManager.toSearchById(taskId);

        if(task==null){
            out.println("No Such Task!");
            resp.setStatus(404);
        }else {
            out.println(task);
            resp.setStatus(201);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        String status=(req.getParameter("status"));
        int taskId=Integer.parseInt(req.getParameter("id"));
        Task task=taskManager.toSearchById(taskId);

        if(task==null){
            out.println(" TaskId not matching or invalid TaskId");
            resp.setStatus(404);
        }else {
            out.println("Task Status Updated!");
            taskManager.updateStatus(taskId,TaskStatus.valueOf(status));
            Task newTask=taskManager.toSearchById(taskId);
            out.println(newTask);
            resp.setStatus(201);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId=Integer.parseInt(req.getParameter("id"));
        Task task=taskManager.toSearchById(taskId);
        PrintWriter out= resp.getWriter();
        if(task==null){
            out.println(" TaskId not matching or invalid TaskId");
            resp.setStatus(404);
        }else {
            out.println("Task id: " + taskId + " has been deleted!");
            taskManager.toDeleteTask(taskId);
        }
    }

}
