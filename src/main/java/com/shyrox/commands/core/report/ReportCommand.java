package com.shyrox.commands.core.report;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.CooldownUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {


    public ReportCommand() {
        super("report", "", "rapor");
        CooldownUtils.createCooldown("Report");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Oyuncu olman gerek"));
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /report <Oyuncu> [Sebep]");
            return;
        }
        if (args.length == 1) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /report <Oyuncu> [Sebep]");
            return;
        }
        ProxiedPlayer reported = ProxyServer.getInstance().getPlayer(args[0]);
      if (reported == (ProxiedPlayer) sender) {
            sender.sendMessage(ChatColor.RED + "Kendini rapor edemezsin.");
            return;
        }
        if (reported == null) {
            sender.sendMessage(ChatColor.RED + "Oyuncu bulunamadı. ");
            return;
        }
        if (CooldownUtils.isOnCooldown("Report", (ProxiedPlayer) sender)) {
            sender.sendMessage(ServerUtils.getColor("&c&l" + CooldownUtils.getCooldownForPlayerInt("Report", (ProxiedPlayer) sender) + " &csaniye sonra tekrardan rapor edebilirsiniz."));

        } else {
            StringBuilder b = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                b.append(" ");
                b.append(args[i]);
            }
            String reason = b.toString();

            sender.sendMessage(ChatColor.GREEN + "Şikayetin elimize ulaştı, en yakın zamanda incelenecek.");

            CooldownUtils.addCooldown("Report", (ProxiedPlayer) sender, 60);

            ShyroxBungeeUtils.getPlugin().getReportManager().reportMessage((ProxiedPlayer) sender, reported, reason);
        }
    }
}