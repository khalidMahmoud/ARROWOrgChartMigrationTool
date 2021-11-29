package dao;

import config.ConnectionManager;
import config.JdbcConfig;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao
{
    public User selectUserByUserName(JdbcConfig jdbcConfig, String userName)
    {
        User user = new User();
        PreparedStatement statement = null;
        String sql = "";
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                sql = "SELECT * FROM CMS_USER where USER_NAME = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, userName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    user.setId(resultSet.getString("USER_ID"));
                    user.setName(resultSet.getString("USER_NAME"));
                }

                resultSet.close();
            }
        } catch (Exception e)
        {
            user = null;
        }
        if (user.getId() != null && user.getName() != null)
            return user;
        else
            return null;
    }

    public int addUserToRole(JdbcConfig jdbcConfig, String roleId, String userId)
    {
        int result = 0;
        PreparedStatement statement = null;
        String sql = "";
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                sql = "INSERT INTO NODE_USERS_MEMBERS VALUES (?,?)";
                statement = connection.prepareStatement(sql);
                statement.setString(1, roleId);
                statement.setString(2, userId);
                result = statement.executeUpdate();
            }
        } catch (Exception e)
        {
            result = 0;
        }

        return result;
    }

    public boolean isRoleUserExist(JdbcConfig jdbcConfig, String roleId, String userId)
    {
        ResultSet result = null;
        PreparedStatement statement = null;
        String sql = "";
        boolean exist = false;
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                sql = "SELECT * FROM NODE_USERS_MEMBERS WHERE NODE_ID = ? and USER_ID = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, roleId);
                statement.setString(2, userId);
                result = statement.executeQuery();
                while (result.next())
                {
                    if (result.getString("NODE_ID") != null && result.getString("USER_ID") != null)
                    {
                        exist = true;
                    } else
                    {
                        exist = false;
                    }
                }
                result.close();
            }
        } catch (Exception e)
        {
            exist = true;
        }
        return exist;
    }
}
