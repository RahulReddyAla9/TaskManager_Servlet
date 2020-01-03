package Servlets;

import com.rahulreddy.Task;
import com.rahulreddy.TaskManager;
import com.rahulreddy.TaskStatus;
import org.json.JSONArray;

import javax.naming.Name;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servlet extends HttpServlet {

    public Servlet(){

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskManager taskManager=new TaskManager();

        List<Task> tasks= taskManager.toDisplayTask();
        JSONArray array=new JSONArray(tasks);
        PrintWriter out= resp.getWriter();
        out.println(array);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Task task=new Task();
        int Task_id=100000 + new Random().nextInt(900000);
        String Name=req.getParameter("name");
        String description=req.getParameter("description");
        String date=req.getParameter("date");
        String Status=req.getParameter("status");

        task.setId(Task_id);
        task.setName(Name);
        task.setDescription(description);
        try {
            task.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setStatus(TaskStatus.valueOf(Status));
        TaskManager taskManager=new TaskManager();
        taskManager.toAddTask(task);
        resp.setStatus(201);
    }
}
