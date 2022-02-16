package com.chern.repo.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPool {

    private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static final Lock lock = new ReentrantLock(true);
    private static final int INITIAL_POOL_SIZE = 10;

    private static ConnectionPool instance = null;

    private final LinkedBlockingQueue<ProxyConnection> freeConnections;
    private final LinkedBlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE);
        busyConnections = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE);
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                freeConnections.offer((ProxyConnection) connectionFactory.getConnection());
            }
            if (freeConnections.isEmpty()) {
                throw new RuntimeException("Unable to initialize connection pool");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to initialize connection pool: ", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
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

    public boolean releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            try {
                boolean removed = busyConnections.remove(proxyConnection);
                if (removed) {
                    freeConnections.put(proxyConnection);
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
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
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
}