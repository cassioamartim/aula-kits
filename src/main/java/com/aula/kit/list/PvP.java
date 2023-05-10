package com.aula.kit.list;

import com.aula.kit.Kit;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class PvP extends Kit {

    public PvP() {
        super("PvP", "Kit padrão do servidor.", 0);
    }

    @Override
    public List<ItemStack> getSpecialItems() {
        return Collections.emptyList();
    }
}
