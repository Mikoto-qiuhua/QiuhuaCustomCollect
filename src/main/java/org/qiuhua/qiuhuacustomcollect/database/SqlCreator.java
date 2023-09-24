package org.qiuhua.qiuhuacustomcollect.database;

import javax.sql.DataSource;
import java.sql.Connection;

public interface SqlCreator
{
    /**
     * 数据源
     */
    DataSource dataSource = null;

    /**
     * 获取数据库链接
     * @return 链接
     */
    Connection getConnection ();


}
