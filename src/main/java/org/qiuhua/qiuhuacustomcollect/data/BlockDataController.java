package org.qiuhua.qiuhuacustomcollect.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.qiuhua.qiuhuacustomcollect.Config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockDataController {

    //全部方块数据
    private static final Map<String, List<BlockData>> allBlockData = new ConcurrentHashMap<>();


    //添加一个破坏的方块进去
    public static void putBlockData(String blockId, Location location){
        if(!allBlockData.containsKey(blockId)){
            allBlockData.put(blockId, new CopyOnWriteArrayList<>());
//            Bukkit.getConsoleSender().sendMessage("增加一个自定义方块数据");
        }
        BlockData data = new BlockData();
        Map<String, Long> map = Config.getCustomListMap();
        Long refreshInterval = map.get(blockId); //获取刷新间隔
        Long currentTime = System.currentTimeMillis();  //获取时间
        data.setBlockData(location, refreshInterval + currentTime);
        allBlockData.get(blockId).add(data);
//        Bukkit.getConsoleSender().sendMessage("破坏了一个自定义方块");
//        Bukkit.getConsoleSender().sendMessage(allBlockData.toString());
    }




    public static Map<String, List<BlockData>> getAllBlockData(){
        return allBlockData;
    }

}
