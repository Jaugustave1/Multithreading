package main.java.com.taskServ.servlet;

import backEnd.sqlDB;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.java.com.taskServ.database.sharedDB;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/list/*")
public class userList extends HttpServlet{
    private sqlDB db = sharedDB.getDB();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Split path into separate parts
        String path = request.getPathInfo();
        String[] pathParts = path.split("/");

        // Verify path is valid
        if ( pathParts.length == 2 ) {

            // If path is "/user/list/createList"
            if ( pathParts[1].equals("createList") ) {
                // Get form params
                String listName = request.getParameter("listName");
                int userID = Integer.parseInt(request.getParameter("userID"));

                db.createList(userID, listName);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"List Created\"}");
            }
            // if path is "/user/list/updateList"
            else if ( pathParts[1].equals("updateList") ) {
                // get form params
                int listID = Integer.parseInt(request.getParameter("listID"));
                int ownerID = Integer.parseInt(request.getParameter("ownerID"));
                String listName = request.getParameter("listName");

                db.updateList(listID, ownerID, listName);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"List Updated\"}");
            }

            // If path is "/user/list/deleteList"
            else if ( pathParts[1].equals("deleteList") ) {
                response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                out.println("{\"message\": \"Unimplemented\"}");
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
