package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager
{
    private Map<JdbcConfig, Connection> connectionMap = new HashMap<>();

    public Connection getConnection(JdbcConfig jdbcConfig) throws ClassNotFoundException, SQLException
    {
        Connection returnedConnection = null;

        if (jdbcConfig != null)
        {
            if (jdbcConfig.getIp() != null && jdbcConfig.getPort() != null && jdbcConfig.getDatabase() != null && jdbcConfig.getUsername() != null && jdbcConfig.getPassword() != null)
            {
                Connection connection = connectionMap.get(jdbcConfig);
                if (connection != null && !connection.isClosed())
                {
                    returnedConnection = connection;
                } else
                {
                    switch (jdbcConfig.getDbType())
                    {
                        case "oracle":
                            returnedConnection = initOracleConnection(jdbcConfig.getIp(), jdbcConfig.getPort(), jdbcConfig.getDatabase(), jdbcConfig.getUsername(), jdbcConfig.getPassword(), jdbcConfig.getConnectionType());
                            connectionMap.put(jdbcConfig, returnedConnection);
                            break;
                        case "sqlserver":
                            returnedConnection = initSQL_ServerConnection(jdbcConfig.getIp(), jdbcConfig.getPort(), jdbcConfig.getDatabase(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
                            connectionMap.put(jdbcConfig, returnedConnection);
                            break;
                        default:
                            break;
                    }

                }
            }
        }
        return returnedConnection;
    }

    public Connection initSQL_ServerConnection(String ip, String port, String database, String username, String password) throws ClassNotFoundException, SQLException
    {
        String dbURL = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + database;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(dbURL, username, password);
        return conn;
    }

    public Connection initOracleConnection(String ip, String port, String database, String username, String password, String connectionType) throws ClassNotFoundException, SQLException
    {
        String dbURL = "";
        switch (connectionType)
        {
            case "sid":
                dbURL = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + database;
                break;
            case "service":
                dbURL = "jdbc:oracle:thin:@" + ip + ":" + port + "/" + database;
                break;
        }

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(dbURL, username, password);
        return conn;
    }
}
