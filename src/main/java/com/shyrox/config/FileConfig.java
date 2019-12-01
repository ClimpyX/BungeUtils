package com.shyrox.config;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static net.md_5.bungee.api.ProxyServer.getInstance;

@Getter
public class FileConfig {
    private Configuration configuration;
    private File file;

    public FileConfig(Plugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        checkFileStatus(plugin, this.file, false);

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public FileConfig(String fileName) {
        this.file = new File(fileName);

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void checkFileStatus(Plugin plugin, File checkedFile, boolean hasDirectory) {
        String fileName = checkedFile.getName();

        if (!checkedFile.exists()) {
            if (hasDirectory) {
                if (checkedFile.mkdir())
                    plugin.getLogger().log(Level.WARNING, "Yapılandırılma dosyası oluşturulamadı!");
            } else {
                if (checkedFile.getParentFile().mkdir())
                    plugin.getLogger().log(Level.WARNING, "Yapılandırılma dizini oluşturulamadı!");
            }

            if (plugin.getResourceAsStream(fileName) == null) {
                try {
                    if (!checkedFile.createNewFile())
                        plugin.getLogger().log(Level.WARNING, "Yapılandırılma dosyası oluşturulamadı!");
                } catch (IOException ex) {
                    plugin.getLogger().log(Level.WARNING, "Yapılandırılan dosya oluşturulamadı: " + fileName + "!");
                }
            } else {
                this.configSave(plugin);
            }
        }
    }

    public void configSave(Plugin plugin) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, new File(plugin.getDataFolder(), file.getName()));
        } catch (IOException ex) {
            getInstance().getLogger().warning("Yapılandırma dosyası " + this.file.getName() + " kayıt edilemedi!");
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (IOException ex) {
            getInstance().getLogger().warning("Yapılandırma dosyası " + this.file.getName() + " yeniden yüklenemedi!");
            ex.printStackTrace();
        }
    }

}