package org.qiuhua.qiuhuacustomcollect.data;


import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;


public class BlockData {
    private Location location; //位置
    private Long refreshTime; //刷新时间


    public BlockData () {}

    /**
     * 有参构造
     * @param location 破坏的方块的 location
     * @param refreshTime 何时刷新的时间
     */
    public BlockData (Location location, Long refreshTime)
    {
        this.location = location;
        this.refreshTime = refreshTime;
    }

    public Location getLocation(){
        return this.location;
    }

    public Long getRefreshTime ()
    {
        return this.refreshTime;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public void setRefreshTime (long refreshTime)
    {
        this.refreshTime = refreshTime;
    }

    /**
     * 偏移刷新时间
     * @param time 初始化的时间加上这个时间 结果就是方块刷新时间
     */
    public void offsetRefreshTime (Long time)
    {
        this.refreshTime += time;
    }

    //判断是否刷新方块
    public boolean isRefreshBlock(){
        long currentTime = System.currentTimeMillis();  //获取时间
        //            Bukkit.getConsoleSender().sendMessage("方块刷新");
        return currentTime >= refreshTime;
    }

    /**
     * 序列化
     * @return 序列化后的 map
     */
    public Map<String, Object> serialize ()
    {
        HashMap<String, Object> result = new HashMap<>();

        result.put("refreshTime", this.refreshTime);
        result.put ("location", this.location);

        return result;
    }

    /**
     * 反序列化
     * @param target 反序列化的 map
     * @return 反序列化后的 BlockData 对象
     */
    public static BlockData deserialize (Map<String, Object> target)
    {
       return new BlockData ( (Location)target.get("location"),  (Long)target.get("refreshTime"));
    }



}
