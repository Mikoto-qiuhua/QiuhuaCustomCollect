package org.qiuhua.qiuhuacustomcollect.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.qiuhua.qiuhuacustomcollect.Config;
import org.qiuhua.qiuhuacustomcollect.data.BlockData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockDataStorage
{
    private final static SqlCreator creator = DefaultCreator.getCreator(Config.getEnableSql());
    public static void createBlockDataTable ()
    {
        try (Connection connection = creator.getConnection(); PreparedStatement statement = connection.prepareStatement("create table if not exists `custom_block_data` (`blockId` varchar(32), refreshTime BigInt(20), world varchar(16), x int, y int, z int);")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new RuntimeException("创建 block data 表失败....");
        }
    }

    public static int insertBlockData (String blockId, BlockData data)
    {
        int result;
        try (Connection connection = creator.getConnection(); PreparedStatement statement = connection.prepareStatement("insert into `custom_block_data` values(?, ?, ?, ?, ?, ?);")) {
            statement.setObject(1, blockId);
            statement.setObject(2, data.getRefreshTime());
            statement.setObject(3, Objects.requireNonNull(data.getLocation().getWorld()).getName());
            statement.setObject(4, ((Double)data.getLocation().getX()).intValue());
            statement.setObject(5, ((Double)data.getLocation().getY()).intValue());
            statement.setObject(6, ((Double)data.getLocation().getZ()).intValue());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入 [blockId = " + blockId + "] 的操作失败了 数据是 [refreshTime = " + data.getRefreshTime() + "location = xyz (" + data.getLocation().getBlockX() + " , " + data.getLocation().getBlockY() + " , " + data.getLocation().getBlockZ() + ")]....");
        }
        return result;
    }



    public static Map<String, CopyOnWriteArrayList<BlockData>> getBlockData ()
    {
        Map<String, CopyOnWriteArrayList<BlockData>> result = new HashMap<>();


        try (Connection connection = creator.getConnection(); PreparedStatement statement = connection.prepareStatement("select * from `custom_block_data`")) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                BlockData data = new BlockData();
                String id = resultSet.getString(1);

                if (!result.containsKey(id))
                {
                    result.put (id, new CopyOnWriteArrayList());
                }

                long refreshTime = resultSet.getLong(2);
                Location location = new Location (
                        Bukkit.getWorld (resultSet.getString (3)),
                        ((Integer)resultSet.getInt(4)).doubleValue(),
                        ((Integer)resultSet.getInt(5)).doubleValue(),
                        ((Integer)resultSet.getInt(6)).doubleValue());

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
        try (Connection connection = creator.getConnection(); PreparedStatement statement = connection.prepareStatement("drop table if exists `custom_block_data`;")) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("删除 block data 表失败....");
        }
    }
}
