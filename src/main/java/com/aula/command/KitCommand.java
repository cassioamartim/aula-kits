package com.aula.command;

import com.aula.Main;
import com.aula.account.Account;
import com.aula.kit.KitType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends Command {

    public KitCommand() {
        super("kit");
    }

    @Override
    public boolean execute(CommandSender sender, String lb, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente jogadores podem selecionar um kit!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cModo de uso: /kit <nome-do-kit>.");
            return false;
        }

        KitType type = KitType.getKit(args[0]);

        if (type == null) {
            player.sendMessage("§cKit não encontrado.");
            return false;
        }

        Account account = Main.getAccountStorage().read(player.getUniqueId());

        if (account.getKitType().equals(type)) {
            player.sendMessage("§cVocê já está usando o kit §e" + type.getKit().getName() + "§c.");
            return false;
        }

        account.setKitType(type);

        type.getKit().sendInventory(player);

        return true;
    }
}
