package com.shyrox.commands.core;


import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static net.md_5.bungee.api.ProxyServer.getInstance;

public class HubCommand extends Command {

    //  private final EliceBungee plugin;

    public HubCommand() {
        super("hub", null, "lobi", "lobby");
        // this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {


        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

            if (proxiedPlayer.getServer().getInfo().getName().equals("CakmaLobi"))
                proxiedPlayer.sendMessage(new TextComponent(ChatColor.RED + "Bulunduğun sunucuda bu komut kullanılmaz."));
            else {
                if (proxiedPlayer.getServer().getInfo().getName().equals("Lobi"))
                    proxiedPlayer.sendMessage(new TextComponent(ChatColor.RED + ServerUtils.getServer(proxiedPlayer) + " sunucusuna şu anda zaten bağlısınız."));
                else {
                    proxiedPlayer.connect(getInstance().getServerInfo("Lobi"));
                    proxiedPlayer.sendMessage(new TextComponent(ChatColor.GREEN + "Lobi sunucusu ile bağlantı sağlanıyor.."));
                }
            }
        }
    }
}

