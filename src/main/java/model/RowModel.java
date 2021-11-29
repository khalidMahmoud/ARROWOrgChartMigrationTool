package model;

public class RowModel
{
    private int index;
    private String name;
    private int id;
    private int parent;
    private int active;
    private String type;
    private String category;
    private String unitCode;

    public RowModel()
    {
    }

    public RowModel(int index, String name, int id, int parent, int active, String type, String category, String unitCode)
    {
        this.index = index;
        this.name = name;
        this.id = id;
        this.parent = parent;
        this.active = active;
        this.type = type;
        this.category = category;
        this.unitCode = unitCode;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getParent()
    {
        return parent;
    }

    public void setParent(int parent)
    {
        this.parent = parent;
    }

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getUnitCode()
    {
        return unitCode;
    }

    public void setUnitCode(String unitCode)
    {
        this.unitCode = unitCode;
    }
}
