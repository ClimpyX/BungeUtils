package com.shyrox.commands.bungee;

import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class ServerCommand extends Command {

      public ServerCommand() {
            super("server", "shyrox.bungee.serverlist", "sunucu", "join", "katil", "katıl");
      }

      @Override
      public void execute(CommandSender sender, String[] args) {
            

            if (sender instanceof ProxiedPlayer) {
                  Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

                  if (((ProxiedPlayer) sender).getServer().getInfo().getName().equals("CakmaLobi")) {
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Bulunduğun sunucuda bu komut kullanılmaz." ));
                        return;
                  }

                  StringBuilder serverList = new StringBuilder();
                  for (ServerInfo server : servers.values()) {
                        serverList.append(server.getName());
                        serverList.append(", ");
                  }
                  if (serverList.length() != 0) {
                        serverList.setLength(serverList.length() - 2);
                  }

                  if (args.length != 1) {
                        sender.sendMessage(ServerUtils.getColor("§aMevcut sunucular: §f" + serverList.toString() + " &7(Mevcut: " + ProxyServer.getInstance().getPlayer(sender.getName()).getServer().getInfo().getName() + ")"));
                        sender.sendMessage(ServerUtils.getColor("§aSunucu değiştirmek için §f/sunucu [sunucu ismi]"));
                        return;
                  }



                //  if(args[1].contains("GirisLobisi")) {
               //         sender.sendMessage(new TextComponent("§cGüvenlik nedeniyle bu imkansız."));
              //          return;
               //  }

                  if (args.length == 1) {
                        String serverName = args[0];
                        if (ProxyServer.getInstance().getServers().get(serverName) == null) {
                              sender.sendMessage(ChatColor.RED + "Girilen sunucu adı '" + ChatColor.WHITE + args[0] + ChatColor.RED + "' bulunamadı.");
                              return;
                        }

                        ((ProxiedPlayer) sender).connect(ProxyServer.getInstance().getServers().get(serverName));
                        return;
                  }
            } else {
                  sender.sendMessage(ChatColor.RED + "Konsollar aglasın..");
                  return;
            }
      }
}
