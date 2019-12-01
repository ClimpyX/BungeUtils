package com.shyrox.commands.bungee;

import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.command.PlayerCommand;

public class FindCommand extends PlayerCommand {

    public FindCommand() {
        super("find", "shyrox.bungee.find", "bul", "oyuncubul");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage( new ComponentBuilder("Doğru Kullanım: /find <Oyuncu Adı>").color(ChatColor.RED).create());
        } else {
            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(args[0]);
            if (player == null || player.getServer() == null) {
                sender.sendMessage(new ComponentBuilder(ChatColor.RED + "Oyuncu çevrimiçi değil.").create());
            } else {
                sender.sendMessage(new ComponentBuilder("§f" + args[0]).color(ChatColor.GREEN).append(" şu anda ").append("§f" + ServerUtils.getServer(player) + "§a sunucusunda bulunuyor.").create());
            }
        }
    }
}