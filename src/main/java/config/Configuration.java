package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration
{
    public String configPath = "config.properties";
    public Map<String, String> getDBProperties()
    {
        Map dbProperties = new HashMap();

        try
        {
            InputStream input = new FileInputStream(configPath);
            Properties properties = new Properties();
            properties.load(input);
            dbProperties.put("ip", properties.getProperty("ip"));
            dbProperties.put("port", properties.getProperty("port"));
            dbProperties.put("database", properties.getProperty("database"));
            dbProperties.put("username", properties.getProperty("username"));
            dbProperties.put("password", properties.getProperty("password"));
            dbProperties.put("dbType", properties.getProperty("dbType"));
            dbProperties.put("connectionType", properties.getProperty("connectionType"));
        } catch (Exception exception)
        {
            dbProperties = new HashMap();
        }
        return dbProperties;
    }

    public Map getExcelSheetName()
    {
        Map props = new HashMap();
        try
        {
            InputStream input = new FileInputStream(configPath);
            Properties properties = new Properties();
            properties.load(input);
            props.put("excelSheetName",properties.getProperty("excelSheetName"));
            props.put("withCode",properties.getProperty("withCode"));
            props.put("dbType",properties.getProperty("dbType"));
            props.put("withUsers",properties.getProperty("withUsers"));
            props.put("roleUsersSheetName",properties.getProperty("roleUsersSheetName"));
            props.put("migrateOrgChart",properties.getProperty("migrateOrgChart"));
            return props;
        } catch (Exception exception)
        {
            return null;
        }
    }
}
