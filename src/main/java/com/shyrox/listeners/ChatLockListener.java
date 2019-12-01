package com.shyrox.listeners;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatLockListener implements Listener {

	@EventHandler
	public void Chatlock(ChatEvent event) {
		ProxiedPlayer p = (ProxiedPlayer) event.getSender();
		if (isChatMuted()) {
			if (p.hasPermission("shyrox.bungee.chatlock.bypass")) {
				return;
			}
			if (!event.getMessage().startsWith("/")) {
				p.sendMessage(ServerUtils.getColor("&cSohbet kilitli, şu an konuşamazsınız."));
				event.setCancelled(true);
			}
		}
	}

	private boolean isChatMuted() { return ShyroxBungeeUtils.getPlugin().chatMuted; }
}