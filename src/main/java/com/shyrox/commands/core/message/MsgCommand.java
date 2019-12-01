package com.shyrox.commands.core.message;

import com.shyrox.ShyroxBungeeUtils;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class MsgCommand
  extends Command
  implements TabExecutor {
  public static List<String> comandos = new ArrayList();

  
  public MsgCommand() { super("msg", "", new String[] { "tell", "say", "me", "mesaj", "gönder", "m"}); }


  
  public void execute(CommandSender sender, String[] args) {

    if (sender instanceof ProxiedPlayer) {
        ProxiedPlayer player = (ProxiedPlayer)sender;
        if (args.length >= 2) {
          ProxiedPlayer player2 = ShyroxBungeeUtils.getPlugin().getProxy().getPlayer(args[0]);
          if (player2 == null) {
            player.sendMessage(ChatColor.RED + "Oyuncu çevrim içi değil.");
            return;
          } 
          if (player.getName() == player2.getName()) {
            player.sendMessage(ChatColor.RED + "Kendime mesaj gönderemezsin.");
            return;
          } 
          if (ToggleMsg.tmsg.contains(player2)) {
            player.sendMessage(ChatColor.GREEN + "Oyuncu özelden mesajları kapattı, daha sonra deneyin.");
            return;
          } 
          String mensaje = "";
          for (int i = 1; i < args.length; i++) {
            mensaje = String.valueOf(mensaje) + args[i] + " ";
          }
          String tusv = player.getServer().getInfo().getName();
          String susv = player2.getServer().getInfo().getName();


          player.sendMessage("§eSen §f" + player2.getName() + "§eoyuncusuna mesaj gönderdin§8: §f" + mensaje);
          player2.sendMessage("§7("+ tusv + ") " + ChatColor.YELLOW + player.getName() + " sana mesaj gönderdi§8: §f" + mensaje);

          for (ProxiedPlayer staffs : ShyroxBungeeUtils.getPlugin().getProxy().getPlayers()) {
            if (SocialSpy.sp.contains(staffs)) {

              staffs.sendMessage("§6[MSJ-KONTROL] §f" + player.getName() +  "§6: §f" + player2.getName() + "§7: " + mensaje);
            } 
          }
        } else {
          player.sendMessage(ChatColor.RED + "Doğru kullanım: /mesaj <oyuncu> [mesaj]");
        }
    } else {
      sender.sendMessage("Maalesef!");
    } 
  }
  
  public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length != 1) {
      return ImmutableSet.of();
    }
    Set<String> matches = new HashSet<String>();
    if (args.length == 1 &&
            ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getBoolean("msj-tab-doldur")) {
      String search = args[0].toLowerCase();
      for (ProxiedPlayer player : ShyroxBungeeUtils.getPlugin().getProxy().getPlayers()) {
        if (player.getName().toLowerCase().startsWith(search)) {
          matches.add(player.getName());
        }
      } 
    } 
    
    return matches;
  }
}
