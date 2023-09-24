package org.qiuhua.qiuhuacustomcollect.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.qiuhua.qiuhuacustomcollect.Config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DefaultCreator implements SqlCreator
{
    private  DataSource dataSource;

    private static SqlCreator creator;
    
    private DefaultCreator () {}
    /**
     * 初始化
     */
    public void init (String type)
    {
        HikariConfig config = new HikariConfig();
        Map<String, String> sqlSettings;
        if ("mysql".equalsIgnoreCase(type))
        {
            sqlSettings = Config.getMysqlSettingsToMap();
            config.setJdbcUrl(sqlSettings.get("jdbcUrl"));
            config.setUsername(sqlSettings.get("username"));
            config.setPassword(sqlSettings.get("password"));
        }else if ("sqlite".equalsIgnoreCase(type))
        {
            sqlSettings = Config.getSqliteSettings();
            config.setJdbcUrl(sqlSettings.get("jdbcUrl"));
        }else
            throw new RuntimeException("配置 [enable = " + type + "] 必须 [mysql , sqlite] 其中之一....");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource= new HikariDataSource(config);
    }


  
    public Connection getConnection ()
    {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库链接失败.....");
        }
        return connection;
    }


    /**
     * 单例返回 DefaultCreator 对象
     * @return 初始化的 DefaultCreator 对象
     */
    public static SqlCreator getCreator (String type)
    {
        if (creator == null)
            creator = new DefaultCreator();

        ((DefaultCreator)creator).init(type);

        return creator;
    }
}
