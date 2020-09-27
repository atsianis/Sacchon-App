package com.pfizer.sacchon.team3.security.dao;

import org.restlet.Context;

import java.sql.*;
import java.util.Objects;

public class ApplicationUserPersistence   {

    // Singleton pattern.
    private static ApplicationUserPersistence applicationUserPersistence = new ApplicationUserPersistence();
    private ApplicationUserPersistence() {
    }

    public static synchronized ApplicationUserPersistence getApplicationUserPersistence() {
        return applicationUserPersistence;
    }

    protected Connection getConnection() throws SQLException {
        Context.getCurrentLogger().finer("Get a fresh connection to database");
        Connection result = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);
        Context.getCurrentLogger().finer("Got a fresh connection to database");
        return result;
    }

    protected void releaseConnection(Connection connection) throws SQLException {
        Context.getCurrentLogger().finer(
                "Release connection: " + Objects.toString(connection));
        if (connection != null) {
            connection.close();
            Context.getCurrentLogger().finer(
                    "Connection released: " + Objects.toString(connection));
        }

    }

}