package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.api.ActionBar;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class Surprise extends Kit {
    public Surprise() {
        super("Surprise", Material.CAKE,
                new ItemStack[]{null},
                Arrays.asList("§7Com o kit Surprise, você ganhará algum",
                        "§7kit aleatório no início da partida."));
    }

    private void event(Player p){
        int random = new Random().nextInt(Kit.kits.size());
        Kit k = Kit.kits.get(random);
        for (int i = 0; i < 20; i++) {
            if (k == this) {
                random = new Random().nextInt(Kit.kits.size());
                k = Kit.kits.get(random);
            }
        }
        for (int i = 0; i < 30; i++) {
            if (!Main.getMg().getDisabledKits().contains(k)) {
                random = new Random().nextInt(Kit.kits.size());
                k = Kit.kits.get(random);
            }
        }
        removePlayer(p);
        k.addPlayer(p);
        ActionBar.GOOD.send(p, "Seu kit surpresa é o kit " + k.toString() + "!");
        if (k.getItems() != null) {
            for (ItemStack items : k.getItems()) {
                if (items != null) {
                    p.getInventory().addItem(items);
                }
            }
        }
    }

    @Override
    public void addPlayer(Player p) {
        super.addPlayer(p);
        if (Main.getMg().getGameState() != GameState.PREGAME) {
            event(p);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStartGame() {
        super.onStartGame();
        Bukkit.getOnlinePlayers().stream().filter(this::hasAbility).forEach(this::event);
    }

}
