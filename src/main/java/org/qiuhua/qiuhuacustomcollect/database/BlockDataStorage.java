package org.qiuhua.qiuhuacustomcollect.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.qiuhua.qiuhuacustomcollect.Config;
import org.qiuhua.qiuhuacustomcollect.data.BlockData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BlockDataStorage
{
    private final static SqlCreator creator = DefaultCreator.getCreator(Config.getEnableSql());
    public static void createBlockDataTable ()
    {
        try (PreparedStatement statement = creator.getConnection().prepareStatement("create table if not exists `custom_block_data` (`blockId` varchar(32), `refreshTime` bigint(32), `world` varchar(10), `x` double(20,10), `y` double(20,10), `z` double(20,10) )charset=utf8;")) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("创建 block data 表失败....");
        }
    }

    public static int insertBlockData (String blockId, BlockData data)
    {
        int result;
        try (PreparedStatement statement = creator.getConnection().prepareStatement("insert into `custom_block_data` values(?, ?, ?, ?, ?, ?);")) {
            statement.setObject(1, blockId);
            statement.setObject(2, data.getRefreshTime());
            statement.setObject(3, Objects.requireNonNull(data.getLocation().getWorld()).getName());
            statement.setObject(4, data.getLocation().getX());
            statement.setObject(5, data.getLocation().getY());
            statement.setObject(6, data.getLocation().getZ());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入 [blockId = " + blockId + "] 的操作失败了 数据是 [refreshTime = " + data.getRefreshTime() + "location = xyz (" + data.getLocation().getBlockX() + " , " + data.getLocation().getBlockY() + " , " + data.getLocation().getBlockZ() + ")]....");
        }
        return result;
    }



    public static Map<String, List<BlockData>> getBlockData ()
    {
        Map<String, List<BlockData>> result = new HashMap<>();


        try (PreparedStatement statement = creator.getConnection().prepareStatement("select * from `custom_block_data`")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                BlockData data = new BlockData();
                String id = resultSet.getString(1);

                if (!result.containsKey(id))
                {
                    result.put (id, new ArrayList<>());
                }

                long refreshTime = resultSet.getLong(2);
                Location location = new Location (
                        Bukkit.getWorld (resultSet.getString (3)),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getDouble(6));

                data.setRefreshTime(refreshTime);
                data.setLocation(location);

                result.get(id).add(data);
            }

        } catch (SQLException e) {
            throw new RuntimeException("查询操作失败了....");
        }
        return result;
    }

    public static void deleteTable ()
    {
        try (PreparedStatement statement = creator.getConnection().prepareStatement("drop table if exists `custom_block_data`;")) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("创建 block data 表失败....");
        }
    }
}
