package com.shyrox.commands.bungee;

import com.shyrox.config.ConfigUtils;
import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class AlertCommand extends Command {


    public AlertCommand() {
        super("alert", "shyrox.bungee.alert", "galert");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Bir mesaj girmelisin.");
            return;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i<args.length; i++) {
                sb.append(args[i]).append(" ");
            }

            ConfigUtils configUtils = new ConfigUtils(ShyroxBungeeUtils.getPlugin().getMainConfig(), "alert");

            String message = sb.toString().trim();

            ServerUtils.getAllPlayersSendMessage(configUtils.getString("prefix", true)
                    + ServerUtils.getColor(message));
            //ProxyServer.getInstance().broadcast(configUtils.getString("prefix", true)
              //      + ServerUtils.getColor(message));

        }
    }
}
