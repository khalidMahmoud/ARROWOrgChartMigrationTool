package business;

import config.JdbcConfig;
import dao.RoleDao;
import model.Role;

public class RoleBusinessManager
{

    RoleDao roleDao = new RoleDao();

    public Role getRoleByName(JdbcConfig jdbcConfig, String name)
    {
        return roleDao.selectRoleByName(jdbcConfig, name);
    }
}
