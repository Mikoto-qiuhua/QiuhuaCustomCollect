package org.qiuhua.qiuhuacustomcollect.collect;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.qiuhua.qiuhuacustomcollect.Main;
import org.qiuhua.qiuhuacustomcollect.data.BlockData;
import org.qiuhua.qiuhuacustomcollect.data.BlockDataManager;
import org.qiuhua.qiuhuacustomcollect.database.BlockDataStorageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockRefresh {

    //异步的主要任务
    public static void main(){
        Map<String, CopyOnWriteArrayList<BlockData>> allBlockData = BlockDataManager.getAllBlockData();
        Bukkit.getConsoleSender().sendMessage("----------开始尝试更新方块----------");
        long start = System.currentTimeMillis();
        for(String blockId : allBlockData.keySet()){
            List<BlockData> list = allBlockData.get(blockId);
            if(list.isEmpty()){
                continue;
            }
            int am = 0;
            Bukkit.getConsoleSender().sendMessage("方块id -> " + blockId);
            for(BlockData data : list){
                if(data.isRefreshBlock()){
                    CustomBlock customBlock = CustomBlock.getInstance(blockId);
                    am = am + 1;
                    if(customBlock != null){
                        Bukkit.getScheduler().runTask(Main.getMainPlugin(), new Runnable() {
                            public void run() {
                                customBlock.place(data.getLocation());
                            }
                        });
                    }
                    list.remove(data);
                }
            }
            Bukkit.getConsoleSender().sendMessage("生成次数 -> " + am);
        }
        long end = System.currentTimeMillis();
        Bukkit.getConsoleSender().sendMessage("----------更新结束 耗时 -> " + (end - start) + " ms ----------");
        BlockDataStorageService.allBlockDataStorage();
    }







}
