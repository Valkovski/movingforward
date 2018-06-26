package com.epam.training.framework.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavlo Bida
 * 2018
 */

public class Connector {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * A container to keep all the connections in one place
     */
    private static Map<Database, Connection> connectionPool = new HashMap<>();

    static{
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens and returns a connection to specified database.
     * Doesn't allow multiple connections to a single database with the same user.
     * @param   database
     * @return  Connection
     * @throws  SQLException
     */
    public static Connection getConnection(Database database) throws SQLException {
        if(!connectionPool.containsKey(database)) {
            connectionPool.put(database,
                    DriverManager.getConnection(
                            database.getFormattedUrl(),
                            database.getUsername(),
                            database.getPassword()
                    )
            );
        }

        return connectionPool.get(database);
    }

    /**
     * Closes connection quietly, catching SQLException
     * @param   database
     */
    public static void closeConnection(Database database) {
        try {
            if(connectionPool.containsKey(database)) {
                if(connectionPool.get(database) != null) {
                    connectionPool.get(database).close();
                }
                connectionPool.remove(database);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close all connections, that have been opened by this class
     * @throws  SQLException
     */
    public static void closeAllConnections() {
        for(Database database : connectionPool.keySet()) {
            closeConnection(database);
        }
    }
}
