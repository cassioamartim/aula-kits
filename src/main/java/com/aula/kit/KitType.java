package com.aula.kit;

import com.aula.kit.list.Camel;
import com.aula.kit.list.Kangaroo;
import com.aula.kit.list.PvP;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum KitType {

    NONE(new Kit("Nenhum", "", 0) {
        @Override
        public List<ItemStack> getSpecialItems() {
            return Collections.emptyList();
        }
    }),

    PVP(new PvP()),
    KANGAROO(new Kangaroo()),
    CAMEL(new Camel());

    private final Kit kit;

    private static final Map<String, KitType> KIT_TYPE_MAP = new HashMap<>();

    static {
        for (KitType type : values())
            KIT_TYPE_MAP.put(type.getKit().getName().toLowerCase(), type);
    }

    public static KitType getKit(String name) {
        return KIT_TYPE_MAP.get(name.toLowerCase());
    }

    KitType(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }

}
