package com.chern.pool;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class ConnectionPool implements DataSource, AutoCloseable{

    private final int initialPoolSize;
    private final LinkedBlockingQueue<ProxyConnection> freeConnections;
    private final LinkedBlockingQueue<ProxyConnection> busyConnections;

    public ConnectionPool(DriverManagerDataSource connectionFactory, int initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
        freeConnections = new LinkedBlockingQueue<>(this.initialPoolSize);
        busyConnections = new LinkedBlockingQueue<>(this.initialPoolSize);
        try {
            for (int i = 0; i < this.initialPoolSize; i++) {
                freeConnections.offer(new ProxyConnection(connectionFactory.getConnection(), this));
            }
            if (freeConnections.isEmpty()) {
                throw new RuntimeException("Unable to initialize connection pool");
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException("Unable to initialize connection pool: ", e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
        }
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw  new SQLFeatureNotSupportedException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public boolean releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            try {
                System.out.print(freeConnections.size() + " | " + busyConnections.size() + " -------- ");
                boolean removed = busyConnections.remove(proxyConnection);
                if (removed) {
                    freeConnections.put(proxyConnection);
                    System.out.println(freeConnections.size() + " | " + busyConnections.size());
                }
                return removed;
            } catch (InterruptedException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public void destroyPool() throws RuntimeException {
        for (int i = 0; i < this.initialPoolSize; i++) {
            try {
                ProxyConnection connection = freeConnections.take();
                connection.closeDirectly();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException("Error occurred while destroying connection pool!");
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (true) {
                Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "ConnectionPool{" +
                "freeConnections=" + freeConnections +
                ", busyConnections=" + busyConnections +
                '}';
    }

    @Override
    public void close() throws Exception {
        this.destroyPool();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}