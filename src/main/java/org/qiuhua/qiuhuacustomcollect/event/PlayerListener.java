package org.qiuhua.qiuhuacustomcollect.event;


import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.qiuhua.qiuhuacustomcollect.collect.BlockCollect;

public class PlayerListener implements Listener {


    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent event){
        BlockCollect.main(event);
    }


}
