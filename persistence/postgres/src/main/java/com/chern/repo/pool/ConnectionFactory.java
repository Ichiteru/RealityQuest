package com.chern.repo.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String PROPERTIES_PATH = "db.properties";
    private static final String DRIVER_NAME_KEY = "driver";
    private static final String URL_PROPERTY_NAME = "url";
    private static final Properties props = new Properties();

    private static ConnectionFactory instance;

    static {
        String driverName = null;
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            props.load(inputStream);
            driverName = (String) props.get(DRIVER_NAME_KEY);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Can't register driver: " + driverName);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Can't load properties file");
        }
    }

    ConnectionFactory() {
    }

    static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    Connection getConnection() throws RuntimeException {
        try {
            Connection connection = DriverManager.getConnection(props.getProperty(URL_PROPERTY_NAME), props);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to database failed!", e);
        }
    }
}
