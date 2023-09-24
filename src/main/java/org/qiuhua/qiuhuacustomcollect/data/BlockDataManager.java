package org.qiuhua.qiuhuacustomcollect.data;

import org.qiuhua.qiuhuacustomcollect.Config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockDataManager {

    //全部方块数据
    private static final Map<String, CopyOnWriteArrayList<BlockData>> allBlockData = new ConcurrentHashMap<>();

    private static final Map<String, Long> configContainer = Config.getCustomListMap();

    private BlockDataManager() {}

    /**
     * 指定 blockId 创建 BlockData 对象
     * 存入 allBlockData 容器中
     * @param blockId itemAdder 的自定义方块 id
     * @param data 方块信息
     */
    public static void putBlockData (String blockId, BlockData data)
    {
        if(!allBlockData.containsKey(blockId))
            allBlockData.put(blockId, new CopyOnWriteArrayList<>());

        //获取间隔时间
        Long refreshInterval = configContainer.get(blockId);
        //data被创建时获取的当前毫秒数 + 间隔时间
        data.offsetRefreshTime(refreshInterval);

        allBlockData.get (blockId).add(data);
    }


    public static Map<String, CopyOnWriteArrayList<BlockData>> getAllBlockData(){
        return allBlockData;
    }

}
