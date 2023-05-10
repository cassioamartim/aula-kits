package com.aula.kit.list;

import com.aula.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class Kangaroo extends Kit implements Listener {

    public Kangaroo() {
        super("Kangaroo", "Pular igual um kangaroo", 120);
    }

    @Override
    public List<ItemStack> getSpecialItems() {
        return Collections.singletonList(new ItemStack(Material.FIREWORK));
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        ItemStack item = event.getItem();

        if (item == null || item.getType().equals(Material.AIR)) return;

        if (isUsingKit(player.getUniqueId()) && isKitItem(item)) {
            event.setCancelled(true);

            player.setVelocity(new Vector(0.0, 0.5, 0.0));
        }
    }
}
