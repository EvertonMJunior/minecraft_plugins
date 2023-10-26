package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.ActionBar;
import net.goodcraft.api.Item;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;

public class Phantom extends Kit {
    public Phantom() {
        super("Phantom", Material.FEATHER,
                new ItemStack[]{Item.item(Material.FEATHER, 1, "§3Phantom")},
                Arrays.asList("§7Voe por alguns segundos!"));
    }

    public static HashMap<String, ItemStack[]> armour = new HashMap<>();


    @EventHandler
    public void kitPhantom(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (p.getItemInHand().getType() == Material.FEATHER
                && hasAbility(p)) {
            if (!hasCooldown(p)) {
                armour.put(p.getName(), p.getInventory().getArmorContents());
                p.getInventory().setArmorContents(null);
                p.getInventory().setHelmet(Item.leatherArmor(Material.LEATHER_HELMET, "", Color.WHITE));
                p.getInventory().setChestplate(Item.leatherArmor(Material.LEATHER_CHESTPLATE, "", Color.WHITE));
                p.getInventory().setLeggings(Item.leatherArmor(Material.LEATHER_LEGGINGS, "", Color.WHITE));
                p.getInventory().setBoots(Item.leatherArmor(Material.LEATHER_BOOTS, "", Color.WHITE));
                p.playSound(p.getLocation(), Sound.WITHER_DEATH, 2.0F, 2.0F);
                p.setAllowFlight(true);
                p.setFlying(true);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6 * 20, 2));
                new BukkitRunnable() {
                    int time = 6;

                    public void run() {
                        time -= 1;
                        if (time > 0) {
                            ActionBar.INFO.send(p, "Você tem " + time + " segundos de voo restantes!");
                        } else {
                            p.getInventory().setArmorContents(null);
                            p.getInventory().setArmorContents(armour.get(p.getName()));
                            armour.remove(p.getName());
                            ActionBar.ERROR.send(p, "O tempo de voo acabou!");
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            cancel();
                        }
                    }
                }.runTaskTimer(Main.getPlugin(), 0L, 20);
                addCooldown(p, 60);
            } else {
                mensagemcooldown(p);
                return;
            }
        }
    }

}
