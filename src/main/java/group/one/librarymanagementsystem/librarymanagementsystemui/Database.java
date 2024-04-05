package group.one.librarymanagementsystem.librarymanagementsystemui;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    String url;
    String username;
    String password;

    Database() {
        url = "jdbc:mysql://localhost:3306/library";
        username = "root";
        password = "";
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

}
