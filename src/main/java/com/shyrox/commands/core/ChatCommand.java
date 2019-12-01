package com.shyrox.commands.core;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ChatCommand extends Command {
	
	public ChatCommand(){
		super("chat", "shyrox.bungee.chat", "sohbet");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(args.length == 1 && args[0].equals("lock")){
			if (ShyroxBungeeUtils.getPlugin().chatMuted){
				ShyroxBungeeUtils.getPlugin().chatMuted = false;
				sender.sendMessage(ServerUtils.getColor("&aSohbet açıldı, oyunculara bilgi mesajı gönderiliyor.."));
				if(ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getBoolean("chatlock-broadcast.enabled")){
					ServerUtils.getAllPlayersSendMessage("&aSohbet açıldı, artık konuşabilirsiniz.");
				}
			} else {
				ShyroxBungeeUtils.getPlugin().chatMuted = true;
				sender.sendMessage(ServerUtils.getColor("&aSohbet kapatıldı, oyunculara bilgi mesajı gönderiliyor.."));
				if(ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getBoolean("chatlock-broadcast.enabled")){
					ServerUtils.getAllPlayersSendMessage("&cSohbet kapatıldı, artık konuşamazsınız.");
				}
			}
			return;
		}
		sender.sendMessage(ServerUtils.getColor("&cKullanım: /chat lock"));
		return;
	}

}