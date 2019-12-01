package com.shyrox.commands.core.message;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ToggleMsg
  extends Command
{
  public ToggleMsg() { super("togglemsg", "", new String[] { "tmsg" }); }

  
  public static List<ProxiedPlayer> tmsg = new ArrayList();

  
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {
      if (sender.hasPermission("shyrox.command.togglemsg")) {
        ProxiedPlayer player = (ProxiedPlayer)sender;
        if (tmsg.contains(player)) {
          tmsg.remove(player);
          player.sendMessage(ChatColor.GREEN  + "Mesajlar açıldı.");
          return;
        } 
        tmsg.add(player);
        player.sendMessage(ChatColor.RED  + "Mesajlar kapatıldı.");
      } else {
        sender.sendMessage(ChatColor.RED  + "Yetki yok.");
      } 
    } else {
      sender.sendMessage("olmaz");
    } 
  }
}
