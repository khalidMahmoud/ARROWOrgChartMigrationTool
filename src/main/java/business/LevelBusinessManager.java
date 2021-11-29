package business;

import config.JdbcConfig;
import dao.LevelDao;
import model.RowModel;

import java.util.List;

public class LevelBusinessManager
{
    LevelDao levelDao = new LevelDao();

    public List<RowModel> addLevels(List<RowModel> topLevel, JdbcConfig jdbcConfig,  int parent)
    {
        return levelDao.insertLevel(topLevel, jdbcConfig, parent);
    }

}
