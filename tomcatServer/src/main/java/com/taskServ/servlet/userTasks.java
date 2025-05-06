package main.java.com.taskServ.servlet;

import backEnd.sqlDB;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.taskServ.database.jsonLib;
import main.java.com.taskServ.database.sharedDB;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/*")
public class userTasks extends HttpServlet {
    private sqlDB db = sharedDB.getDB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Split path into separate parts
        String path = request.getPathInfo();
        String[] pathParts = path.split("/");


        if (pathParts.length == 3) {
            // If path is /user/getTasks/{userID}
            if (pathParts[1].equals("getTasks")) {

                int userID = Integer.parseInt(pathParts[2]);
                ResultSet rset = db.getTasks(userID);

                try {
                    String str = jsonLib.RsetToJson(rset);
                    out.println(str);
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("{\"message\": \"Error getting tasks\"}");
                }
            }
            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Invalid path\"}");
            }
            // Provide more paths if needed
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"message\": \"Invalid path\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Split path into separate parts
        String path = request.getPathInfo();
        String[] pathParts = path.split("/");

        // Verify path is valid
        if ( pathParts.length == 3 ) {

            // If path is /user/createTask/{userID}
            if ( pathParts[1].equals("createTask") ) {
                int userID = Integer.parseInt(pathParts[2]);

                int listID = Integer.parseInt(request.getParameter("listID"));
                String taskName = request.getParameter("taskName");
                String status = request.getParameter("status");
                int priority = Integer.parseInt(request.getParameter("priority"));
                String dueDate = request.getParameter("dueDate");
                int colorCode = Integer.parseInt(request.getParameter("colorCode"));

                db.createTask(userID, listID, taskName, status, priority, dueDate, colorCode);

                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Task Created\"}");
            }

            // If path is /user/updateTask/{userID}
            else if ( pathParts[1].equals("updateTask") ) {

                int taskID = Integer.parseInt(request.getParameter("taskID"));
                String taskName = request.getParameter("taskName");
                String status = request.getParameter("status");
                int priority = Integer.parseInt(request.getParameter("priority"));
                String dueDate = request.getParameter("dueDate");
                int colorCode = Integer.parseInt(request.getParameter("colorCode"));

                db.updateTask( taskID, taskName, status, priority, dueDate, colorCode);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Task Updated\"}");
            }

            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Invalid path\"}");
            }

        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"message\": \"Invalid path\"}");
        }

    }


}
