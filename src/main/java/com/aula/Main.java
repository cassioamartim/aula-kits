package com.aula;

import com.aula.account.storage.AccountStorage;
import com.aula.command.KitCommand;
import com.aula.kit.list.Kangaroo;
import com.aula.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static AccountStorage accountStorage;

    public static AccountStorage getAccountStorage() {
        return accountStorage;
    }

    @Override
    public void onEnable() {
        accountStorage = new AccountStorage();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new Kangaroo(), this);

        getServer().getCommandMap().register("kit", new KitCommand());
    }
}
