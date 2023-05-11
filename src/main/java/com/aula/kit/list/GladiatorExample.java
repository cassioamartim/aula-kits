package com.aula.kit.list;

import java.util.*;

import com.aula.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GladiatorExample {

    /*
    private final GladiatorController gladiatorController = new GladiatorController();

    public GladiatorExample() {
        super("Gladiator",
                "Puxe os jogadores em uma jaula, onde ficará somente você e ele para tirarem x1\n\n§aAdicionado recentemente!", 0);
    }

    @Override
    public List<ItemStack> getSpecialItems() {
        return Collections.singletonList(new ItemStack(Material.IRON_FENCE));
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player))
            return;

        if (event.getPlayer().getItemInHand() == null)
            return;

        Player player = event.getPlayer();

        if (isUsingKit(player.getUniqueId()) && isKitItem(player.getItemInHand())) {
            event.setCancelled(true);

            Player target = (Player) event.getRightClicked();

            gladiatorController.sendGladiator(player, target);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getAction() != Action.PHYSICAL && isUsingKit(player.getUniqueId()) && isKitItem(e.getItem())) {
            player.updateInventory();
            e.setCancelled(true);
        }
    }

    private static final double HEIGHT = 190.0d;

    public static class GladiatorController {

        private int radius = 8;
        private int height = 12;

        private Map<Player, Gladiator> playerList;
        private List<Gladiator> gladiatorList;
        private List<Block> blockList;

        private GladiatorListener listener = new GladiatorListener();

        public GladiatorController() {
            playerList = new HashMap<>();
            gladiatorList = new ArrayList<>();
            blockList = new ArrayList<>();
        }

        public Location[] createGladiator(List<Block> blockList, Location gladiatorLocation) {
            Location loc = gladiatorLocation;
            boolean hasGladi = true;

            while (hasGladi) {
                hasGladi = false;
                boolean stop = false;
                for (double x = -8.0D; x <= 8.0D; x += 1.0D) {
                    for (double z = -8.0D; z <= 8.0D; z += 1.0D) {
                        for (double y = 0.0D; y <= 10.0D; y += 1.0D) {
                            Location l = new Location(loc.getWorld(), loc.getX() + x, HEIGHT + y, loc.getZ() + z);

                            if (l.getBlock().getType() != Material.AIR) {
                                hasGladi = true;
                                loc = new Location(loc.getWorld(), loc.getX() + 20.0D, loc.getY(), loc.getZ());
                                stop = true;
                            }
                            if (stop) {
                                break;
                            }
                        }
                        if (stop) {
                            break;
                        }
                    }
                    if (stop) {
                        break;
                    }
                }
            }

            Block mainBlock = loc.getBlock();

            for (double x = -radius; x <= radius; x += 1.0D) {
                for (double z = -radius; z <= radius; z += 1.0D) {
                    for (double y = 0.0D; y <= height; y += 1.0D) {
                        Location l = new Location(mainBlock.getWorld(), mainBlock.getX() + x, HEIGHT + y,
                                mainBlock.getZ() + z);
                        l.getBlock().setType(Material.GLASS);
                        blockList.add(l.getBlock());
                        this.blockList.add(l.getBlock());
                    }
                }
            }

            for (double x = -radius + 1; x <= radius - 1; x += 1.0D) {
                for (double z = -radius + 1; z <= radius - 1; z += 1.0D) {
                    for (double y = 1.0D; y <= height; y += 1.0D) {
                        Location l = new Location(mainBlock.getWorld(), mainBlock.getX() + x, HEIGHT + y,
                                mainBlock.getZ() + z);
                        l.getBlock().setType(Material.AIR);
                        this.blockList.remove(l.getBlock());
                    }
                }
            }

            return new Location[]{
                    new Location(mainBlock.getWorld(), mainBlock.getX() + 6.5D, HEIGHT + 1.0d, mainBlock.getZ() + 6.5D),
                    new Location(mainBlock.getWorld(), mainBlock.getX() - 5.5D, HEIGHT + 1.0d,
                            mainBlock.getZ() - 5.5D)};
        }

        public boolean isInFight(Player player) {
            return playerList.containsKey(player);
        }

        public Gladiator getGladiator(Player player) {
            return playerList.get(player);
        }

        public boolean isGladiatorBlock(Block block) {
            return blockList.contains(block);
        }

        public void sendGladiator(Player player, Player target) {
            Gladiator gladiator = new Gladiator(player, target);

            playerList.put(player, gladiator);
            playerList.put(target, gladiator);
            gladiatorList.add(gladiator);
            listener.register();
        }

        public void removeGladiator(Gladiator gladiator) {
            playerList.remove(gladiator.quemChamou);
            playerList.remove(gladiator.foiChamado);
            gladiatorList.remove(gladiator);

            if (playerList.isEmpty())
                listener.unregister();
        }

        public class Gladiator {

            // Gladiator(quemChamou=Quem Chamou, foiChamado= Quem foi chamado, gladiatorLocation=Localização do glad, backLocation=Localização de volta,
            // gladiatorBlocks=Os blocos do quemChamou, Um bloco X vai ser adicionado no gladiatorBlocks também, playersBlocks=BLocos que jogadores botaram no glad)

            private Player quemChamou;
            private Player foiChamado;

            private Location gladiatorLocation;
            private Location backLocation;

            private List<Block> gladiatorBlocks;
            private List<Block> playersBlocks;

            private int time;

            public Gladiator(Player quemChamou, Player foiChamado) {
                this.quemChamou = quemChamou;
                this.foiChamado = foiChamado;

                this.gladiatorBlocks = new ArrayList<>();
                this.playersBlocks = new ArrayList<>();

                this.gladiatorLocation = quemChamou.getLocation();
                this.backLocation = quemChamou.getLocation();

                Location[] location = createGladiator(gladiatorBlocks, gladiatorLocation);

                Location l1 = location[0];
                l1.setYaw(135.0F);

                quemChamou.teleport(l1);
                quemChamou.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5));

                Location l2 = location[1];
                l2.setYaw(315.0F);

                foiChamado.teleport(l2);
                foiChamado.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5));

                foiChamado.damage(1, quemChamou);
                quemChamou.damage(1, foiChamado);
            }

            public void handleEscape(boolean teleportBack) {
                clearGladiator();

                if (teleportBack)
                    teleportBack();

                quemChamou.removePotionEffect(PotionEffectType.WITHER);
                foiChamado.removePotionEffect(PotionEffectType.WITHER);
                removeGladiator(this);

            }

            public void handleWin(Player death) {
                Player winner = (death == quemChamou ? foiChamado : quemChamou);

                clearGladiator();

                winner.teleport(backLocation);
                winner.removePotionEffect(PotionEffectType.WITHER);
                winner.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5));

                removeGladiator(this);
            }

            public void handleFinish() {
                clearGladiator();
                teleportBack();

                if (quemChamou.isOnline())
                    quemChamou.removePotionEffect(PotionEffectType.WITHER);

                if (foiChamado.isOnline())
                    foiChamado.removePotionEffect(PotionEffectType.WITHER);

                removeGladiator(this);
            }

            public void pulse() {
                time++;

                if (time == 10) {
                    for (Block block : gladiatorBlocks) {
                        if (block.hasMetadata("gladiatorBreakable")) {
                            block.setType(Material.AIR);
                        }
                    }
                }

                if (time == 120) {
                    quemChamou.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 60, 3));
                    foiChamado.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 60, 3));
                }

                if (time == 180) {
                    handleFinish();
                }
            }

            public void addBlock(Block block) {
                if (!this.playersBlocks.contains(block))
                    this.playersBlocks.add(block);
            }

            public boolean removeBlock(Block block) {
                if (this.playersBlocks.contains(block)) {
                    this.playersBlocks.remove(block);
                    return true;
                }

                return false;
            }

            private void clearGladiator() {
                for (Block block : gladiatorBlocks) {
                    block.setType(Material.AIR);

                    if (blockList.contains(block))
                        blockList.remove(block);
                }

                for (Block block : playersBlocks) {
                    block.setType(Material.AIR);

                    if (blockList.contains(block))
                        blockList.remove(block);
                }
            }

            private void teleportBack() {
                quemChamou.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5));
                quemChamou.teleport(backLocation);

                foiChamado.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 5));
                foiChamado.teleport(backLocation);
            }

            public boolean isInGladiator(Player player) {
                return player == this.foiChamado || player == quemChamou;
            }
        }

        public class GladiatorListener implements Listener {

            private boolean registered;

            @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
            public void onPlayerDeath(PlayerDeathEvent event) {
                Player player = event.getEntity();

                if (isInFight(player)) {
                    event.getDrops().clear();
                    getGladiator(player).handleWin(player);
                }
            }

            @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
            public void onPlayerQuit(PlayerQuitEvent event) {
                Player player = event.getPlayer();

                if (isInFight(player))
                    getGladiator(player).handleWin(player);
            }

            @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
            public void onBlockPlace(BlockPlaceEvent event) {
                Player player = event.getPlayer();

                if (isInFight(player))
                    getGladiator(player).addBlock(event.getBlock());
            }

            @EventHandler(priority = EventPriority.MONITOR)
            public void onBlockBreak(BlockBreakEvent event) {
                Player player = event.getPlayer();

                if (blockList.contains(event.getBlock())) {
                    event.setCancelled(true);
                    return;
                }

                if (isInFight(player)) {
                    getGladiator(player).removeBlock(event.getBlock());
                }
            }

            @SuppressWarnings("deprecation")
            @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
            public void onBlockBreak(BlockDamageEvent event) {
                if (blockList.contains(event.getBlock())) {
                    Player player = event.getPlayer();
                    Block block = event.getBlock();

                    player.sendBlockChange(block.getLocation(), Material.BEDROCK, (byte) 0);
                    return;
                }
            }

            @EventHandler
            public void onUpdate(UpdateEvent event) {
                if (event.getType() == UpdateType.SECOND) {
                    gladiatorList.iterator().forEachRemaining(Gladiator::pulse);
                }
            }

            @EventHandler
            public void onPlayerMoveUpdate(PlayerMoveUpdateEvent event) {
                Player player = event.getPlayer();

                if (isInFight(player)) {
                    Gladiator gladiator = getGladiator(player);

                    if (event.getFrom().getY() - HEIGHT > height)
                        gladiator.handleEscape(true);
                    else if (event.getFrom().getY() <= HEIGHT - 2 && gladiator.time > 2)
                        gladiator.handleEscape(true);
                }
            }

            @EventHandler
            public void onTeleportAll(TeleportAllEvent event) {
                for (Gladiator gladiator : gladiatorList)
                    gladiator.handleEscape(false);
            }

            @EventHandler
            public void onExplode(EntityExplodeEvent event) {
                Iterator<Block> blockIt = event.blockList().iterator();

                while (blockIt.hasNext()) {
                    Block b = (Block) blockIt.next();
                    if (blockList.contains(b)) {
                        blockIt.remove();
                    }
                }
            }

            public void register() {
                if (!registered) {
                    Bukkit.getPluginManager().registerEvents(this, GameMain.getInstance());
                    registered = true;
                }
            }

            public void unregister() {
                if (registered) {
                    HandlerList.unregisterAll(this);
                    registered = false;
                }
            }
        }

    }*/
}