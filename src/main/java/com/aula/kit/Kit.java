package com.aula.kit;

import com.aula.Main;
import com.aula.account.Account;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.UUID;

public abstract class Kit {

    private final String name;

    private final String description;

    private final int price;

    public Kit(String name, String description, int price) {
        this.name = name;

        this.description = description;

        this.price = price;
    }

    public abstract List<ItemStack> getSpecialItems();

    public boolean isUsingKit(UUID uniqueId) {
        Account account = Main.getAccountStorage().read(uniqueId);

        if (account == null) return false;

        return account.getKitType().getKit().getName().equalsIgnoreCase(getName());
    }

    public boolean isKitItem(ItemStack stack) {
        if (getSpecialItems().isEmpty()) return false;

        for (ItemStack specialItem : getSpecialItems()) {
            if (specialItem.isSimilar(stack))
                return true;
        }

        return false;
    }

    public void sendInventory(Player player) {
        PlayerInventory inv = player.getInventory();

        inv.clear();

        for (int i = 0; i < 36; i++) {
            inv.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
        }

        inv.setItem(13, new ItemStack(Material.BOWL, 64));
        inv.setItem(14, new ItemStack(Material.RED_MUSHROOM, 64));
        inv.setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));

        inv.setItem(0, new ItemStack(Material.STONE_SWORD));

        if (!getSpecialItems().isEmpty()) {

            int slot = 1;
            for (ItemStack specialItem : getSpecialItems()) {

                inv.setItem(slot, specialItem);

                slot++;
            }

        }

        player.sendMessage("§aVocê recebeu os itens do kit §e" + getName() + "§a!");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
