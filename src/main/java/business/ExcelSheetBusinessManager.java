package business;

import config.Configuration;
import model.RoleUserModel;
import model.RowModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExcelSheetBusinessManager
{
    //    Read orgchart excel sheet
    public List readOrgChartFromExcel()
    {
        List all = new ArrayList();
        Map<String, Object> orgChartMap = new HashMap();
        List<Map<String, String>> orgChartRow;
        List rowX = new ArrayList();
        try
        {
            File file = new File(new Configuration().getExcelSheetName().get("excelSheetName").toString());
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_STRING:
                            rowX.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            rowX.add((int) cell.getNumericCellValue());
                            break;
                        default:
                    }
                }

                orgChartMap.put("index", rowX.get(0));
                orgChartMap.put("name", rowX.get(1));
                orgChartMap.put("id", rowX.get(2));
                orgChartMap.put("parent", rowX.get(3));
                orgChartMap.put("active", rowX.get(4));
                orgChartMap.put("type", rowX.get(5));
                rowX = new ArrayList();
                all.add(orgChartMap);
                orgChartMap = new HashMap();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return all;
    }


    public List readOrgChartFromExcel_mode(String sheetName)
    {
        List all = new ArrayList();
        Map<String, Object> orgChartMap = new HashMap();
        List<Map<String, String>> orgChartRow;
        List rowX = new ArrayList();
        try
        {
            File file = new File(sheetName);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_STRING:
                            rowX.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            rowX.add((int) cell.getNumericCellValue());
                            break;
                        default:
                    }
                }
                all.add(new RowModel((Integer) rowX.get(0), (String) rowX.get(1), (Integer) rowX.get(2), (Integer) rowX.get(3), (Integer) rowX.get(4), (String) rowX.get(5),(String) rowX.get(6),String.valueOf(rowX.get(7))));
                rowX = new ArrayList();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return all;
    }

    public List readRoleUsers(String sheetName)
    {
        List all = new ArrayList();
        Map<String, Object> orgChartMap = new HashMap();
        List<Map<String, String>> orgChartRow;
        List rowX = new ArrayList();
        try
        {
            File file = new File(sheetName);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_STRING:
                            rowX.add(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            rowX.add((int) cell.getNumericCellValue());
                            break;
                        default:
                    }
                }
                all.add(new RoleUserModel((String) rowX.get(0),(String) rowX.get(1)));
                rowX = new ArrayList();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return all;
    }
}
