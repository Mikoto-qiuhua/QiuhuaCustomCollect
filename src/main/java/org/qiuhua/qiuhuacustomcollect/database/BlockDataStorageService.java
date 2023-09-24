package org.qiuhua.qiuhuacustomcollect.database;

import org.qiuhua.qiuhuacustomcollect.data.BlockData;
import org.qiuhua.qiuhuacustomcollect.data.BlockDataManager;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockDataStorageService
{
    /**
     * 插件开启时运行
     */
    public static void  enablePluginLoad ()
    {
        //创建表 如果有的话不创建 应对第一次使用插件的情况
        BlockDataStorage.createBlockDataTable();
        //从数据库中获取所有的方块信息
        Map<String, CopyOnWriteArrayList<BlockData>> databaseCache = BlockDataStorage.getBlockData();
        //如果它是 empty 说明没有需要刷新的自定义方块
        if (!databaseCache.isEmpty())
            BlockDataManager.getAllBlockData().putAll(databaseCache);

        //删除表 重新建一个什么都没的表
        BlockDataStorage.deleteTable();
        BlockDataStorage.createBlockDataTable();
    }


    /**
     * 插件关闭时运行
     */
    public static void disablePluginStorage ()
    {

        BlockDataManager.getAllBlockData().forEach((blockId, data) ->
                data.forEach(blockData -> {
                    BlockDataStorage.insertBlockData(blockId, blockData);
                }));
    }
}