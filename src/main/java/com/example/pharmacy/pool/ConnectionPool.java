package com.example.pharmacy.pool;

import com.example.pharmacy.exception.ServiceException;
import com.example.pharmacy.util.PropertiesStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    static Logger logger = LogManager.getLogger();
    public static final int CAPACITY = 3;
    public static final String PROPERTIES_FILE_NAME = "database.properties";
    private static final Properties properties = new Properties();
    private static ConnectionPool instance;
    private static String databaseUrl;
    private static Lock lock = new ReentrantLock(true);
    private static PropertiesStreamReader propertiesStreamReader = new PropertiesStreamReader();
    private BlockingQueue<ProxyConnection> connections = new LinkedBlockingQueue<>(CAPACITY);
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            logger.fatal("Error by registering driver: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        try (FileReader file = new FileReader(propertiesStreamReader.getFileFromResource(PROPERTIES_FILE_NAME).toFile())){
            properties.load(file);
        } catch (IOException | ServiceException e) {
            logger.fatal("Error loading properties file: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
        databaseUrl = properties.getProperty("db.url");
        for (int i = 0; i < CAPACITY; i++) {
            Connection connection = createConnection(databaseUrl, properties);
            connections.add((ProxyConnection)connection);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (!isCreated.get()) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
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
            connection = connections.take();
        } catch (InterruptedException e) {
            logger.warn("Error by getting connection: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null && connection instanceof ProxyConnection proxy) {
            try {
                connections.put(proxy);
            } catch (InterruptedException e) {
                logger.warn("Error closing connection: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public void destroyPool() {
        for (int i = 0; i < CAPACITY; i++) {
            try {
                connections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.warn("Error by closing connections: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        try {
            DriverManager.deregisterDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            logger.fatal("Error deregistering driver: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private Connection createConnection(String url, Properties properties) {
        try {
            return new ProxyConnection(DriverManager.getConnection(url, properties));
        } catch (SQLException e) {
            logger.fatal("Error creating connection: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }
}
