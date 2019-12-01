package com.shyrox.listeners;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class EliceListener implements Listener {

    private ShyroxBungeeUtils plugin;

    public EliceListener(ShyroxBungeeUtils instance) { this.plugin = instance; }


    @EventHandler(priority = 32)
    public void onPing(ProxyPingEvent e) {
        PendingConnection connection = e.getConnection();
        if (connection == null || connection.getVirtualHost() == null || connection.getVirtualHost().getHostName() == null) {
            return;
        }

        ServerPing response = e.getResponse();

        if (ShyroxBungeeUtils.getPlugin().mainteanceMode) {
            response.getPlayers().setMax(0);
            response.getPlayers().setOnline(0);
            response.getPlayers().setSample(this.plugin.ServerPingMaintenance);
            response.setVersion(new ServerPing.Protocol(ChatColor.DARK_RED + "Bakım-Modu", 99));
        } else {
            response.getPlayers().setSample(this.plugin.ServerPingDefault);
            response.setVersion(new ServerPing.Protocol("§dÇevrimiçi: §f" + ProxyServer.getInstance().getOnlineCount(), 99));
        }

        e.setResponse(response);

        int online = response.getPlayers().getOnline();
        this.plugin.currentMaxPlayers = response.getPlayers().getMax();
        this.plugin.currentOnlinePlayers = online;
        if (online > this.plugin.peakPlayers) {
            this.plugin.setPeakPlayers(online);
        }
    }

    @EventHandler()
    public void onLoginEvent(PostLoginEvent event) {
      //  PendingConnection pendingConnection = event.getConnection();
        ProxiedPlayer p = event.getPlayer();
        if (ShyroxBungeeUtils.getPlugin().mainteanceMode && !p.hasPermission("shyrox.bungee.maintenance.bypass")) {
            p.disconnect(ChatColor.translateAlternateColorCodes('&', "&cHesabınız §6Shyrox §cüzerinden atıldı.\n§cSakin olabilirsiniz! Atılma sebebiniz sunucuda şu an başlatılan bakım sürecidir.\n§cÇok yakında sizinle olacağız, her hangi bir veri kaybı olmayakcatır.\n§eDaha fazlası için bize ulaşın!"));
            return;
        }
    }

    @EventHandler()
    public void onKickServer(ServerKickEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player == null) {
            return;
        }

        if (ShyroxBungeeUtils.getPlugin().mainteanceMode && !player.hasPermission("shyrox.bungee.maintenance.bypass")) {
            player.disconnect(ChatColor.translateAlternateColorCodes('&', "&cHesabınız §6EliceMC §cüzerinden atıldı.\n§cSakin olabilirsiniz! Atılma sebebiniz sunucuda şu an başlatılan bakım sürecidir.\n§cÇok yakında sizinle olacağız, her hangi bir veri kaybı olmayakcatır.\n§eDaha fazlası için bize ulaşın!"));

            return;
        }
        if (player.getServer() != null && event.getCancelServer() != null && player.getServer() != event.getCancelServer()) {
            event.setCancelled(true);
            player.sendMessage((new ComponentBuilder("")).append(ChatColor.RED + "Sunucudan atıldınız: " + ChatColor.GREEN + event.getKickReason()).create());
            return;
        }
    }


}
