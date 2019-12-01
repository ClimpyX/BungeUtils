package com.shyrox.managers;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ReportManager {

    private final ShyroxBungeeUtils plugin;

    public ReportManager(ShyroxBungeeUtils plugin) {
        this.plugin = plugin;
    }

    public void reportMessage(ProxiedPlayer reporter, ProxiedPlayer reported, String reason) {
        ProxiedPlayer player = (ProxiedPlayer)reporter;
        for (ProxiedPlayer online : ProxyServer.getInstance().getPlayers()) {
            if (online.hasPermission("shyrox.bungee.report.see")) {

                String tusv = player.getServer().getInfo().getName();
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[Şikayet] &e[" + tusv + "] " +
                        "&7(Şikayet edilen: &b" + reported.getName() + "&7)" +
                        " &7(Şikayetçi: &b" + reporter.getName() + "&7) \n&9Sebep: &7" + reason));
            }
        }
    }
}
