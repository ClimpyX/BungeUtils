package com.shyrox.commands.core;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.net.InetSocketAddress;

public class AddServerCommand extends Command {
  
  public AddServerCommand() {
    super("addserver", "shyrox.bungee.addserver", "serveradd", "serverekle");
  }

  public void execute(CommandSender sender, String[] args) {

    if (args.length != 3) {
      sender.sendMessage((new ComponentBuilder("")).append(ChatColor.RED + "Kullanım: " + ChatColor.GOLD + "/addserver [isim] [adres] [port]").create());
      
      return;
    } 
      String name = args[0];
       String address = args[1];
      int port = Integer.valueOf(args[2]).intValue();
    
      ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, new InetSocketAddress(address, port), "", false);
      ProxyServer.getInstance().getServers().put(name, serverInfo);
    
     sender.sendMessage((new ComponentBuilder("")).append(ChatColor.GREEN + "Eklenen sunucu: " + ChatColor.GOLD + name).create());
  }
}
