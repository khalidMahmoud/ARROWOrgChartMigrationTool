package orgChartMigration;

import business.*;
import config.Configuration;
import config.JdbcConfig;
import model.Role;
import model.RoleUserModel;
import model.RowModel;
import model.User;

import java.util.*;
import java.util.stream.Collectors;

public class MainApp
{

    static JdbcConfig jdbcConfig = new JdbcConfig();
    static String dbType = "";
    static boolean withUsers = false;
    static boolean migrateOrgChart = false;
    static List<RowModel> otherInternal = new ArrayList<>();

    public static void main(String[] args)
    {
        PreparationBusinessManager preparationBusiness = new PreparationBusinessManager();
        ExcelSheetBusinessManager excelSheetBusiness = new ExcelSheetBusinessManager();
        LevelBusinessManager firstLevelBusiness = new LevelBusinessManager();
        RoleBusinessManager roleBusinessManager = new RoleBusinessManager();
        UserBusinessManager userBusinessManager = new UserBusinessManager();
        LevelBusinessManager levelBusinessManager = new LevelBusinessManager();

        Map<String, String> dbProperties = getDBProperties();
        jdbcConfig = new JdbcConfig(dbProperties.get("ip"), dbProperties.get("port"), dbProperties.get("database"), dbProperties.get("username"), dbProperties.get("password"), dbProperties.get("dbType"), dbProperties.get("connectionType"));
        withUsers = Boolean.parseBoolean(new Configuration().getExcelSheetName().get("withUsers").toString());
        migrateOrgChart = Boolean.parseBoolean(new Configuration().getExcelSheetName().get("migrateOrgChart").toString());

        if (migrateOrgChart)
        {
            System.out.println("Reading org chart excel sheet ...");
            List<RowModel> orgChart = excelSheetBusiness.readOrgChartFromExcel_mode(new Configuration().getExcelSheetName().get("excelSheetName").toString());
            List<RowModel> topLevel = preparationBusiness.getFirstLevel(orgChart);
            List<RowModel> internal = topLevel.stream().filter((RowModel rowModel) ->
            {
                return rowModel.getType().equals("Internal");
            }).collect(Collectors.toList());
            internal = internal.stream()
                    .filter(x -> !x.getName().equals("إدارة شؤون اللجان"))
                    .collect(Collectors.toList());


            List<RowModel> external = topLevel.stream().filter((RowModel rowModel) ->
            {
                return rowModel.getType().equals("External");
            }).collect(Collectors.toList());

            internal = firstLevelBusiness.addLevels(internal, jdbcConfig, 2);
            for (RowModel rowModel : internal)
            {
                otherInternal.add(new RowModel(rowModel.getIndex() + 1000, "موظفى " + rowModel.getName(), rowModel.getId(), rowModel.getIndex(), rowModel.getActive(), "ROLE", "ROLE", ""));
                otherInternal.add(new RowModel(rowModel.getIndex() + 10000, "مدير " + rowModel.getName(), rowModel.getId(), rowModel.getIndex(), rowModel.getActive(), "ROLE", "ROLE", ""));
                levelBusinessManager.addLevels(otherInternal, jdbcConfig, rowModel.getId());
                otherInternal = new ArrayList<>();
            }

            external = firstLevelBusiness.addLevels(external, jdbcConfig, 3);
            preparationBusiness.addChildren(orgChart, internal, jdbcConfig);
            preparationBusiness.addChildren(orgChart, external, jdbcConfig);
        }

        if (withUsers)
        {
            System.out.println("Insert users to roles ...");
            List<RoleUserModel> roleUsers = excelSheetBusiness.readRoleUsers(new Configuration().getExcelSheetName().get("roleUsersSheetName").toString());
            for (RoleUserModel roleUserModel : roleUsers)
            {
                Role roleByName = roleBusinessManager.getRoleByName(jdbcConfig, roleUserModel.getRoleName().replaceFirst("\\s++$", ""));
                if (roleByName != null)
                {
                    User userByUserName = userBusinessManager.getUserByUserName(jdbcConfig, roleUserModel.getUserName().replaceFirst("\\s++$", ""));
                    if (userByUserName != null)
                    {
                        if (!userBusinessManager.roleUserExist(jdbcConfig, roleByName.getId(), userByUserName.getId()))
                            userBusinessManager.addUserToRole(jdbcConfig, roleByName.getId(), userByUserName.getId());
                        else
                        {
                            System.out.println("User: " + userByUserName.getName() + " is already exist in role: " + roleByName.getName());
                        }
                    } else
                    {
                        System.out.println("User " + roleUserModel.getUserName() + ": not found!");
                    }
                } else
                {
                    System.out.println("Role " + roleUserModel.getRoleName() + ": not found!");
                }
            }
        }
        System.out.println("......Done!......");
    }

    //    Read database properties from properties file
    private static Map<String, String> getDBProperties()
    {
        Configuration configuration = new Configuration();
        return configuration.getDBProperties();
    }

}
