package com.shyrox.commands.core;

import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PluginsCommand extends Command {


    public PluginsCommand() {
        super("plugins", "", "pl", "eklentiler", "eklenti");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        sender.sendMessage(ServerUtils.getColor("&fPlugins (4): &aShyroxLib&f, &aShyroxBungeeUtils&f, &aShyroxSkyBlock&f, &aShyroxHub"));
    }
}
