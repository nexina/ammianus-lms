package group.one.librarymanagementsystem.librarymanagementsystemui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Database {
    String url;
    String address;
    int port;
    String username;
    String password;
    String db_name;

    Database() {
        if(DevTools.configFileExists())
        {
            address = DevTools.getConfig("address").toString();
            port = (int) DevTools.getConfig("port");
            username = DevTools.getConfig("db-username").toString();
            password = DevTools.getConfig("db-password").toString();
            db_name = "library";
            url = "jdbc:mysql://"+address+":"+port+"/"+db_name;
        }
    }

    public boolean testConnection(String address, int port)
    {
        String test_url = "jdbc:mysql://"+address+":"+port+"/"+db_name;
        try (Connection connection = DriverManager.getConnection(test_url, username, password)) {
            if (connection != null) {
                return true;
            }
        } catch (SQLException e) {

        }
        return false;
    }


    int query(String x) {
        int response = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement()) {

                int rowsAffected = statement.executeUpdate(x);

                if (rowsAffected > 0) {
                    System.out.println("Task was successful. Rows affected: " + rowsAffected);
                    response = 1; // Set response to 1 indicating successful insertion
                } else {
                    System.out.println("Task was failed. No rows affected.");
                    response = 0; // Set response to 0 indicating no rows affected
                }
            } catch (SQLException e) {
                // Handle database-related exceptions
                e.printStackTrace(); // Log the exception
                // Set response to -1 indicating failure
                response = -1;
            }
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException
            e.printStackTrace(); // Log the exception
            // Set response to -1 indicating failure
            response = -1;
        }
        return response;
    }

    int query(String query, Object... params) {
        int response = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                // Dynamically set parameters if there are any
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Task was successful. Rows affected: " + rowsAffected);
                    response = 1; // Indicate successful execution
                } else {
                    System.out.println("Task failed. No rows affected.");
                    response = 0; // Indicate no rows affected
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception
                response = -1; // Indicate SQL failure
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception
            response = -1; // Indicate ClassNotFound failure
        }
        return response;
    }

    public List<Object[]> queryView(String x) {
        List<Object[]> resultList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(x)) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        row[i - 1] = resultSet.getObject(columnName);
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return resultList;
    }

    public List<Object[]> queryView(String sql, Object... params) {
        List<Object[]> resultList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters in the PreparedStatement
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Process the result set
                while (resultSet.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        row[i - 1] = resultSet.getObject(columnName);
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            // Handle SQLException appropriately
        }

        return resultList;
    }

}
