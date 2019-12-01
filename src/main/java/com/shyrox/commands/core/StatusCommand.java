package com.shyrox.commands.core;

import com.shyrox.utils.ServerUtils;
import com.shyrox.utils.TPSUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusCommand extends Command {
	
	Integer L = 1024;
	
	public StatusCommand() {
		super("status", "shyrox.bungee.status", "serverstatus");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Long uptime = ManagementFactory.getRuntimeMXBean().getStartTime();
		SimpleDateFormat df2 = new SimpleDateFormat("kk:mm dd-MM-yyyy");
		String date = df2.format(new Date(uptime));

		Double TPS = TPSUtils.getTPS();
		ChatColor color;
		String msg;
		if (TPS >= 18.0) {
			msg= "(İyi) ";
			color = ChatColor.GREEN;
		} else if(TPS >= 14.0) {
			msg= "(Orta) ";
			color = ChatColor.YELLOW;
		} else if(TPS >= 8.0){
			msg = "(Kötü) ";
			color = ChatColor.RED;
		} else {
			msg = "(Çöp)";
			color = ChatColor.DARK_RED;
		}
		sender.sendMessage(ServerUtils.getColor("&d&lShyrox &dsunucu istatistikleri;"));
		sender.sendMessage(ServerUtils.getColor("&6TPS: &7" + color + msg + TPS));
		sender.sendMessage(ServerUtils.getColor("&6Maksimum Bellek: &f" + (Runtime.getRuntime().maxMemory() / L / L) + " MB"));
		sender.sendMessage(ServerUtils.getColor("&6Boş Bellek: &f" + (Runtime.getRuntime().freeMemory() / L / L) + " MB"));
		sender.sendMessage(ServerUtils.getColor("&6Toplam Bellek: &f" + (Runtime.getRuntime().totalMemory() / L / L) + " MB"));
		sender.sendMessage(ServerUtils.getColor("&6Çalışma süresi: &f" + date));
	}
}