package dao;

import model.CalendarEvent;
import java.sql.*;
import java.util.*;

public class CalendarDAO {
    private Connection conn;

    public CalendarDAO(Connection conn) {
        this.conn = conn;
    }

    public void addEvent(CalendarEvent event) throws SQLException {
        String sql = "INSERT INTO calendar (userID, taskID, eventTime) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, event.getUserID());
            stmt.setInt(2, event.getTaskID());
            stmt.setTimestamp(3, Timestamp.valueOf(event.getEventTime()));
            stmt.executeUpdate();
        }
    }

    public List<CalendarEvent> getUpcomingEvents() throws SQLException {
        String sql = "SELECT * FROM calendar WHERE eventTime >= NOW() ORDER BY eventTime ASC";
        List<CalendarEvent> events = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CalendarEvent event = new CalendarEvent(
                    rs.getInt("userID"),
                    rs.getInt("taskID"),
                    rs.getTimestamp("eventTime").toLocalDateTime()
                );
                event.setCalendarID(rs.getInt("calendarID"));
                events.add(event);
            }
        }
        return events;
    }
}
