package io.yunjiao.apijson.orm;

import apijson.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * sql 连接提供者
 *
 * @author yangyunjiao
 */
public interface SqlConnectProvider {
    Logger log = LoggerFactory.getLogger(SqlConnectProvider.class);

    default void getConnectionFromDataSource(String key) throws Exception {
        Connection c = getConnection(key);
        if (c == null || c.isClosed()) {
            synchronized (this) {
                c = getConnection(key);
                if (c == null || c.isClosed()) {
                    DataSource dataSource = getDataSource();
                    Connection newCon = dataSource.getConnection();
                    if (log.isDebugEnabled()) {
                        log.debug("Successfully obtained a connection from data source: {}->{}", key, newCon);
                    }
                    putConnection(key, newCon);
                }
            }
        }
    }

    @NotNull
    Connection getConnection(String key) throws Exception;

    Connection putConnection(String key, Connection newCon) throws Exception;

    DataSource getDataSource();
}
