package com.shyrox.commands.core;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("configload", "shyrox.bungee.reload", "cr", "creload", "configr", "bungeload", "config", "config");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        
        ShyroxBungeeUtils.getPlugin().getMainConfig().reloadConfig();

        sender.sendMessage(ChatColor.GREEN + "Ayarlar başarıyla yenilendi.");
    }
}