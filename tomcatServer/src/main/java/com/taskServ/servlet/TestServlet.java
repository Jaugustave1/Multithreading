package main.java.com.taskServ.servlet;

import backEnd.sqlDB;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/test/*")
public class TestServlet extends HttpServlet {
    private final sqlDB db = sharedDB.getDB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        ResultSet rset = db.getAllTasks();
        try {
            String str = jsonLib.RsetToJson(rset);
            out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"message\": \"Error getting tasks\"}");
        }


    }

}