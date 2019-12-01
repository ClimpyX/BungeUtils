package com.shyrox.handlers;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MotdHandler implements Listener {

    public MotdHandler(ShyroxBungeeUtils plugin) {
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, this);
    }

    String maintenanceMotd;
    String motd;


    @EventHandler(priority = 64)
    public boolean onServerListPing(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();

        if (ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getString("motd") == null) return false;

        if (ShyroxBungeeUtils.getPlugin().mainteanceMode) {
            maintenanceMotd = (ShyroxBungeeUtils.getPlugin().mainConfig.getConfiguration().getString("maintenance-motd").replace('&', '§'));
            maintenanceMotd = maintenanceMotd.replace("{nl}", "\n");

            ping.setDescription(maintenanceMotd);
            event.setResponse(ping);
            return true;

        } else {
            motd = (ShyroxBungeeUtils.getPlugin().mainConfig.getConfiguration().getString("motd").replace('&', '§'));
            motd = motd.replace("{nl}", "\n");

            ping.setDescription(motd);
            event.setResponse(ping);
            return false;
        }
    }
}
