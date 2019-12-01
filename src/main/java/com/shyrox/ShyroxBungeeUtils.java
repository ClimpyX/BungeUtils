package com.shyrox;

import com.shyrox.commands.bungee.AlertCommand;
import com.shyrox.commands.bungee.FindCommand;
import com.shyrox.commands.bungee.GListCommand;
import com.shyrox.commands.bungee.ServerCommand;
import com.shyrox.commands.core.*;
import com.shyrox.commands.core.message.MsgCommand;
import com.shyrox.commands.core.message.SocialSpy;
import com.shyrox.commands.core.message.ToggleMsg;
import com.shyrox.commands.core.report.ReportCommand;
import com.shyrox.commands.core.request.RequestCommand;
import com.shyrox.config.ConfigUtils;
import com.shyrox.config.FileConfig;
import com.shyrox.handlers.MotdHandler;
import com.shyrox.listeners.ChatLockListener;
import com.shyrox.listeners.EliceListener;
import com.shyrox.listeners.PlayerJoinListener;
import com.shyrox.managers.ReportManager;
import com.shyrox.managers.RequestManager;
import com.shyrox.tasks.AnnouncementTask;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter @Setter
public class ShyroxBungeeUtils extends Plugin implements Listener {

    @Getter public static ShyroxBungeeUtils plugin;

    @Getter public FileConfig mainConfig;

    public int peakPlayers = 0;
    public int currentMaxPlayers = 0;
    public int currentOnlinePlayers = 0;

    public ServerPing.PlayerInfo[] ServerPingDefault;
    public ServerPing.PlayerInfo[] ServerPingMaintenance;

    public boolean mainteanceMode = true;
    public boolean chatMuted = false;

    public ReportManager reportManager;
    public RequestManager requestManager;

    public JedisPubSub jedisPubSub;
    public JedisPool jedisPool;

    public void onEnable() {
        plugin = this;


        log("Eklenti başlatılıyor..");

        this.mainConfig = new FileConfig(this, "settings.yml");
        this.mainteanceMode = mainConfig.getConfiguration().getBoolean("settings.maintenance");

        serverHoverList();
        registerServer();
        registerSchedulers();

        log("Eklenti başlatıldı.");
    }

    public void onDisable() {
        log("Eklenti kapatıldı.");
    }


    public void log(String mesaj) {
        getLogger().info(mesaj);
    }

    public void registerServer() {
        PluginManager pm = ProxyServer.getInstance().getPluginManager();

        // Bungee Komutları
        pm.registerCommand(this, new AlertCommand());
        pm.registerCommand(this, new ServerCommand());
        pm.registerCommand(this, new FindCommand());
        pm.registerCommand(this, new GListCommand());

        // Core Komutları
        pm.registerCommand(this, new HubCommand());
        pm.registerCommand(this, new HelpCommand());
        pm.registerCommand(this, new ReloadCommand());
        pm.registerCommand(this, new MaintenanceCommand());
        pm.registerCommand(this, new MotdCommand());
        pm.registerCommand(this, new ReportCommand());
        pm.registerCommand(this, new RequestCommand());
        pm.registerCommand(this, new AddServerCommand());
        pm.registerCommand(this, new RemoveServerCommand());
        pm.registerCommand(this, new StatusCommand());
        pm.registerCommand(this, new ChatCommand());
        pm.registerCommand(this, new PluginsCommand());
        pm.registerCommand(this, new MsgCommand());
        pm.registerCommand(this, new SocialSpy());
        pm.registerCommand(this, new ToggleMsg());

        // Listenerlar
        pm.registerListener(this, new EliceListener(this));
        pm.registerListener(this, new PlayerJoinListener());
        pm.registerListener(this, new ChatLockListener());

        // Handlerlar (Önemli)
        new MotdHandler(this);


        // Managerler
        this.reportManager = new ReportManager(this);
        this.requestManager = new RequestManager(this);
    }

    public void serverHoverList() {
        getProxy().getScheduler().schedule(this, new Runnable() {

        public void run() {
            List<String> hoverList = ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().getStringList("ServerPingList");
            ServerPing.PlayerInfo[] arrayOfPlayerInfo = new ServerPing.PlayerInfo[hoverList.size()];
            for (int i = 0; i < arrayOfPlayerInfo.length; i++) {
                String line = ChatColor.translateAlternateColorCodes('&', ((String) hoverList.get(i)).replace("[peakplayers]", String.valueOf(ShyroxBungeeUtils.this.peakPlayers)).replace("[online]", String.valueOf(ShyroxBungeeUtils.getPlugin().currentOnlinePlayers)).replace("[max]", String.valueOf(ShyroxBungeeUtils.getPlugin().getCurrentMaxPlayers())));
                arrayOfPlayerInfo[i] = new ServerPing.PlayerInfo((line.length() > 0) ? line : "§r", "");
            }
            ShyroxBungeeUtils.getPlugin().ServerPingDefault = arrayOfPlayerInfo;
        }
        },1L, 10L, TimeUnit.SECONDS);
    }


    public void setPeakPlayers(int peak_players) {
        this.peakPlayers = peak_players;
        ShyroxBungeeUtils.getPlugin().getMainConfig().getConfiguration().set("peakplayers", Integer.valueOf(this.peakPlayers));
        ShyroxBungeeUtils.getPlugin().getMainConfig().configSave(ShyroxBungeeUtils.getPlugin());
    }

    private void registerSchedulers() {
        ConfigUtils configAnnouncements = new ConfigUtils(getMainConfig(), "duyurular");

        getProxy().getScheduler().schedule(this, new AnnouncementTask(), 0L, configAnnouncements.getInt("aralik") * 20L, TimeUnit.SECONDS);
    }
}