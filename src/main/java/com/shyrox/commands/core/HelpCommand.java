package com.shyrox.commands.core;

import com.shyrox.ShyroxBungeeUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class HelpCommand extends Command {

  public HelpCommand() {
    super("help", "", "yardim");
  }


  public void execute(CommandSender sender, String[] args) {
    

      sender.sendMessage(ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getString("help-message").replace('&', '§'));
  }
}
