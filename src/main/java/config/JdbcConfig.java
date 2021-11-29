package config;

import java.io.Serializable;
import java.util.Objects;

public class JdbcConfig implements Serializable
{
    private String ip;
    private String port;
    private String database;
    private String username;
    private String password;
    private String dbType;
    private String connectionType;

    public JdbcConfig()
    {
    }

    public JdbcConfig(String ip, String port, String database, String username, String password, String dbType, String connectionType)
    {
        this.ip = ip;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.dbType = dbType;
        this.connectionType = connectionType;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getDatabase()
    {
        return database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDbType()
    {
        return dbType;
    }

    public void setDbType(String dbType)
    {
        this.dbType = dbType;
    }

    public String getConnectionType()
    {
        return connectionType;
    }

    public void setConnectionType(String connectionType)
    {
        this.connectionType = connectionType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        JdbcConfig that = (JdbcConfig) o;
        return Objects.equals(ip, that.ip) && Objects.equals(port, that.port) && Objects.equals(database, that.database) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(dbType, that.dbType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ip, port, database, username, password, dbType);
    }


}
