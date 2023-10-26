package net.goodcraft.hardcoregames.kit.habilidades;

import net.goodcraft.hardcoregames.Main;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.Kit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class Ninja extends Kit {
    public Ninja() {
        super("Ninja", Material.NETHER_STAR,
                new ItemStack[]{null},
                Arrays.asList("§7Ao hitar algum jogador, pressionando,",
                        "§7a tecla SHIFT você será teletransportado",
                        "§7para as costas de seu adversário!"));
    }

    public HashMap<Player, Player> a = new HashMap<>();

    @EventHandler
    public void a(EntityDamageByEntityEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof Player))) {
            final Player p = (Player) e.getDamager();
            Player p2 = (Player) e.getEntity();
            if (hasAbility(p)) {
                this.a.put(p, p2);
            }
        }
    }


    @EventHandler
    public void a(PlayerToggleSneakEvent e) {
        if (Main.getMg().getGameState() == GameState.PREGAME) return;
        Player p = e.getPlayer();
        if ((e.isSneaking()) && (hasAbility(p)) && (this.a.containsKey(p))) {
            Player p2 = (Player) this.a.get(p);
            if ((p2 != null) && (!p2.isDead())) {
                if (!hasCooldown(p)) {
                    if (p.getLocation().distance(p2.getLocation()) < 50.0D) {
                        p.teleport(p2.getLocation());
                        p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1F);
                        addCooldown(p, 5);
                    }
                } else {
                    mensagemcooldown(p);
                }
            }
        }
    }
}