package com.shyrox.tasks;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.config.ConfigUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static net.md_5.bungee.api.ProxyServer.getInstance;


public class AnnouncementTask
  implements Runnable
{
  private List<String> autoMessages;

  public AnnouncementTask() { this.autoMessages = new ArrayList(); }



  public void run() {
    for (ProxiedPlayer proxiedPlayer : getInstance().getPlayers()) {
      proxiedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', getNextAutoMessage()));
    }
  }
  
  private String getNextAutoMessage() {
    ConfigUtils configMessages = new ConfigUtils(ShyroxBungeeUtils.getPlugin().getMainConfig(), "duyurular");
    
    if (this.autoMessages.isEmpty()) {
      this.autoMessages.addAll(configMessages.getStringList("mesajlar-listesi"));
    }

    String nextMessage = (String)this.autoMessages.get(0);
    Collections.rotate(this.autoMessages, -1);
    return nextMessage;
  }
}
