package org.qiuhua.qiuhuacustomcollect.collect;

import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.qiuhua.qiuhuacustomcollect.Config;
import org.qiuhua.qiuhuacustomcollect.data.BlockData;
import org.qiuhua.qiuhuacustomcollect.data.BlockDataManager;

import java.util.Map;

public class BlockCollect {

    private static Boolean destroy = false;


    //
    public static void main(CustomBlockBreakEvent event){
        String blockId = event.getNamespacedID();
        if(!isCustomBlock(blockId)){
            return;
        }
        if(destroy){
            event.getPlayer().sendMessage("[自定义采集] 破坏的物品不会进入刷新列表");
            return;
        }
        Location loc = event.getBlock().getLocation();
        BlockDataManager.putBlockData(blockId, new BlockData(loc, System.currentTimeMillis()));
    }

    //判断是否是自定义方块
    public static boolean isCustomBlock(String blockId){
        if(blockId != null) {
            Map<String, Long> map = Config.getCustomListMap();
            if(map.containsKey(blockId)){
                return true;
            }
        }
        return false;

    }

    public static void setDestroy(Player player){
        if(destroy){
            destroy = false;
            player.sendMessage("[自定义采集] 已关闭破坏模式");
        }else {
            destroy = true;
            player.sendMessage("[自定义采集] 已开启破坏模式 破坏的自定义方块不会进入刷新列表");
        }

    }


}
