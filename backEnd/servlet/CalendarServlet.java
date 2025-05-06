package servlet;

import com.google.gson.Gson;
import dao.CalendarDAO;
import model.CalendarEvent;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import java.util.List;

@WebServlet("/calendar/")
public class CalendarServlet extends HttpServlet {
    private CalendarDAO calendarDAO;

    @Override
    public void init() {
        Connection conn = DatabaseConnection.getConnection();
        calendarDAO = new CalendarDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        try {
            List<CalendarEvent> events = calendarDAO.getUpcomingEvents();
            String json = new Gson().toJson(events);
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
