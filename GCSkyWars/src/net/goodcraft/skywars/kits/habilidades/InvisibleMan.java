package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class InvisibleMan extends Kit {
    public InvisibleMan(){
        super("InvisibleMan", Material.SLIME_BALL,
                new ItemStack[]{
                        Item.item(Material.SLIME_BALL, 1, "§3InvisibleMan"),
                }, Arrays.asList(
                        "§7Fique invisível por 5",
                        "§7segundos, assim podendo",
                        "§7batalhar ou fugir sem",
                        "§7ser visto!"
                ));
        setVip();
        setPrice(4000);
    }

    public static ArrayList<UUID> invisible = new ArrayList<>();

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if(!e.getAction().name().contains("RIGHT")) return;
        if (!hasAbility(p)) return;
        if (i == null) return;
        if (i.getType() != Material.SLIME_BALL) return;
        if (Main.getMg().getGameState() == GameState.PREGAME) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);

        if(invisible.contains(p.getUniqueId())){
            Message.INFO.send(p, "Você ainda está invisível!");
            return;
        }

        if (hasCooldown(p)) {
            mensagemcooldown(p);
            return;
        }
        addCooldown(p, 30);
        invisible.add(p.getUniqueId());

        for(UUID id : Main.getMg().getPlayers()){
            Player player = Bukkit.getPlayer(id);
            if(player == null) continue;
            player.hidePlayer(p);
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 1, false, false));
        int slot = p.getInventory().getHeldItemSlot();
        p.getInventory().setItem(slot, Item.addGlow(p.getInventory().getItem(slot)));

        final double[] leftTime = {5};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (leftTime[0] <= 0) {
                    p.removePotionEffect(PotionEffectType.INVISIBILITY);
                    ActionBar.INFO.send(p, "A invisibilidade acabou!");
                    p.getInventory().setItem(slot, Item.removeGlow(p.getInventory().getItem(slot)));
                    invisible.remove(p.getUniqueId());
                    Utils.showForAll(p);

                    cancel();
                    return;
                }
                ActionBar.INFO.send(p, "Ainda invisível por " +  new DecimalFormat("#.#").format(leftTime[0]) + " segundos");
                leftTime[0] -= 0.1;
            }
        }.runTaskTimer(Main.getPlugin(), 0L, 2L);
    }
}
