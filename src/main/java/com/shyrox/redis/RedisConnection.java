package com.shyrox.redis;

import com.shyrox.config.ConfigUtils;
import com.shyrox.ShyroxBungeeUtils;
import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;

@Getter @Setter
public class RedisConnection {
    private final ShyroxBungeeUtils eliceBungee;
    public boolean setupMode = true;

    public RedisConnection(ShyroxBungeeUtils eliceBungee) {
        this.eliceBungee = eliceBungee;
        this.redisConnection();
    }

    private void redisConnection() {
        try {
            System.out.println("Veritabanı ile bağlantıya geçiliyor..");
            this.setupRedisConnetion();
        } catch (IOException iex) {
            System.out.println("Yapılandırma dosyaları üzgünüz ki işlenemiyor!");
            eliceBungee.getPlugin().jedisPool = null;
          //  eliceBungee.getPlugin().shutdown();
        } catch (JedisConnectionException jce) {
            System.out.println("Çalışan, yerel Redis Veritabanı sunucusuna bağlanılamıyor!");
            eliceBungee.getPlugin().jedisPool = null;
    //        eliceBungee.getPlugin().shutdown();
        }
    }

    //TODO: Jedis bağlantısını başlatan ana satırlar.
    private void setupRedisConnetion() throws IOException, JedisConnectionException {
        ConfigUtils configRedis = new ConfigUtils(eliceBungee.getPlugin().mainConfig, "redis");
        ConfigUtils configServer = new ConfigUtils(eliceBungee.getPlugin().mainConfig, "server");

        String redisServer = configRedis.getString("server");
        int redisPort = configRedis.getInt("port");
        String redisPassword = configRedis.getString("password");
        String serverName = configServer.getString("name");

        //TODO: RedisDatabase üzerinde bağlantı için bir şifre yok ise olacaklar.
        if (redisPassword != null && (redisPassword.isEmpty() || redisPassword.equals("none")
                || redisPassword.equals("yok") || redisPassword.equals("boş"))) {
            redisPassword = null;
        }

        //TODO: Eğer sunucuya ayarlanmış bir id yok ise olacaklar.
        if (serverName == null || serverName.isEmpty()) {
            System.out.println("Yapılandırmada sunucu adı belirtilmemiş, boş!");

            eliceBungee.getPlugin().jedisPool = null;
        //    eliceBungee.getPlugin().shutdown();
            return;
        }

        //TODO: Sunucu ile redis bağlantısını yapılandırır
        if (redisServer != null && !redisServer.isEmpty()) {

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(this.eliceBungee.mainConfig.getConfiguration().getInt("max-redis-connections"));
            eliceBungee.getPlugin().jedisPool = new JedisPool(jedisPoolConfig, redisServer, redisPort, 0, redisPassword);

            //TODO: Jedis bağlantısını 2. bir kez test eder.
            try (Jedis jedisPoolResource = eliceBungee.getPlugin().jedisPool.getResource()) {
                jedisPoolResource.ping();

                //TODO: Eğer jedissPool tanımlandı ise gerekli işlemlerini işler.
                if (eliceBungee.getPlugin().jedisPool != null) {
                    System.out.println("Veritabanı sunucusuna başarıyla bağlanıldı.");
                    this.setupMode = false;
                } else {
                    System.out.println("Veritabanı sunucusuna bağlanılamadı!");
                    eliceBungee.getPlugin().jedisPool = null;
                //    eliceBungee.getPlugin().shutdown();
                }
            } catch (JedisConnectionException jce) {
                System.out.println("Veritabanı sunucusuna bağlanılamadı!");
                System.out.println(jce.getMessage());
                eliceBungee.getPlugin().jedisPool = null;
           //     eliceBungee.getPlugin().shutdown();
            }
        } else {
            System.out.println("Veritabanı sunucusu belirtilmemiş durumda!");
            eliceBungee.getPlugin().jedisPool = null;
        //    eliceBungee.getPlugin().shutdown();
        }
    }


}