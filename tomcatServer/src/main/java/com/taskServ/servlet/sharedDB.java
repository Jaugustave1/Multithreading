package main.java.com.taskServ.servlet;

import backEnd.sqlDB;

// Created to have only one db connection for all servlets
public class sharedDB {
    private static final sqlDB db = new sqlDB();

    public static sqlDB getDB() {
        return db;
    }
}
