package net.goodcraft.minigames.eventos;

import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.AdminEvent;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.ArrayList;

public class GeneralEvents implements Listener {

    private final Minigame MINIGAME;

    public GeneralEvents(Minigame minigame){
        this.MINIGAME = minigame;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if ((MINIGAME.getGameState() == GameState.PREGAME && !MINIGAME.hasOption(Option.GETS_DAMAGE_PREGAME)) ||
                MINIGAME.getGameState() == GameState.INVENCIBILITY || !MINIGAME.hasOption(Option.GETS_DAMAGE)) {
            e.setCancelled(true);
        }
        if(MINIGAME.getSpectatorManager().spectators.containsKey(e.getEntity().getUniqueId())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(MINIGAME.hasOption(Option.CAN_USE_INVENTORY)) return;
        if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
        if (MINIGAME.getGameState() == GameState.PREGAME) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void spawn(EntitySpawnEvent e) {
        ArrayList<EntityType> allowedEnt = new ArrayList<>();
        allowedEnt.add(EntityType.ARMOR_STAND);
        allowedEnt.add(EntityType.DROPPED_ITEM);
        allowedEnt.add(EntityType.PLAYER);

        if (allowedEnt.contains(e.getEntityType())) {
            return;
        }
        if (MINIGAME.getGameState() == GameState.PREGAME) {
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if(MINIGAME.hasOption(Option.FREE_LAPIS_ON_ENCHANTMENTTABLE) &&
                e.getItemDrop().getItemStack().getType() == Material.INK_SACK &&
                e.getItemDrop().getItemStack().getData().getData() == (byte)4){
            e.getItemDrop().remove();
            return;
        }

        if (MINIGAME.getGameState() == GameState.PREGAME ||
                !MINIGAME.hasOption(Option.CAN_DROP_ITEMS)) {
            e.setCancelled(true);
        } else if (MINIGAME.getSpectatorManager().spectators.containsKey(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent e) {
        if (MINIGAME.getGameState() == GameState.PREGAME &&
                !MINIGAME.hasOption(Option.CAN_PICKUP_DROPS)) {
            e.setCancelled(true);
        } else if (AdminAPI.admins.containsKey(e.getPlayer().getName())) {
            e.setCancelled(true);
        } else if (MINIGAME.getSpectatorManager().spectators.containsKey(e.getPlayer().getUniqueId())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(!MINIGAME.hasOption(Option.CAN_DESTROY)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockCanBuildEvent e){
        if(MINIGAME.hasOption(Option.CAN_BUILD)) return;

        if(e.getBlock().getLocation().getY() >= MINIGAME.getMaxY()){
            e.setBuildable(false);
            return;
        }
        e.setBuildable(true);
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e){
        if(MINIGAME.hasOption(Option.CAN_BUILD)) return;

        if(e.getBlockClicked().getLocation().getY() >= MINIGAME.getMaxY() + 5){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAdmin(AdminEvent e) {
        Player p = e.getPlayer();
        if (e.isEntering()) {
            if(MINIGAME.isPlayer(p.getUniqueId())){
                MINIGAME.getPlayers().remove(p.getUniqueId());
                if (MINIGAME.getGameState() != GameState.PREGAME) {
                    String kit = "Nenhum";
                    if (KitManager.hasAnyKit(p)) kit = KitManager.getKit(p).toString();
                    if(!MINIGAME.hasOption(Option.UNLIMITED_LIFES)){
                        Utils.broadcast("§6[" + MINIGAME.getName() + "] §e" + p.getName() + "§7(" + kit + ")§e morreu!");
                        KitManager.removeKit(p);
                    }
                }
            }
        } else {
            if (MINIGAME.getGameState() == GameState.PREGAME) MINIGAME.getPlayers().add(p.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent e) {
        if(MINIGAME.getGameState() != GameState.PREGAME &&
                !MINIGAME.hasOption(Option.DOES_FOODLEVEL_CHANGE)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if(!MINIGAME.hasOption(Option.FREE_LAPIS_ON_ENCHANTMENTTABLE)) return;

        if (e.getInventory() instanceof EnchantingInventory) {
            EnchantingInventory inv = (EnchantingInventory) e.getInventory();
            Dye d = new Dye();
            d.setColor(DyeColor.BLUE);
            ItemStack i = d.toItemStack();
            i.setAmount(20);
            inv.setItem(1, i);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!MINIGAME.hasOption(Option.FREE_LAPIS_ON_ENCHANTMENTTABLE)) return;

        if (e.getInventory() instanceof EnchantingInventory) {
            if(e.getSlot() == 1){
                e.setCancelled(true);
                e.setResult(Event.Result.DENY);
            }
        }
    }
}
