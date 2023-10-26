package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dark.Utils.DarkUtils;
import me.dark.Utils.MinerListener;
import me.dark.kit.Kit;

public class Miner extends Kit{
	public static ItemStack pickaxe = DarkUtils.create_item(Material.STONE_PICKAXE, Enchantment.DIG_SPEED, 2, "§3Miner", 1, 0, null);
	public Miner() {
		super("Miner", Material.STONE_PICKAXE, 
				new ItemStack[] { null },
				Arrays.asList(""));
		pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		setItems(new ItemStack[] { pickaxe, new ItemStack(Material.APPLE, 3) });
		new MinerListener();
	}
	@EventHandler
	public void inte(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (hasAbility(p)) {
			if (p.getItemInHand() != null) {
				if (p.getItemInHand().getType() == Material.APPLE) {
					e.setCancelled(true);
					boolean used = false;
					for (PotionEffect pot : p.getActivePotionEffects()) {
						if (pot.getType() == PotionEffectType.FAST_DIGGING) {
							used = true;
						}
					}
					if (!used) {
						p.setFoodLevel(p.getFoodLevel()+3);
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 30*20, 1));
						if (p.getItemInHand().getAmount() > 1) {
							p.setItemInHand(new ItemStack(Material.APPLE, p.getItemInHand().getAmount()-1));
							p.updateInventory();
						} else {
							p.setItemInHand(new ItemStack(Material.AIR));
							p.updateInventory();
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	  public void onMinerBreakOre(BlockBreakEvent e) {
	    Player player = e.getPlayer();
	    if (hasAbility(player)) {
	      Block b = e.getBlock();

	      if (MinerListener.list.contains(Integer.valueOf(b.getTypeId()))) {
	        List<Block> blocks = MinerListener.MinerConnect.getConnectedBlocks(b);
	        for (int i = 0; i < blocks.size(); i++)
	          ((Block)blocks.get(i)).breakNaturally();
	      }
	    }
	  }
}
