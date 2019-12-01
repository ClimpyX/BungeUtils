package com.shyrox.commands.core.request;

import com.shyrox.ShyroxBungeeUtils;
import com.shyrox.utils.CooldownUtils;
import com.shyrox.utils.ServerUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RequestCommand extends Command {

    public RequestCommand() {
        super("request", "", "istek");
        CooldownUtils.createCooldown("Request");
    }

    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Oyuncu olman gerek"));
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Kullanım: /request [istek]");
            return;
        }

        if (CooldownUtils.isOnCooldown("Request", (ProxiedPlayer) sender)) {
            sender.sendMessage(ServerUtils.getColor("&c&l" + CooldownUtils.getCooldownForPlayerInt("Request", (ProxiedPlayer) sender) + " &csaniye sonra tekrardan istek bildirimi atabilirsiniz."));
        } else {
            StringBuilder b = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                b.append(" ");
                b.append(args[i]);
            }
            String reason = b.toString();

            sender.sendMessage(ChatColor.GREEN + "İsteğin elimize ulaştı, en yakın zamanda incelenecek.");

            CooldownUtils.addCooldown("Request", (ProxiedPlayer) sender, 60);

            ShyroxBungeeUtils.getPlugin().getRequestManager().requestMessage((ProxiedPlayer) sender, reason);
        }
    }
}