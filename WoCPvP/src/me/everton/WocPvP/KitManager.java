package me.everton.WocPvP;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class KitManager {

	public static void resetKit(Player p, Boolean msg) {
		Main.kits.remove(p);
		Main.switcher.remove(p);
		Main.stomper.remove(p);
		Main.pvp.remove(p);
		Main.grappler.remove(p);
		Main.endermage.remove(p);
		Main.infernor.remove(p);
		Main.kangaroo.remove(p);
		Main.usandokit.remove(p);
		if (msg) {
			p.sendMessage("§a§lSeu kit foi resetado!");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
		}
	}

	public static void kitSwitcher(Player p) {
		if (p.hasPermission("kit.switcher")) {
			darKit(p, "Switcher", new ItemStack[] { new ItemStack(
					Material.SNOW_BALL, 16) }, Main.switcher, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitKangaroo(Player p) {
		if (p.hasPermission("kit.kangaroo")) {
			darKit(p, "Kangaroo", new ItemStack[] { new ItemStack(
					Material.FIREWORK) }, Main.kangaroo, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitGladiator(Player p) {
		if (p.hasPermission("kit.gladiator")) {
			darKit(p, "Gladiator", new ItemStack[] { new ItemStack(
					Material.IRON_FENCE) }, Main.gladiator, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void kitGrappler(Player p) {
		if (p.hasPermission("kit.grappler")) {
			darKit(p, "Grappler", new ItemStack[] { new ItemStack(
					Material.LEASH) }, Main.grappler, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void kitStomper(Player p) {
		if (p.hasPermission("kit.stomper")) {
			darKit(p, "Stomper", null, Main.stomper, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void kitPvP(Player p) {
		if (p.hasPermission("kit.pvp")) {
			darKit(p, "PvP", null, Main.pvp, true);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void kitInfernor(Player p) {
		if (p.hasPermission("kit.infernor")) {
			darKit(p, "Infernor", new ItemStack[] { new ItemStack(
					Material.NETHER_FENCE) }, Main.infernor, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitHulk(Player p) {
		if (p.hasPermission("kit.hulk")) {
			darKit(p, "Hulk", new ItemStack[] { new ItemStack(
					Material.SADDLE) }, Main.hulk, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void kitEndermage(Player p) {
		if (p.hasPermission("kit.endermage")) {
			ItemStack bow = new ItemStack(Material.PORTAL);
			ItemMeta bowmeta = bow.getItemMeta();
			bowmeta.setDisplayName("§5Portal de Endermage");
			bow.setItemMeta(bowmeta);
			darKit(p, "Endermage", new ItemStack[] { bow }, Main.endermage,
					false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja e comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	public static void barKit(Player p, String kit) {
		BarAPI.setMessage(p, "§3Você escolheu o kit§2 " + kit, 5);
	}

	public static void darKit(Player p, String kit, ItemStack[] outros,
			ArrayList<Player> habilidade, Boolean kpvp) {
		for (PotionEffect efeito : p.getActivePotionEffects()) {
			p.removePotionEffect(efeito.getType());
		}

		p.getInventory().clear();
		p.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		if (!kpvp) {
			p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
		} else {
			p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
			p.getInventory().getItem(0)
					.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		}

		if (outros != null) {
			p.getInventory().addItem(outros);
		}

		for (int i = 0; i < 36; i++) {
			p.getInventory().addItem(
					new ItemStack[] { new ItemStack(Material.MUSHROOM_SOUP) });
		}
		p.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64));
		p.getInventory()
				.setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));
		p.getInventory().setItem(16, new ItemStack(Material.BOWL, 64));
		Main.usandokit.add(p);
		habilidade.add(p);
		barKit(p, kit);
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 4.0F, 4.0F);
	}

	public static String kitUsando(Player p) {
		if (Main.endermage.contains(p)) {
			return "Endermage";
		} else if (Main.grappler.contains(p)) {
			return "Grappler";
		} else if (Main.stomper.contains(p)) {
			return "Stomper";
		} else if (Main.switcher.contains(p)) {
			return "Switcher";
		} else if (Main.pvp.contains(p)) {
			return "PvP";
		} else if (Main.infernor.contains(p)) {
			return "Infernor";
		} else if (Main.kangaroo.contains(p)) {
			return "Kangaroo";
		} else if (Main.hulk.contains(p)) {
			return "Hulk";
		} else if (Main.gladiator.contains(p)) {
			return "Gladiator";
		} else {
			return "Sem kit";
		}
	}
	
	@SuppressWarnings("deprecation")
	public void aoDropar(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		Material m = e.getItemDrop().getItemStack().getType();
		if(Main.endermage.contains(p)){
			if(m == Material.PORTAL){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.grappler.contains(p)){
			if(m == Material.LEASH){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.switcher.contains(p)){
			if(m == Material.SNOW_BALL){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.infernor.contains(p)){
			if(m == Material.NETHER_FENCE){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.kangaroo.contains(p)){
			if(m == Material.FIREWORK){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.hulk.contains(p)){
			if(m == Material.SADDLE){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
		
		if(Main.gladiator.contains(p)){
			if(m == Material.IRON_FENCE){
				e.setCancelled(true);
				p.updateInventory();
			}
		}
	}
}
