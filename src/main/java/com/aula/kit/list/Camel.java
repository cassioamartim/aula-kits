package com.aula.kit.list;

import com.aula.kit.Kit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Camel extends Kit implements Listener {

    public Camel() {
        super("Camel", "Andar rápido sobre a areia", 120);
    }

    @Override
    public List<ItemStack> getSpecialItems() {
        return Collections.emptyList();
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (isUsingKit(player.getUniqueId())) {

            Block down = event.getFrom().getBlock().getRelative(BlockFace.DOWN);

            if (down.getType().equals(Material.SAND)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 2));
            }

        }

    }
}
