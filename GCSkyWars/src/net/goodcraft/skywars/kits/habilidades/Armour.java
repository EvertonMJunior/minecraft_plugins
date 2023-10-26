package net.goodcraft.skywars.kits.habilidades;

import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.skywars.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class Armour extends Kit {
    public Armour() {
        super("Armour", Material.IRON_CHESTPLATE,
                new ItemStack[]{}, Arrays.asList(
                        "§7Ganhe partes de armadura de",
                        "§7ferro aleatórias conforme",
                        "§7você matar seus oponentes!"
                ));
        setPrice(5000);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;

        if (e.getEntity().getKiller() == null) return;
        if (!hasAbility(e.getEntity().getKiller())) return;

        Player killer = e.getEntity().getKiller();
        Player killed = e.getEntity();

        if (killer.getUniqueId().equals(killed.getUniqueId())) return;
        if (hasCooldown(killer)) {
            mensagemcooldown(killer);
            return;
        }
        addCooldown(killer, 5);
        ItemStack[] armorParts = new ItemStack[]{
                Item.item(Material.IRON_HELMET),
                Item.item(Material.IRON_CHESTPLATE),
                Item.item(Material.IRON_LEGGINGS),
                Item.item(Material.IRON_BOOTS)
        };
        int r = new Random().nextInt(armorParts.length + 1);
        ItemStack armor = armorParts[r];
        if(armor == null){
            Message.ERROR.send(killer, "Quem sabe na próxima você ganha uma parte de armadura de ferro!");
            return;
        }
        killer.getInventory().addItem(armor);
        Message.GOOD.send(killer, "Você ganhou uma parte de armadura de ferro! Parabéns!");
        killer.playSound(killer.getLocation(), Sound.NOTE_PLING, 1F, 1F);
    }
}
