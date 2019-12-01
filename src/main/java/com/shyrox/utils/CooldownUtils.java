package com.shyrox.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class CooldownUtils {

    private static HashMap<String, HashMap<UUID, Long>> cooldown = new HashMap();

    public static void clearCooldowns()
    {
        cooldown.clear();
    }

    public static void createCooldown(String name) {
        if (cooldown.containsKey(name)) {
            throw new IllegalArgumentException("Bu cooldown mevcut.");
        }
        cooldown.put(name, new HashMap());
    }

    public static HashMap<UUID, Long> getCooldownMap(String name) {
        if (cooldown.containsKey(name)) {
            return (HashMap)cooldown.get(name);
        }
        return null;
    }

    public static void addCooldown(String name, ProxiedPlayer p, int seconds) {
        if (!cooldown.containsKey(name)) {
            throw new IllegalArgumentException(String.valueOf(name) + " mevcut değil.");
        }
        long next = System.currentTimeMillis() + seconds * 1000L;
        ((HashMap)cooldown.get(name)).put(p.getUniqueId(), Long.valueOf(next));
    }

    public static boolean isOnCooldown(String name, ProxiedPlayer p) {
        return (cooldown.containsKey(name)) && (((HashMap)cooldown.get(name)).containsKey(p.getUniqueId())) && (System.currentTimeMillis() <= ((Long)((HashMap)cooldown.get(name)).get(p.getUniqueId())).longValue());
    }

    public static int getCooldownForPlayerInt(String name, ProxiedPlayer p) {
        return (int)((((Long)((HashMap)cooldown.get(name)).get(p.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000L);
    }

    public static void removeCooldown(String name, ProxiedPlayer p) {
        if (!cooldown.containsKey(name)) {
            throw new IllegalArgumentException(String.valueOf(name) + " mevcut değil.");
        }
        ((HashMap)cooldown.get(name)).remove(p.getUniqueId());
    }
}

