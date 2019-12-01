package com.shyrox.commands.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static net.md_5.bungee.api.ProxyServer.getInstance;


public class SendCommand
  extends Command
{
  public SendCommand() { super("send", "shyrox.bungee.send", new String[] { "gönder" }); }


  
  public void execute(CommandSender sender, String[] args) {
    ProxiedPlayer proxiedPlayer = getInstance().getPlayer(sender.getName());
    if (args.length != 2) {
      sender.sendMessage(ChatColor.RED + "Kullanım: /" + getName() + " <sunucu|oyuncuAdı|hepsi|şuAnki> <hedefSunucu>");
      
      return;
    } 
    ServerInfo targetServer = getInstance().getServerInfo(args[1]);
    if (targetServer == null) {
      sender.sendMessage("Girilen sunucu adı bulunamadı.");
      
      return;
    } 
    if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("hepsi")) {
      getInstance().getPlayers().forEach(onlinePlayer ->
          summonPlayer(onlinePlayer, targetServer, sender));
    }
    else if (args[0].equalsIgnoreCase("current") || args[0].equalsIgnoreCase("şuanki")) {
      if (!(sender instanceof ProxiedPlayer)) {
        sender.sendMessage(ChatColor.RED + "Oyuncular yapabilir");
        
        return;
      } 
      proxiedPlayer.getServer().getInfo().getPlayers().forEach(onlinePlayer -> 
          summonPlayer(onlinePlayer, targetServer, sender));
    } else {
      
      ServerInfo serverTarget = getInstance().getServerInfo(args[0]);
      if (serverTarget != null) {
        serverTarget.getPlayers().forEach(onlinePlayer -> 
            summonPlayer(onlinePlayer, targetServer, sender));
      } else {
        
        ProxiedPlayer targetPlayer = getInstance().getPlayer(args[0]);
        if (targetPlayer == null) {
          sender.sendMessage(ChatColor.RED + "Girilen oyuncu çevrimdışı.");
          
          return;
        } 
        summonPlayer(targetPlayer, targetServer, sender);
      } 
    } 
    
    sender.sendMessage(ChatColor.GREEN + "Oyuncu(lar) başarıyla gönderildi.");
  }
  
  private void summonPlayer(ProxiedPlayer player, ServerInfo target, CommandSender sender) {
    if (player.getServer() != null && !player.getServer().getInfo().equals(target)) {
      player.connect(target);
      
      player.sendMessage(ChatColor.GOLD + sender.getName() + " tarafından " + target.getName() + " sunucusuna gönderildiniz.");
    } 
  }
}
