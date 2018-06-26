package com.epam.training.framework.core.database;

import com.epam.training.framework.enums.DBEnum;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * Created by Pavlo Bida
 * 2018
 */

public class Database {

    static final String MYSQL_DB_URL = "jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private String dbUrl;
    private String dbName;
    private String username;
    private String password;
    private DBEnum engine;

    /**
     * Database class is initialized with all the data, needed to establish a connection
     * Example:
     * @param dbUrl     "localhost:3306"
     * @param dbName    "world"
     * @param username  "root"
     * @param password  "1234"
     * @param engine    DBEnum.MYSQL
     */
    public Database(String dbUrl, String dbName, String username, String password, DBEnum engine) {
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.engine = engine;
    }

    /**
     * Builds a URL to be passed to the java.sql.DriverManager to establish a connection
     * @return
     */
    public String getFormattedUrl() {
        String result = StringUtils.EMPTY;
        switch (engine) {
            case MYSQL: return String.format(MYSQL_DB_URL, dbUrl, dbName);
            case POSTGRE_SQL: return "";
            case MICROSOFT_SQL_SERVER: return "";
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("URL generation for " + engine + " is not implemented yet");
        }
        return result;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public DBEnum getEngine() {
        return engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database database = (Database) o;
        return Objects.equals(dbUrl, database.dbUrl) &&
                Objects.equals(dbName, database.dbName) &&
                Objects.equals(username, database.username) &&
                Objects.equals(password, database.password) &&
                engine == database.engine;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dbUrl, dbName, username, password, engine);
    }
}
