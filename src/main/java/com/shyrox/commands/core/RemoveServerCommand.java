package com.shyrox.commands.core;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class RemoveServerCommand extends Command {

  public RemoveServerCommand() {
    super("removeserver", "shyrox.bungee.removeserver", "silserver");
  }

  
  public void execute(CommandSender sender, String[] args) {

    if (args.length != 1) {
      sender.sendMessage(ChatColor.RED + "Kullanım: " + ChatColor.GOLD + "/removeserver [isim]");
      
      return;
    } 
    String name = args[0];
    if (ProxyServer.getInstance().getServers().remove(name) == null) {
      sender.sendMessage(ChatColor.RED + "Girilen adda biri sunucu bulunamadı: " + ChatColor.GOLD + name);
    } else {
      sender.sendMessage(ChatColor.GREEN + "Silinen sunucu: " + ChatColor.GOLD + name);
    } 
  }
}
