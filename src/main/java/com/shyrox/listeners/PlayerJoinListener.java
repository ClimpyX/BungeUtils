package com.shyrox.listeners;

import com.shyrox.config.ConfigUtils;
import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {
        ConfigUtils configUtils = new ConfigUtils(ShyroxBungeeUtils.getPlugin().getMainConfig(), "staff");
    //    ProxiedPlayer player = event.getPlayer();

        for (ProxiedPlayer allplayer : ProxyServer.getInstance().getPlayers()) {
        //    if(player.hasPermission("elice.bungee.joinmessage")) {
              //  allplayer.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&9[Staff] &7[" + ServerUtil.getServer(player) + "]" + " &b" + player + " isimli yetkili oyuna giriş yaptı.")));
       //         allplayer.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&9[Staff] &7[Server]" + " &b" + player + " isimli yetkili oyuna giriş yaptı.")));
            }
        }
    }
//}
