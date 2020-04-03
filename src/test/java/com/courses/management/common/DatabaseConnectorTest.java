package com.courses.management.common;

import com.courses.management.config.DatabaseConnector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnectorTest {
    private static final Logger LOG = LogManager.getLogger(DatabaseConnector.class);
    private final HikariDataSource ds;

    public DatabaseConnectorTest() {
        HikariConfig config = new HikariConfig();
        final Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceAsStream = classLoader.getResourceAsStream("application-test.properties")){
            properties.load(resourceAsStream);
        } catch (IOException e) {
            LOG.error("error loading application-test properties", e);
            throw new RuntimeException(e);
        }
        config.setJdbcUrl(properties.getProperty("jdbc.url"));
        ds = new HikariDataSource(config);
    }

    public DataSource getDataSource(){
        return ds;
    }
}
