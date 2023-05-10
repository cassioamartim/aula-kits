package com.aula.account;

import com.aula.Main;
import com.aula.kit.KitType;
import org.bukkit.entity.Player;

public class Account {

    private final Player player;

    private KitType kitType;

    public Account(Player player) {
        this.player = player;

        this.kitType = KitType.NONE;

        Main.getAccountStorage().save(this);
    }

    public Player getPlayer() {
        return player;
    }

    public KitType getKitType() {
        return kitType;
    }

    public void setKitType(KitType kitType) {
        this.kitType = kitType;
    }

}
