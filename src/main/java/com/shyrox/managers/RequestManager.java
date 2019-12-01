package com.shyrox.managers;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RequestManager {

    private final ShyroxBungeeUtils plugin;

    public RequestManager(ShyroxBungeeUtils plugin) {
        this.plugin = plugin;
    }

    public void requestMessage(ProxiedPlayer reporter, String reason) {
        ProxiedPlayer player = (ProxiedPlayer)reporter;
        for (ProxiedPlayer online : ProxyServer.getInstance().getPlayers()) {
            if (online.hasPermission("shyrox.bungee.request.see")) {

                String tusv = player.getServer().getInfo().getName();
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[İstek]" +
                        " &e[" + tusv + "]" + " &7(Oyuncu: &b" + reporter.getName() + "&7) \n&9İstek: &7" + reason));
            }
        }
    }
}
