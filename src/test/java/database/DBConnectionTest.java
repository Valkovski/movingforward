package database;

import com.epam.training.framework.core.database.Database;
import com.epam.training.framework.core.database.DatabaseHelper;
import com.epam.training.framework.enums.DBEnum;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Pavlo Bida
 * 2018
 */

public class DBConnectionTest {

    private DatabaseHelper databaseHelper;
    private Database database;

    /**
     * Test data, expected to be found in the DB
     */
    private final Map<String, List<String>> COUNTRIES_AND_CITIES = new HashMap<String, List<String>>() {{
        put("Germany", Arrays.asList("Berlin", "Hannover", "Hamburg"));
        put("France", Arrays.asList("Paris", "Marseille", "Lyon"));
        put("Ukraine", Arrays.asList("Kyiv", "Lviv", "Mykolajiv"));
    }};

    /**
     * This is a default database that comes with MySQL application for Windows.
     * In order to run this test, you must install MySQL server to you machine, or specify the URL of the machine that
     * runs MySQL Server.
     * Please pay attention to username and password values and change them in accordance with your database config.
     */
    private final String DB_URL = "localhost:3306";
    private final String DB_NAME = "world";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    /**
     * Queries
     */
    private final String SELECT_ALL_COUNTRY_NAMES = "SELECT Name FROM country";
    private final String SELECT_ALL_CITIES_BY_COUNTRY = "SELECT city.Name FROM city JOIN country ON city.CountryCode = " +
            "country.Code WHERE country.Name='%s';";

    @BeforeClass(description = "Initialize a Database and DatabaseHelper objects with the given data")
    public void setup() {
        database = new Database(DB_URL, DB_NAME, USERNAME, PASSWORD, DBEnum.MYSQL);
        databaseHelper = new DatabaseHelper(database);
    }

    @Test(description = "Verifies that DB contains the countries and the cities, " +
            "specified in COUNTRIES_AND_CITIES constant. If needed to test some more countries or cities - " +
            "just modify the constant")
    public void testCountriesAndCities() {
        List<String> countriesFromDB = databaseHelper.getQueryResultAsStringList(SELECT_ALL_COUNTRY_NAMES);

        COUNTRIES_AND_CITIES.keySet().forEach(country -> {
            List<String> citiesFromDB = databaseHelper.getQueryResultAsStringList(String.format(SELECT_ALL_CITIES_BY_COUNTRY, country));
            List<String> citiesFromCountry = COUNTRIES_AND_CITIES.get(country);

            Assert.assertTrue(countriesFromDB.contains(country),
                    "Database does not contain countries given");
            Assert.assertTrue(citiesFromCountry.stream().allMatch(city -> citiesFromDB.stream().anyMatch(city::contains)),
                    "Database does not contain cities given");
        });
    }
}
