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

@WebServlet("/user/profile/*")
public class userProfile extends HttpServlet {
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
            // if path is register
            // "/user/profile/register/"
            if (pathParts[1].equals("register")) {
                // Get form params
                String name = request.getParameter("name");
                String password = request.getParameter("pass");
                String email = request.getParameter("email");

                db.register(name, password, email);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Registration Successful\"}");
            }

            // if path is login
            // "/user/profile/login/"
            else if (pathParts[1].equals("login")) {
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

            // if path is updateProfile
            // "/user/profile/updateProfile/"
            else if ( pathParts[1].equals("updateProfile") ) {
                // Get form Params
                int userID = Integer.parseInt(request.getParameter("userID"));
                String name = request.getParameter("name");
                String password = request.getParameter("pass");
                String email = request.getParameter("email");

                db.updateProfile(userID, name, password, email);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println("{\"message\": \"Profile Updated\"}");
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
