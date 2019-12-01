package com.shyrox.commands.core;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by Marko on 09.04.2018.
 */
public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", "shyrox.bungee.maintenance", "globalwl", "globalwhitelist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        

        if (args.length == 0) {
            sender.sendMessage((new ComponentBuilder("")).append(ChatColor.RED + "Kullanım: /maintenance set").create());

            return;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("set")) {
                boolean status = maintenanceToggle();
                if (status) {
                    sender.sendMessage((new ComponentBuilder("")).append(ChatColor.GREEN + "Bakım modu aktif edildi, oyuncular atılıyor..").create());

                    return;
                }
                sender.sendMessage((new ComponentBuilder("")).append(ChatColor.RED + "Bakım modu de-aktif edildi, oyuncu girişleri açıldı.").create());

                return;
            }
            sender.sendMessage((new ComponentBuilder("")).append(ChatColor.RED + "Kullanım: /maintenance set").create());
        }
    }

    private boolean maintenanceToggle() {
        if (ShyroxBungeeUtils.getPlugin().mainteanceMode != true) {
            ShyroxBungeeUtils.getPlugin().mainteanceMode = true;
            ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().set("settings.maintenance", Boolean.valueOf(true));
            ShyroxBungeeUtils.getPlugin().getMainConfig().configSave(ShyroxBungeeUtils.getPlugin());
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (!player.hasPermission("shyrox.bungee.maintenance.bypass")) {
                    player.disconnect(ServerUtils.getColor("&cHesabınız §6Shyrox §cüzerinden atıldı.\n§cSakin olabilirsiniz! Atılma sebebiniz sunucuda şu an başlatılan bakım sürecidir.\n§cÇok yakında sizinle olacağız, her hangi bir veri kaybı olmayakcatır.\n§eDaha fazlası için bize ulaşın!"));
                }
            }
            return true;
        }
        ShyroxBungeeUtils.getPlugin().mainteanceMode = false;
        ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().set("settings.maintenance", Boolean.valueOf(false));
        ShyroxBungeeUtils.getPlugin().getMainConfig().configSave(ShyroxBungeeUtils.getPlugin());
        return false;
    }

}