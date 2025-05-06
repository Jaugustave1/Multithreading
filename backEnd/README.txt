# Calendar Backend Setup – Project: "Dogether"

This package contains the backend calendar system built in Java, including:
- SQL script to create and populate the `calendar` table
- Java model, DAO, and servlet files
- Database connection helper
- Demo HTML + JavaScript for frontend testing

## 1. SQL Setup
Run the following script in your MySQL environment:

> calendar_table_setup.sql

This will create the `calendar` table and insert a few sample events.

## 2. Java Files
Add the following to your Java project:

- model/CalendarEvent.java
- dao/CalendarDAO.java
- servlet/CalendarServlet.java
- DatabaseConnection.java

You must also include `gson-2.x.jar` in your classpath to support JSON serialization.

## 3. Frontend Integration
Use the HTML and JavaScript in:

> calendar_viewer_demo.html

This will show events fetched from:
`http://localhost:8080/taskWebApp/calendar/`

## 4. Deployment Notes
- Deploy to Tomcat or any Java EE server.
- Ensure servlet mapping is active (`@WebServlet` or web.xml).
- Use JDBC for MySQL with correct credentials in `DatabaseConnection.java`.

Let Jahelle know if help is needed integrating into the full project.
