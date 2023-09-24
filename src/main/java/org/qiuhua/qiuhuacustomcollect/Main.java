package org.qiuhua.qiuhuacustomcollect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.qiuhua.qiuhuacustomcollect.collect.BlockRefresh;
import org.qiuhua.qiuhuacustomcollect.database.BlockDataStorageService;
import org.qiuhua.qiuhuacustomcollect.event.PlayerListener;

public class Main extends JavaPlugin {
    private static Main mainPlugin;
    public static Main getMainPlugin(){
        return mainPlugin;
    }


    //启动时运行
    @Override
    public void onEnable(){
        //设置主插件
        mainPlugin = this;
        Config.saveAllConfig();
        Config.reload();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        //注册指令
        new QiuhuaCustomCollectCommand().register();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, BlockRefresh::main, 20L, 20L);

        BlockDataStorageService.enablePluginLoad();

    }


    //关闭时运行
    @Override
    public void onDisable(){

        BlockDataStorageService.disablePluginStorage();
    }

    //执行重载命令时运行
    @Override
    public void reloadConfig(){
        Config.reload();
        Main.getMainPlugin().getLogger().info("配置文件重载完成 已经进入刷新的采集物不会受到影响");
    }
}
