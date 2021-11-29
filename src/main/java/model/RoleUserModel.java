package model;

public class RoleUserModel
{
    private String roleName;
    private String userName;

    public RoleUserModel()
    {
    }

    public RoleUserModel(String roleName, String userName)
    {
        this.roleName = roleName;
        this.userName = userName;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
