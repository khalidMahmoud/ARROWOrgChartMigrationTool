package business;

import config.JdbcConfig;
import model.RowModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreparationBusinessManager
{
    List<RowModel> newChildren = new ArrayList<>();
    ChildrenBusinessManager childrenBusinessManager = new ChildrenBusinessManager();
    LevelBusinessManager levelBusinessManager = new LevelBusinessManager();

    public List<RowModel> getFirstLevel(List<RowModel> orgChart)
    {
        return orgChart.stream().filter((RowModel rowModel) ->
        {
            return rowModel.getParent() == 0;
        }).collect(Collectors.toList());
    }

    public void addChildren(List<RowModel> orgChart, List<RowModel> topLevel, JdbcConfig jdbcConfig)
    {
        for (RowModel rowModel : topLevel)
        {
            boolean hasChildren = childrenBusinessManager.hasChildren(orgChart, rowModel);
            if (hasChildren)
            {
                List<RowModel> children = childrenBusinessManager.getChildren(orgChart, rowModel);

                List<RowModel> rowModels = levelBusinessManager.addLevels(children, jdbcConfig, rowModel.getId());

                for (RowModel row : rowModels)
                {
                    if (row.getType().equals("Internal"))
                    {
                        newChildren.add(new RowModel(row.getIndex() + 1000, "موظفى " + row.getName(), row.getId(), row.getId(), row.getActive(), "ROLE", "ROLE", ""));
                        newChildren.add(new RowModel(row.getIndex() + 10000, "مدير " + row.getName(), row.getId(), row.getId(), row.getActive(), "ROLE", "ROLE", ""));
                        levelBusinessManager.addLevels(newChildren, jdbcConfig, row.getId());
                        newChildren = new ArrayList<>();
                    }
                }

                addChildren(orgChart, rowModels, jdbcConfig);
            }
        }
    }


}
