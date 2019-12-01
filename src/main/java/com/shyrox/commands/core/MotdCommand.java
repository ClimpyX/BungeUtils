package com.shyrox.commands.core;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MotdCommand extends Command {

    public MotdCommand() {
        super("motd", "shyrox.bungee.motd", "setmotd");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        

        if (args.length < 2) {
            sender.sendMessage("§cKullanım: /motd [default/maintenance] <Yazı>");
            sender.sendMessage("§cKullanım: {nl} yeni satır");
            return;
        }

        if (args[0].equalsIgnoreCase("default")) {
            StringBuilder message = new StringBuilder();

            for(int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }

            ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().set("motd", message.toString());
            ShyroxBungeeUtils.getPlugin().getMainConfig().configSave(ShyroxBungeeUtils.getPlugin());

            sender.sendMessage(ServerUtils.getColor("§aVarsayılan MOTD güncellenedi: " + message.toString()));
        } else if (args[0].equalsIgnoreCase("maintenance")) {
            StringBuilder message = new StringBuilder();

            for(int i = 1; i < args.length; i++) {
                message.append(args[i]).append(" ");
            }

            ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().set("maintenance-motd", message.toString());
            ShyroxBungeeUtils.getPlugin().getMainConfig().configSave(ShyroxBungeeUtils.getPlugin());

            sender.sendMessage(ServerUtils.getColor("§aBakım-Modu MOTD güncellenedi: " + message.toString()));
        }
    }
}