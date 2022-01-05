package dao;

import config.ConnectionManager;
import config.JdbcConfig;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDao
{
    public Role selectRoleByName(JdbcConfig jdbcConfig, String name)
    {
        Role role = new Role();
        PreparedStatement statement = null;
        String sql = "";
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                sql = "SELECT * FROM CHART_NODE where NODE_NAME = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    role.setId(resultSet.getString("NODE_ID"));
                    role.setName(resultSet.getString("NODE_NAME"));
                }
                resultSet.close();
            }
        } catch (Exception e)
        {
            role = null;
        }

        if (role.getId() != null && role.getName() != null)
            return role;
        else
            return null;
    }
    public Role getRoleByUnitCode(JdbcConfig jdbcConfig, String unitCode)
    {
        Role role = new Role();
        PreparedStatement statement = null;
        String sql = "";
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                sql = "SELECT * FROM CHART_NODE where UNIT_CODE = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, unitCode);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    role.setId(resultSet.getString("NODE_ID"));
                    role.setName(resultSet.getString("NODE_NAME"));
                }
                resultSet.close();
            }
        } catch (Exception e)
        {
            role = null;
        }

        if (role.getId() != null && role.getName() != null)
            return role;
        else
            return null;
    }
}
