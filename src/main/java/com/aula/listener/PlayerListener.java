package com.aula.listener;

import com.aula.Main;
import com.aula.account.Account;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        new Account(event.getPlayer());
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Main.getAccountStorage().delete(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void kick(PlayerKickEvent event) {
        Main.getAccountStorage().delete(event.getPlayer().getUniqueId());
    }
}
