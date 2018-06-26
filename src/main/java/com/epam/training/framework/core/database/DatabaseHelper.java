package com.epam.training.framework.core.database;

import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.training.framework.core.database.Connector.*;

/**
 * Created by Pavlo Bida
 * 2018
 */

public class DatabaseHelper {

    private final int FIRST_COLUMN_INDEX = 1;
    private Database database;

    /**
     * DatabaseHelper needs to be initialized with a database, which it should work with
     * @param database
     */
    public DatabaseHelper(Database database) {
        this.database = database;
    }

    /**
     * Executes given query to the DB and returns result as int.
     * Immediately closes the connection after query execution.
     * @param   query representation in String
     * @return  the first result from the first column
     */
    public int getQueryResultAsInt(String query) {
        int result = 0;
        try {
            result = getConnection(database)
                    .createStatement()
                    .executeQuery(query)
                    .getInt(FIRST_COLUMN_INDEX);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(database);
        }
        return result;
    }

    /**
     * Executes given query to the DB and returns result as String.
     * Immediately closes the connection after query execution.
     * @param   query representation in String
     * @return  the first result from the first column
     */
    public String getQueryResultAsString(String query) {
        String result = StringUtils.EMPTY;
        try {
            result = getConnection(database)
                    .createStatement()
                    .executeQuery(query)
                    .getString(FIRST_COLUMN_INDEX);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(database);
        }
        return result;
    }

    /**
     * Executes given query to the DB and returns result as List<String>.
     * Immediately closes the connection after query execution.
     * @param   query representation in String
     * @return  results from the first column
     */
    public List<String> getQueryResultAsStringList(String query) {
        List<String> result = new ArrayList<>();
        try {
            ResultSet resultSet = getConnection(database)
                    .createStatement()
                    .executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getString(FIRST_COLUMN_INDEX));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(database);
        }
        return result;
    }

    /**
     * Executes given query to the DB and returns result as List<Integer>.
     * Immediately closes the connection after query execution.
     * @param   query representation in String
     * @return  results from the first column
     */
    public List<Integer> getQueryResultAsIntegerList(String query) {
        List<Integer> result = new ArrayList<>();
        try {
            ResultSet resultSet = getConnection(database)
                    .createStatement()
                    .executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getInt(FIRST_COLUMN_INDEX));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(database);
        }
        return result;
    }

}
