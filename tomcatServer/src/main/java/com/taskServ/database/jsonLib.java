package main.java.com.taskServ.database;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import java.sql.ResultSet;
import java.sql.SQLException;

public class jsonLib {

    public static String RsetToJson(ResultSet rset) throws SQLException {
        JsonArray jsonArray = new JsonArray();

        while (rset.next()) {
            JsonObject obj = new JsonObject();
            addValToJson(obj, "listID", rset.getInt("listID"));
            addValToJson(obj, "taskID", rset.getInt("taskID"));
            addValToJson(obj, "taskName", rset.getString("taskName"));
            addValToJson(obj, "status", rset.getString("status"));
            addValToJson(obj, "priority", rset.getInt("priority"));
            addValToJson(obj, "dueDate", rset.getString("dueDate"));
            addValToJson(obj, "colorCode", rset.getInt("colorCode"));
            jsonArray.add(obj);
        }

        return jsonArray.toString();

    }

    public static void addValToJson(JsonObject obj, String key, Object val) {
        if (val == null) {
            obj.add( key, JsonNull.INSTANCE );
        } else if (val instanceof Integer) {
            obj.addProperty( key, (Integer) val );
        } else if (val instanceof String) {
            obj.addProperty( key, (String) val );
        }
    }
}
