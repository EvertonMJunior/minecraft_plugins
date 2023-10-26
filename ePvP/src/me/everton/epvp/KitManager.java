package me.everton.epvp;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;
import me.everton.epvp.Kits.Habilidades.Avatar;
import me.everton.epvp.Kits.Habilidades.C4;
import me.everton.epvp.Kits.Habilidades.Flash;
import me.everton.epvp.Kits.Habilidades.Ninja;
import me.everton.epvp.Kits.Habilidades.Sniper;
import me.everton.epvp.Kits.Habilidades.Sombra;
import me.everton.epvp.Kits.Habilidades.Terrorista;
import me.everton.epvp.Listeners.PlayerReceiveKitEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class KitManager implements Listener{

	public static void resetKit(Player p, Boolean msg) {
		Main.kits.remove(p.getName());
		Main.pvp.remove(p.getName());
		Main.grappler.remove(p.getName());
		Main.legolas.remove(p.getName());
		Main.stomper.remove(p.getName());
		Main.quickdropper.remove(p.getName());
		Main.avatar.remove(p.getName());
		Main.sniper.remove(p.getName());
		Main.ninja.remove(p.getName());
		Main.endermage.remove(p.getName());
		Main.c4.remove(p.getName());
		Main.kangaroo.remove(p.getName());
		Main.sombra.remove(p.getName());
		Main.cactus.remove(p.getName());
		Main.poseidon.remove(p.getName());
		Main.terrorista.remove(p.getName());
		Main.usandokit.remove(p.getName());
		
		
		//COOLDOWNS
		Avatar.cd.remove(p.getName());
		C4.cd.remove(p.getName());
		Terrorista.cd.remove(p.getName());
		Sombra.cd.remove(p.getName());
		Sniper.cd.remove(p.getName());
		Flash.cd.remove(p.getName());
		Ninja.cd.remove(p.getName());
		
		
		if (msg) {
			p.sendMessage("§7[§a!§7] Seu kit foi resetado!");
			p.playSound(p.getLocation(), Sound.ANVIL_LAND, 4.0F, 4.0F);
		}
	}

	/*public static void kitSwitcher(Player p) {
		if (p.hasPermission("kit.switcher")) {
			darKit(p, "Switcher", new ItemStack[] { new ItemStack(
					Material.SNOW_BALL, 16) }, Main.switcher, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitKangaroo(Player p) {
		if (p.hasPermission("kit.kangaroo")) {
			darKit(p, "Kangaroo", new ItemStack[] { new ItemStack(
					Material.FIREWORK) }, Main.kangaroo, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitGladiator(Player p) {
		if (p.hasPermission("kit.gladiator")) {
			darKit(p, "Gladiator", new ItemStack[] { new ItemStack(
					Material.IRON_FENCE) }, Main.gladiator, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}*/

	public static void kitGrappler(Player p) {
		if (p.hasPermission("kit.grappler")) {
			darKit(p, "Grappler", new ItemStack[] { API.item(Material.LEASH, 1, "§cGrappler §7(Clique)", new String[] {"§bAnde rapidamente com sua corda!"}) }, Main.grappler, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitKangaroo(Player p) {
		if (p.hasPermission("kit.kangaroo")) {
			darKit(p, "Kangaroo", new ItemStack[] { API.item(Material.FIREWORK, 1, "§cKangaroo §7(Clique)", new String[] {"§bAnde rapidamente dando jumps!"}) }, Main.kangaroo, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitAvatar(Player p) {
		if (p.hasPermission("kit.avatar")) {
			darKit(p, "Avatar", new ItemStack[] { API.item(Material.BEACON, 1, "§2Avatar §7(Clique)", new String[] {"§bPoderes do Avatar!"}) }, Main.avatar, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitC4(Player p) {
		if (p.hasPermission("kit.c4")) {
			darKit(p, "C4", new ItemStack[] { API.item(Material.SLIME_BALL, 1, "§2C4 §7(Clique)", new String[] {"§bExploda todos!"}) }, Main.c4, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitStomper(Player p) {
		if (p.hasPermission("kit.stomper")) {
			darKit(p, "Stomper", null, Main.stomper, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitQuickdropper(Player p) {
		if (p.hasPermission("kit.quickdropper")) {
			darKit(p, "Quickdropper", null, Main.quickdropper, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitSniper(Player p) {
		if (p.hasPermission("kit.sniper")) {
			darKit(p, "Sniper", new ItemStack[] {API.item(Material.BLAZE_ROD, 1, "§6§lSniper", new String[] {"§bCom sua BlazeRod, acerte flechas onde mirar!"})}, Main.sniper, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitPoseidon(Player p) {
		if (p.hasPermission("kit.poseidon")) {
			darKit(p, "Poseidon", new ItemStack[] {API.item(Material.WATER_BUCKET, 1, "§9§lPoseidon", new String[] {"§bJogue águas e ao passar nelas, ganhe força e speed!"})}, Main.poseidon, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitLegolas(Player p) {
		if (p.hasPermission("kit.legolas")) {
			ItemStack arco = API.item(Material.BOW, 1, "§cLegolas §7(Clique Direito)", new String[] {"§bCom seu arco, dê HeadShots e mate todos!"});
			arco.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			arco.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
			
			darKit(p, "Legolas", new ItemStack[] { arco, new ItemStack(Material.ARROW) }, Main.legolas, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	/*public static void kitStomper(Player p) {
		if (p.hasPermission("kit.stomper")) {
			darKit(p, "Stomper", null, Main.stomper, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}*/

	public static void kitPvP(Player p) {
		if (p.hasPermission("kit.pvp")) {
			darKit(p, "PvP", null, Main.pvp, true);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitNinja(Player p) {
		if (p.hasPermission("kit.ninja")) {
			darKit(p, "Ninja", null, Main.ninja, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitFlash(Player p) {
		if (p.hasPermission("kit.flash")) {
			darKit(p, "Flash", new ItemStack[] {API.item(Material.REDSTONE_TORCH_ON, 1, "§c§lFlash §7(Clique direito)", new String[] {"§bTeleporte-se com sua tocha!"})}, Main.flash, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitTerrorista(Player p) {
		if (p.hasPermission("kit.terrorista")) {
			darKit(p, "Terrorista", new ItemStack[] {API.item(Material.MAGMA_CREAM, 1, "§4§lTerrorista §7(Clique direito)", new String[] {"§bDê um super pulo e quando cair, faça uma explosão!"})}, Main.terrorista, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitSombra(Player p) {
		if (p.hasPermission("kit.sombra")) {
			darKit(p, "Sombra", new ItemStack[] {API.item(Material.WOOL, 1, "§0§lSombra §7(Clique direito)", new String[] {"§bDê um super pulo e quando cair, faça uma explosão!"}, 15)}, Main.sombra, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitCactus(Player p) {
		if (p.hasPermission("kit.cactus")) {
			darKit(p, "Cactus", null, Main.cactus, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitVacuum(Player p) {
		if (p.hasPermission("kit.vacuum")) {
			darKit(p, "Vacuum", new ItemStack[] {API.item(Material.EYE_OF_ENDER, 1, "§1§lVacuum §7(Clique direito)", new String[] {"§bPuxe tudo num raio de 15 blocos!"})}, Main.vacuum, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitEndermage(Player p) {
		if (p.hasPermission("kit.endermage")) {
			darKit(p, "Endermage", new ItemStack[] {API.item(Material.PORTAL, 1, "§5Endermage §7(Clique direito)")}, Main.endermage, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}

	/*public static void kitInfernor(Player p) {
		if (p.hasPermission("kit.infernor")) {
			darKit(p, "Infernor", new ItemStack[] { new ItemStack(
					Material.NETHER_FENCE) }, Main.infernor, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}
	
	public static void kitHulk(Player p) {
		if (p.hasPermission("kit.hulk")) {
			darKit(p, "Hulk", new ItemStack[] { new ItemStack(
					Material.SADDLE) }, Main.hulk, false);
		} else {
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
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
			p.sendMessage("§cVocê nao possui este kit! Compre-o utilizando /loja ou comprando Vip ou o Kit caso ele esteja a venda!");
		}
	}*/

	public static void barKit(Player p, String kit) {
		BarAPI.setMessage(p, "§3Você escolheu o kit§2 " + kit, 5);
	}

	public static void darKit(Player p, String kit, ItemStack[] outros,
			ArrayList<String> habilidade, Boolean kpvp) {
		
		if(Main.usandokit.contains(p.getName())){
			p.closeInventory();
			p.sendMessage("§cVocê ja esta usando um kit!");
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 10.0F, 10.0F);
		} else {
			Bukkit.getServer().getPluginManager().callEvent(new PlayerReceiveKitEvent(p, kit));
		for (PotionEffect efeito : p.getActivePotionEffects()) {
			p.removePotionEffect(efeito.getType());
		}

		p.getInventory().clear();
		p.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		p.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		if (!kpvp) {
			p.getInventory().setItem(0, new ItemStack(Material.WOOD_SWORD));
		} else {
			p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
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
		Main.usandokit.add(p.getName());
		habilidade.add(p.getName());
		barKit(p, kit);
		p.playSound(p.getLocation(), Sound.NOTE_PLING, 4.0F, 4.0F);		
	}
	}

	public static String kitUsando(Player p) {
		/*if (Main.endermage.contains(p.getName())) {
			return "Endermage";
		} else if (Main.stomper.contains(p.getName())) {
			return "Stomper";
		} else if (Main.switcher.contains(p.getName())) {
			return "Switcher";
		} else*/ if (Main.pvp.contains(p.getName())) {
			return "PvP";
			/* else if (Main.infernor.contains(p.getName())) {
			return "Infernor";
		} else if (Main.kangaroo.contains(p.getName())) {
			return "Kangaroo";
		} else if (Main.hulk.contains(p.getName())) {
			return "Hulk";
		} else if (Main.gladiator.contains(p.getName())) {
			return "Gladiator";*/
		}else if (Main.grappler.contains(p.getName())) {
			return "Grappler";
		}else if (Main.legolas.contains(p.getName())) {
			return "Legolas";
		}else if (Main.avatar.contains(p.getName())) {
			return "Avatar";
		}else if (Main.sniper.contains(p.getName())) {
			return "Sniper";
		}else if (Main.kangaroo.contains(p.getName())) {
			return "Kangaroo";
		}else if (Main.quickdropper.contains(p.getName())) {
			return "Quickdropper";
		}else if (Main.stomper.contains(p.getName())) {
			return "Stomper";
		}else if (Main.ninja.contains(p.getName())) {
			return "Ninja";
		}else if (Main.c4.contains(p.getName())) {
			return "C4";
		}else if (Main.flash.contains(p.getName())) {
			return "Flash";
		}else if (Main.cactus.contains(p.getName())) {
			return "Cactus";
		}else if (Main.poseidon.contains(p.getName())) {
			return "Poseidon";
		}else if (Main.sombra.contains(p.getName())) {
			return "Sombra";
		}else if (Main.terrorista.contains(p.getName())) {
			return "Terrorista";
		}else if (Main.vacuum.contains(p.getName())) {
			return "Vacuum";
		}else if (Main.endermage.contains(p.getName())) {
			return "Endermage";
		} else {
			return "Sem kit";
		}
	}
}
