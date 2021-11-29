package business;

import config.JdbcConfig;
import dao.UserDao;
import model.User;

public class UserBusinessManager
{

    UserDao userDao = new UserDao();

    public User getUserByUserName(JdbcConfig jdbcConfig, String userName)
    {
        return userDao.selectUserByUserName(jdbcConfig, userName);
    }

    public int addUserToRole(JdbcConfig jdbcConfig, String roleId, String userId)
    {
        return userDao.addUserToRole(jdbcConfig, roleId, userId);
    }

    public boolean roleUserExist(JdbcConfig jdbcConfig, String roleId, String userId)
    {
        return userDao.isRoleUserExist(jdbcConfig,roleId,userId);
    }
}
