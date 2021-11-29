package business;

import model.RowModel;

import java.util.List;
import java.util.stream.Collectors;

public class ChildrenBusinessManager
{
    public boolean hasChildren(List<RowModel> allRows, RowModel parent)
    {
        return checkHaseChildren(allRows, parent);
    }

    private boolean checkHaseChildren(List<RowModel> allRows, RowModel parent)
    {
        boolean hasChildren = false;
        List<RowModel> children = getChildren(allRows, parent);
        if (children.size() > 0)
            hasChildren = true;
        return hasChildren;
    }

    public List<RowModel> getChildren(List<RowModel> allRows, RowModel parent)
    {
        List<RowModel> children = allRows.stream().filter((RowModel rowModel) ->
        {
            return rowModel.getParent() == parent.getIndex();
        }).collect(Collectors.toList());
        return children;
    }
}
