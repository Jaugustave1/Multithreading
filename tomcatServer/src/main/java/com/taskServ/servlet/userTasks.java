package main.java.com.taskServ.servlet;

import backEnd.sqlDB;
import java.sql.*;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            // Provide more paths if needed
        }
        else {
            out.println("{\"message\": \"Invalid path\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Split path into separate parts
        String path = request.getPathInfo();
        String[] pathParts = path.split("/");

        // Check if path is valid
        if (pathParts.length == 2) {
            // if path is login
            // "/user/login/"
            if (pathParts[1].equals("login")) {
                // Get form params
                String name = request.getParameter("name");
                String password = request.getParameter("pass");
                try {
                    int userID = db.login(name, password);

                    // If login is invalid, throw IllegalArgument
                    if (userID == -1) {
                        throw new IllegalArgumentException("Invalid username or password");
                    }

                    // form JSON response
                    JsonObject jsonObj = new JsonObject();
                    jsonObj.addProperty("userID", userID);
                    jsonObj.addProperty("message", "Login Successful");

                    // Send success response
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.println(jsonObj.toString());
                } catch (IllegalArgumentException e) {
                    // Handle invalid login
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"message\": \"" + e.getMessage() + "\"}");
                } catch (Exception e) {
                    // Handle unexpected errors
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    out.println("{\"message\": \"Unexpected error\"}");
                    e.printStackTrace(); // Log the error
                }
            }
        } else {
            out.println("{\"message\": \"Invalid path\"}");
        }

    }
}
