package org.qiuhua.qiuhuacustomcollect.collect;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.qiuhua.qiuhuacustomcollect.Main;
import org.qiuhua.qiuhuacustomcollect.data.BlockData;
import org.qiuhua.qiuhuacustomcollect.data.BlockDataManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockRefresh {

    //异步的主要任务
    public static void main(){
        Map<String, CopyOnWriteArrayList<BlockData>> allBlockData = BlockDataManager.getAllBlockData();
        for(String blockId : allBlockData.keySet()){
            List<BlockData> list = allBlockData.get(blockId);
            if(list.isEmpty()){
                continue;
            }
            for(BlockData data : list){
                if(data.isRefreshBlock()){
//                    Bukkit.getConsoleSender().sendMessage("方块 " + blockId);
                    CustomBlock customBlock = CustomBlock.getInstance(blockId);
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
        }
    }







}
