package model;

import java.time.LocalDateTime;

public class CalendarEvent {
    private int calendarID;
    private int userID;
    private int taskID;
    private LocalDateTime eventTime;

    public CalendarEvent(int userID, int taskID, LocalDateTime eventTime) {
        this.userID = userID;
        this.taskID = taskID;
        this.eventTime = eventTime;
    }

    public int getCalendarID() { return calendarID; }
    public void setCalendarID(int calendarID) { this.calendarID = calendarID; }
    public int getUserID() { return userID; }
    public int getTaskID() { return taskID; }
    public LocalDateTime getEventTime() { return eventTime; }
}
