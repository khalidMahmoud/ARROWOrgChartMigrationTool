package dao;

import config.ConnectionManager;
import config.JdbcConfig;
import model.RowModel;
import utils.UnitType;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class LevelDao
{
    String generatedColumns[] = {"NODE_ID"};

    final String EgyptianMissionsAbroad = UnitType.EgyptianMissionsAbroad.getValue();
    final String ExternalParties = UnitType.ExternalParties.getValue();


    public List<RowModel> insertLevel(List<RowModel> topLevel, JdbcConfig jdbcConfig, int parent)
    {
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                for (RowModel rowModel : topLevel)
                {
                    String sql = "";

                    PreparedStatement statement = null;

                    //                    String nodeExistId = checkIfNodeExist(jdbcConfig, rowModel.getName());
                    //                    if (nodeExistId.equals(""))
                    //                    {
                    switch (jdbcConfig.getDbType())
                    {
                        case "oracle":
                            sql = "INSERT INTO CHART_NODE (NODE_ID,ACTIVE, dateCreated,lastUpdated , NODE_NAME, NOTE_TYPE, UNIT_CODE, PARENT_NODE,INHERIT_PRIVILEGE, SECRET_CORRESPONDENCE) VALUES (CHART_NODE_SEQ.nextval,?,?,?,?,?,?,?,?,?)";
                            statement = connection.prepareStatement(sql, generatedColumns);
                            break;
                        case "sqlserver":
                            sql = "INSERT INTO CHART_NODE (ACTIVE, dateCreated,lastUpdated , NODE_NAME, NOTE_TYPE, UNIT_CODE, PARENT_NODE,INHERIT_PRIVILEGE, SECRET_CORRESPONDENCE) VALUES (?,?,?,?,?,?,?,?,?)";
                            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            break;
                        default:
                            break;
                    }

                    statement.setBoolean(1, rowModel.getActive() == 1 ? true : false);
                    statement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
                    statement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                    statement.setString(4, (String) rowModel.getName().replaceFirst("\\s++$", ""));

                    statement.setString(5, rowModel.getCategory());
                    statement.setObject(6, rowModel.getUnitCode().equals("NULL") ? null : rowModel.getUnitCode());

                    if (parent != 0)
                        statement.setInt(7, parent);
                    else
                        statement.setObject(7, null);

                    statement.setBoolean(8, true);
                    statement.setBoolean(9, false);

                    int i = statement.executeUpdate();


                    switch (jdbcConfig.getDbType())
                    {
                        case "oracle":
                            ResultSet generatedKeys = statement.getGeneratedKeys();
                            if (generatedKeys.next())
                            {
                                rowModel.setId(generatedKeys.getInt(1));
                            }
                            break;
                        case "sqlserver":
                            ResultSet generatedKeys_sqlserver = statement.getGeneratedKeys();
                            int generatedKey = 0;
                            if (generatedKeys_sqlserver.next())
                            {
                                rowModel.setId(generatedKeys_sqlserver.getInt(1));
                            }
                            break;
                        default:
                            break;
                    }
                    //                    } else
                    //                    {
                    //                        System.out.println("Node " + rowModel.getName() + ": is already exist");
                    //                        rowModel.setId(Integer.valueOf(nodeExistId));
                    //                    }


                }
            }
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        return topLevel;
    }

    public String checkIfNodeExist(JdbcConfig jdbcConfig, String nodeName)
    {
        String node_id = "";
        ConnectionManager connectionManager = new ConnectionManager();
        try
        {
            Connection connection = connectionManager.getConnection(jdbcConfig);

            if (connection != null && !connection.isClosed())
            {
                String sql = "SELECT NODE_ID FROM CHART_NODE WHERE NODE_NAME = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, nodeName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                {
                    node_id = resultSet.getString("NODE_ID");
                }
            }
        } catch (Exception e)
        {

        }
        return node_id;
    }
}
