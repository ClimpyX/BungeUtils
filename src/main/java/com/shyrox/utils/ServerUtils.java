package com.shyrox.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerUtils {

    public static String getServer(ProxiedPlayer p) {
        return p.getServer().getInfo().getName();
    }

    public static String getColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void getAllPlayersSendMessage(String message) {
        for(ProxiedPlayer online : ProxyServer.getInstance().getPlayers()) {
            online.sendMessage(ServerUtils.getColor(message));
        }
    }


}