package com.gov.iti.persistence.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static Database myDatabase =null;
    private static HikariDataSource dataSource;

    private Database() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/your_database_name"); // Adjust URL
        config.setUsername("root");
        config.setPassword("12345");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Adjust driver class
        config.setMaximumPoolSize(10); // Optional: Set maximum pool size
        config.setConnectionTimeout(30000); // Optional: Set connection timeout
        config.setDataSource(new HikariDataSource(config));
    }

    public static Database getInstance() {
        if (myDatabase == null) {
            synchronized (Database.class) {
                if (myDatabase == null) {
                    myDatabase = new Database();
                }
            }
        }
        return myDatabase;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeConnection() {
        dataSource.close();
    }
}
