package org.qiuhua.qiuhuacustomcollect;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.qiuhua.qiuhuacustomcollect.collect.BlockCollect;

import java.util.ArrayList;
import java.util.List;

public class QiuhuaCustomCollectCommand implements CommandExecutor, TabExecutor {
    public void register() {
        Bukkit.getPluginCommand("QiuhuaCustomCollect").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                if(player.hasPermission("qiuhuacustomcollect.reload")){
                    Config.reload();
                }
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("destroy")){
                if(player.hasPermission("qiuhuacustomcollect.destroy")){
                    BlockCollect.setDestroy(player);
                }
                return true;
            }
        }else if(sender instanceof ConsoleCommandSender){
            if(args[0].equals("reload")) {
                Config.reload();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            List<String> result = new ArrayList<>();
            //当参数长度是1时
            if(args.length == 1) {
                if (player.hasPermission("qiuhuacustomcollect.reload"))
                    result.add("reload");
                if (player.hasPermission("qiuhuacustomcollect.destroy"))
                    result.add("destroy");
                return  result;
            }
        }
        return null;
    }


}
