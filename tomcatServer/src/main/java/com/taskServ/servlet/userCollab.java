package main.java.com.taskServ.servlet;

import backEnd.sqlDB;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.taskServ.database.sharedDB;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/collab/*")
public class userCollab extends HttpServlet {
    private sqlDB db = sharedDB.getDB();

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
            // if path is "/user/collab/create"
            if (pathParts[1].equals("create")) {
                int listID = Integer.parseInt(request.getParameter("listID"));
                db.createCollaboration(listID);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Collaboration Created\"}");
            }

            // if path is "/user/collab/addUser"
            else if (pathParts[1].equals("addUser")) {
                int userID = Integer.parseInt(request.getParameter("userID"));
                int collabID = Integer.parseInt(request.getParameter("collabID"));
                db.addCollaborator(collabID, userID);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"User Added\"}");
            }

            // if path is "/user/collab/removeUser"
            else if (pathParts[1].equals("removeUser")) {
                int userID = Integer.parseInt(request.getParameter("userID"));
                int collabID = Integer.parseInt(request.getParameter("collabID"));
                db.removeCollaborator(collabID, userID);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"User Removed\"}");
            }

            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"message\": \"Invalid path\"}");
            }

        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"message\": \"Invalid path\"}");
        }

    }

}
