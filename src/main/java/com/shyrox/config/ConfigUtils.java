package com.shyrox.config;

import com.shyrox.ShyroxBungeeUtils;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
public class ConfigUtils {
    private final FileConfig fileConfig;
    private Configuration configuration;
    private String pathValue;

    public static File reportFile;

    public static String pluginFolder = "plugins/" + ShyroxBungeeUtils.getPlugin().getDescription().getName() + "/";

    public ConfigUtils(FileConfig fileConfig, String pathValue) {
        this.fileConfig = fileConfig;
        this.pathValue = pathValue;

        this.configuration = fileConfig.getConfiguration();
    }

    public String getString(String path) {
        if (this.contains(this.getFinalPath(path))) {
            return this.configuration.getString(this.getFinalPath(path));
        }

        return "HATA: GİRDİ DEĞERİ BULUNAMADI!";
    }

    public String getString(String path, boolean colorized) {
        if (this.contains(this.getFinalPath(path))) {
            String toReturn;

            if (colorized) {
                toReturn = ChatColor.translateAlternateColorCodes('&', this.configuration.getString(this.getFinalPath(path)));
            } else {
                return this.configuration.getString(this.getFinalPath(path));
            }

            return toReturn;
        }

        return "HATA: GİRDİ DEĞERİ BULUNAMADI!";
    }

    public boolean getBoolean(String path) {
        if (this.contains(this.getFinalPath(path))) {
            return this.configuration.getBoolean(this.getFinalPath(path));
        }

        return false;
    }

    public int getInt(String path) {
        if (this.contains(this.getFinalPath(path))) {
            return this.configuration.getInt(this.getFinalPath(path));
        }

        return 0;
    }

    public double getDouble(String path) {
        if (this.contains(this.getFinalPath(path))) {
            return this.configuration.getDouble(this.getFinalPath(path));
        }

        return 0;
    }

    public List<String> getStringList(String path, boolean colorized) {
        ArrayList<String> toReturn = new ArrayList<>();
        for (String colorizedList : this.getStringList(path)) {
            if (colorized) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', colorizedList));
            } else {
                return this.getStringList(path);
            }
        }

        return toReturn;
    }

    public List<String> getStringList(String path) {
        if (this.contains(this.getFinalPath(path))) {
            return new ArrayList<>(this.configuration.getStringList(this.getFinalPath(path)));
        }

        return Collections.singletonList("HATA: GİRDİ LİST DEĞERİ BULUNAMADI!");
    }

    public void set(Object value) {
        this.set(null, value);
    }

    public void set(String path, Object values) {
        this.configuration.set(this.pathValue + (path == null ? "" : "." + path), values);
    }

    public Object get(String path) {
        return this.get(path, null);
    }

    public Object get(String path, Object def) {
        return this.configuration.get(this.pathValue + (path == null ? "" : "." + path), def);
    }

    private String getFinalPath(String path) {
        return this.pathValue + "." + path;
    }

    public void configSave() {
        this.fileConfig.configSave(ShyroxBungeeUtils.getPlugin());
    }

    public String configDelete() {
        if (this.fileConfig.getFile().delete()) {
            return null;
        }

        return "HATA: SİLİM İŞLEMİ GERÇEKLEŞTİRİLEMEDİ";
    }

    public boolean contains(String path) {
        return this.configuration.get(path, (Object)null) != null;
    }
}

