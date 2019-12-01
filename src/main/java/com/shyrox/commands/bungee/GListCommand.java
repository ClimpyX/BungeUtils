package com.shyrox.commands.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class GListCommand extends Command {
	
	public GListCommand() {
		super("glist", "", "gplayers", "gp", "players", "sllist", "slglist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		

			sender.sendMessage("§aŞu anda sunucu aktif oyuncu sayısı: §f§l" + ProxyServer.getInstance().getOnlineCount());
			return;
	}
}