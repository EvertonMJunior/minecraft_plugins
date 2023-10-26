package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Ninja extends Kit {
	public Ninja() {
		super("Ninja", Material.DIAMOND_SWORD, 
				new ItemStack[] { null },
				Arrays.asList("§7Ao Hitar algum Player, pressionando,",
						      "§7a tecla SHIFT você será teletransportado",
						      "§7para as costas de seu adversário!"));
	}
	  public HashMap<Player, Player> a = new HashMap<>();

	  @EventHandler
	  public void a(EntityDamageByEntityEvent e) {
			if (Main.estado == GameState.PREGAME) return;
	    if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof Player))){
	      final Player p = (Player)e.getDamager();
	      Player p2 = (Player)e.getEntity();
	      if (hasAbility(p)) {
	        this.a.put(p, p2);
	      }
	    }
	  }
	  

	  @EventHandler
	  public void a(PlayerToggleSneakEvent e) {
			if (Main.estado == GameState.PREGAME) return;
	    Player p = e.getPlayer();
	    if ((e.isSneaking()) && (hasAbility(p)) && (this.a.containsKey(p))){
	      Player p2 = (Player)this.a.get(p);
	      if ((p2 != null) && (!p2.isDead())) {
	        if (!hasCooldown(p)){
	          if (p.getLocation().distance(p2.getLocation()) < 50.0D) {
	            p.teleport(p2.getLocation());
	            p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1F);
	            addCooldown(p, 5);
	          }
	        }else {
	          mensagemcooldown(p);
	        }
	      }
	    }
	  }
	}