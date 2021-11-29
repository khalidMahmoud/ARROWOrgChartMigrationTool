package utils;

public enum  UnitType
{
    PoliticalAdministrations("ديوان عام - ادارات سياسيه"),
    AdministrativeDepartments("ديوان عام - ادارات اداريه"),
    EgyptianMissionsAbroad("البعثات المصريه بالخارج"),
    ExternalParties("جهات خارجيه"),
    VariousThirdParties("جهات اخري متنوعه");

    private String unitType;

    UnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public String getValue()
    {
        return unitType;
    }
}
