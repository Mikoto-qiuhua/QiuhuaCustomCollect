package org.qiuhua.qiuhuacustomcollect.data;


import org.bukkit.Bukkit;
import org.bukkit.Location;


public class BlockData {
    private Location location = null; //位置
    private Long refreshTime = null; //刷新时间

    //设置基础信息
    public void setBlockData(Location location, Long refreshTime){
        this.refreshTime = refreshTime;
        this.location = location;
    }


    //判断是否刷新方块
    public boolean isRefreshBlock(){
        long currentTime = System.currentTimeMillis();  //获取时间
        if(currentTime >= refreshTime){
//            Bukkit.getConsoleSender().sendMessage("方块刷新");
            return true;
        }
        return false;
    }

    //获取坐标

    public Location getLocation(){
        return this.location;
    }




}
