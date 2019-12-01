package com.shyrox.commands.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SocialSpy
  extends Command
{
  public SocialSpy() { super("socialspy", "", new String[] { "sp" }); }

  
  public static HashMap<ProxiedPlayer, ProxiedPlayer> replyhash = new HashMap();
  public static List<ProxiedPlayer> sp = new ArrayList();

  
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {
      ProxiedPlayer player = (ProxiedPlayer)sender;
      if (player.hasPermission("shyrox.command.socialspy")) {
        if (sp.contains(player)) {
          sp.remove(player);
          player.sendMessage(ChatColor.RED  + "Gizlice mesajlara bakma modu kapatıldı.");
          return;
        } 
        sp.add(player);
        player.sendMessage(ChatColor.GREEN  + "Gizlice mesajlara bakma modu açıldı.");
      } else {
        player.sendMessage(ChatColor.RED  + "Yetki yok.");
      } 
    } 
  }
}
