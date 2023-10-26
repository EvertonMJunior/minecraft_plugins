package me.everton.pvp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.confuser.barapi.BarAPI;
import me.everton.pvp.OptionsManager.DataType;
import me.everton.pvp.cmds.Admin;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;
import me.everton.pvp.listeners.Dano.SwordDamage;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.ProtocolInjector;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;

public class API {
	@SuppressWarnings("deprecation")
	public static double getDamage(Player p) {
		double dano = 1D;
		ItemStack item = p.getItemInHand();
		if(item.getType() == Material.WOOD_SWORD) {
			dano = SwordDamage.WOOD.getDamage();
		}
		
		if(item.getType() == Material.STONE_SWORD) {
			dano = SwordDamage.STONE.getDamage();
		}
		
		if(item.getType() == Material.GOLD_SWORD) {
			dano = SwordDamage.GOLD.getDamage();
		}
		
		if(item.getType() == Material.IRON_SWORD) {
			dano = SwordDamage.IRON.getDamage();
		}
		
		if(item.getType() == Material.DIAMOND_SWORD) {
			dano = SwordDamage.DIAMOND.getDamage();
		}
		
		for(PotionEffect pot : p.getActivePotionEffects()) {
			if(pot.getType() == PotionEffectType.INCREASE_DAMAGE) {
				dano += pot.getAmplifier() / 2;
			}
		}
		if(item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			dano += item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) / 2;
		}
		if(!p.isOnGround()) {
			dano += 0.5D;
		}
		
		return dano;
	}
	
	public static boolean isInvFull(Player p) {
		int comItem = 0;
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			if(p.getInventory().getItem(i) != null) {
				comItem++;
			}
		}
		if(comItem == p.getInventory().getSize()) {
			return true;
		}
		return false;
	}
	
	public static int getHowMuchClearSlots(Player p) {
		int slots = 0;
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			if(p.getInventory().getItem(i) == null) {
				slots++;
			}
		}
		return slots;
	}
	
	public static int getAmount(Player p, Material m) {
		int itens = 0;
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			try {
				if(p.getInventory().getItem(i).getType() == m) {
					itens++;
				}
			} catch(Exception e){
				
			}
		}
		return itens;
	}
	
	public static void fillVillagerInv(Inventory inv) {
		for(int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 7));
		}
		
		if(inv.getSize() == 27) {
			inv.setItem(0, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(18, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(22, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(26, API.item(Material.STAINED_GLASS_PANE, 1, " "));
		} else if(inv.getSize() == 54) {
			inv.setItem(1, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(7, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(9, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(17, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(36, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(44, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(46, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(49, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(52, API.item(Material.STAINED_GLASS_PANE, 1, " "));
		} else if(inv.getSize() >= 27){
			inv.setItem(0, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 1, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 5, API.item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 9, API.item(Material.STAINED_GLASS_PANE, 1, " "));
		}
	}
	
	public static ItemStack item(Material m) {
		return new ItemStack(m);
	}

	public static ItemStack item(Material m, int q) {
		return new ItemStack(m, q);
	}
	
	public static ItemStack item(Material m, int q, String n) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack item(Material m, int q, String n, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	public static ItemStack item(Material m, int q, String n, Enchantment en, int level) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		it.addEnchantment(en, level);
		return it;
	}

	public static ItemStack item(Material m, int q, String n, Enchantment en, int level, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		it.addEnchantment(en, level);
		return it;
	}

	public static ItemStack item(Material m, int q, String n, String[] l) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	public static ItemStack item(Material m, int q, String n, String[] l, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack item(PotionType pot, int q, String n, String[] l, boolean splash, boolean extended, int level) {
		Potion pot1 = new Potion(pot, level);
		if(!pot.name().contains("INSTANT")) {
			pot1.setHasExtendedDuration(extended);
		}
		pot1.setSplash(splash);
		ItemStack it = pot1.toItemStack(5);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack leatherArmor(Material m, String n, Color cor) {
		if(!m.name().contains("LEATHER_")) {
			return null;
		}
		ItemStack it = new ItemStack(m);
		LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
		me.setColor(cor);
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack leatherArmor(Material m, int q, String n, String[] l, Color cor) {
		if(!m.name().contains("LEATHER_")) {
			return null;
		}
		ItemStack it = new ItemStack(m, q);
		LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
		me.setColor(cor);
		me.setDisplayName(n);
		me.setLore(Arrays.asList(l));
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack getHead(String name, int q, String n) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta me = (SkullMeta) it.getItemMeta();
		me.setDisplayName(n);
		me.setOwner(name);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack getHead(String name, int q, String n, String[] l) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta me = (SkullMeta) it.getItemMeta();
		me.setDisplayName(n);
		me.setOwner(name);
		me.setLore(Arrays.asList(l));
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack getSoup() {
		return item(Material.MUSHROOM_SOUP, 1, "§6Sopa", new String[] {"§7Esta sopa regenera 3.5", "§7coraçoes de sua vida!"});
	}
	
	public static ItemStack getSword(KitType kit) {
		if(kit == KitType.VIKING) {
			return API.item(Material.STONE_AXE, 1, "§cMachado de Viking");
		}
		if(kit.hasSharpness()) {
			return API.item(Material.STONE_SWORD, 1, "§cEspada", Enchantment.DAMAGE_ALL, 1);
		} else {
			return API.item(Material.STONE_SWORD, 1, "§cEspada");
		}
	}
	
	public static ItemStack getSword(Player p) {
		KitType kit = KitManager.getKit(p);
		if(kit == KitType.VIKING) {
			return API.item(Material.STONE_AXE, 1, "§cMachado de Viking");
		}
		if(OptionsManager.getData(p.getName(), DataType.SWORD).equalsIgnoreCase("stone")) {
			if(kit.hasSharpness()) {
				return API.item(Material.STONE_SWORD, 1, "§cEspada", Enchantment.DAMAGE_ALL, 1);
			} else {
				return API.item(Material.STONE_SWORD, 1, "§cEspada");
			}
		} else {
			if(kit.hasSharpness()) {
				return API.item(Material.WOOD_SWORD, 1, "§cEspada", Enchantment.DAMAGE_ALL, 2);
			} else {
				return API.item(Material.WOOD_SWORD, 1, "§cEspada", Enchantment.DAMAGE_ALL, 1);
			}
		}
	}
	
	public static ItemStack getCocoa() {
		return API.item(Material.INK_SACK, 64,
						"§7Cacau", 3);
	}
	
	public static ItemStack getRedMushroom() {
		return API.item(Material.RED_MUSHROOM, 64, "§7Cogumelo");
	}
	
	public static ItemStack getBrownMushroom() {
		return API.item(Material.BROWN_MUSHROOM, 64,
						"§7Cogumelo");
	}
	
	public static ItemStack getBowl() {
		return API.item(Material.BOWL, 64, "§7Tigela");
	}
	
	public static int getKits(Player p, boolean seusKits) {
		int kits = 0;
		for(KitType kit : KitType.values()) {
			if(seusKits) {
				if(p.hasPermission("kit." + kit.name().toLowerCase())) {
					if(kit != KitType.NONE) {
						kits++;
					}
				}
			} else {
				if(!p.hasPermission("kit." + kit.name().toLowerCase())) {
					if(kit != KitType.NONE) {
						kits++;
					}
				}
			}
		}
		return kits;
	}
	
	public static int getPing(Player p) {
		PlayerConnection pc = ((CraftPlayer)p).getHandle().playerConnection;
		return pc.player.ping;
	}

	public static void itensIniciais(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(1, item(Material.ENDER_CHEST, 1, "§c§lKits §7(Clique Direito)", new String[] {"§7Você possui " + getKits(p, true) + " kits."}));
		p.getInventory().setItem(4, item(Material.PAPER, 1, "§3§lWarps §7(Clique Direito)", new String[] {"§7Clique aqui para ir até", "§7as Warps que possuímos!"}));
		p.getInventory().setItem(7, item(Material.NETHER_STAR, 1, "§5§lPreferências §7(Clique Direito)", new String[] {"§7Clique aqui para customizar", "§7alguns aspectos de sua Gameplay!"}));
	}
	
	public static void displayHolo(Player p, String msg) {
		
	}
	
	public static void startCd(final Player p, final int coolDown, int startDelay, final HashMap<String, Integer> cd, final String msgFinalCd) {
		final KitType pKit = KitManager.getKit(p);
		new BukkitRunnable() {
			int time = coolDown;
			@Override
			public void run() {
				if(pKit != KitManager.getKit(p)) {
					cd.remove(p.getName());
					cancel();
					return;
				}
				if(!p.isOnline()) {
					cd.remove(p.getName());
					cancel();
					return;
				}
				if(time > 0) {
					cd.put(p.getName(), time);
					time--;	
				} else {
					if(msgFinalCd != null) {
						p.sendMessage(msgFinalCd);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
					}
					cd.remove(p.getName());
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), startDelay * 1L, 20L);
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Player> getGamers() {
		ArrayList<Player> jogadores = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!SpawnProtection.hasProtection(p) && !Admin.admins.contains(p.getName())) {
				jogadores.add(p);
			}
		}
		return jogadores;
	}
	
	public static String secToString(int i) {
		if (i >= 86400) {
			int d = (int) TimeUnit.SECONDS.toDays(i);
			int h = (int) (TimeUnit.SECONDS.toHours(i) - (d * 24));
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(d) + " dia(s), " + doisDigitos(h) + " hora(s), "
					+ doisDigitos(m) + " minuto(s) e " + doisDigitos(s)
					+ " segundo(s)";
		} else if (i >= 3600) {
			int h = (int) TimeUnit.SECONDS.toHours(i);
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));

			return doisDigitos(h) + " hora(s), " + doisDigitos(m)
					+ " minuto(s) e " + doisDigitos(s) + " segundo(s)";
		} else {
			int m = (int) TimeUnit.SECONDS.toMinutes(i);
			int s = i - (m * 60);
			if ((m == 0) && (s != 0)) {
				if (s > 1) {
					return doisDigitos(s) + " segundos";
				} else {
					return doisDigitos(s) + " segundo";
				}
			} else if ((m != 0) && (s == 0)) {
				if (m > 1) {
					return doisDigitos(m) + " minutos";
				} else {
					return doisDigitos(m) + " minuto";
				}
			} else {
				if ((s > 1) && (m > 1)) {
					return doisDigitos(m) + " minutos e " + s + " segundos";
				} else if ((s > 1) && (!(m > 1))) {
					return doisDigitos(m) + " minuto e " + s + " segundos";
				} else if ((m > 1) && (!(s > 1))) {
					return doisDigitos(m) + " minutos e " + s + " segundo";
				}
			}
			return doisDigitos(s) + " minutos e " + s + " segundos";
		}
	}
	
	public static String doisDigitos(int number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public static void deleteHashMapKey(HashMap<?, ?> h, String v) {
		if (h.containsKey(v)) {
			h.remove(v);
		}
	}

	public static void deleteArrayList(ArrayList<?> a, String v) {
		if (a.contains(v)) {
			a.remove(v);
		}
	}

	public static String format(String s) {
		String sr = s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
		return sr;
	}
	
	public static void sendTitle(Player p, String t, String st, int fi, int s, int fo) {
		if(isOneEight(p)) {
			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			IChatBaseComponent title = ChatSerializer.a("{'text': '"
					+ t + "'}");
			IChatBaseComponent subtitle = ChatSerializer.a("{'text': '"
					+ st + "'}");
			
			connection.sendPacket(new ProtocolInjector.PacketTitle(Action.TIMES, fi * 20, s * 20, fo * 20));
			if(t != null) {
				connection.sendPacket(new ProtocolInjector.PacketTitle(Action.TITLE, title));
			}
			if(st != null) {
				connection.sendPacket(new ProtocolInjector.PacketTitle(Action.SUBTITLE, subtitle));
			}
		} else {
			BarAPI.setMessage(p, t + " §r- " + st, fi + s + fo);
		}
	}
	
	public static void sendActionBar(Player player, String message) {
		if (isOneEight(player)) {
			CraftPlayer cp = (CraftPlayer) player;
			IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message
					+ "\"}");
			PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
			((CraftPlayer) cp).getHandle().playerConnection.sendPacket(ppoc);
		} else {
			BarAPI.setMessage(player, message, 3);
		}
	}

	public static void setHeaderFooter(Player p, String headerT, String footerT) {
		if (isOneEight(p)) {
			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			IChatBaseComponent headerc = ChatSerializer.a("{'text': '"
					+ headerT + "'}");
			IChatBaseComponent footerc = ChatSerializer.a("{'text': '"
					+ footerT + "'}");
			connection.sendPacket(new ProtocolInjector.PacketTabHeader(headerc,
					footerc));
		}
	}
	
	public static boolean isOneEight(Player player) {
		int version = 0;
		try {
			Object handle = getHandle(player);
			Object connection = getField(handle.getClass(), "playerConnection")
					.get(handle);
			Object networkManager = getValue("networkManager", connection);
			version = (Integer) getMethod("getVersion",
					networkManager.getClass()).invoke(networkManager);
			if (version >= 47) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Field getField(String name, Class<?> clazz) throws Exception {
		return clazz.getDeclaredField(name);
	}

	private static Object getValue(String name, Object obj) throws Exception {
		Field f = getField(name, obj.getClass());
		f.setAccessible(true);
		return f.get(obj);
	}

	private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();

	private static Class<?> getPrimitiveType(Class<?> clazz) {
		return CORRESPONDING_TYPES.containsKey(clazz) ? CORRESPONDING_TYPES
				.get(clazz) : clazz;
	}

	private static Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
		int a = classes != null ? classes.length : 0;
		Class<?>[] types = new Class<?>[a];
		for (int i = 0; i < a; i++)
			types[i] = getPrimitiveType(classes[i]);
		return types;
	}

	private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
		if (a.length != o.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i]))
				return false;
		return true;
	}

	private static Object getHandle(Object obj) {
		try {
			return getMethod("getHandle", obj.getClass()).invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Method getMethod(String name, Class<?> clazz,
			Class<?>... paramTypes) {
		Class<?>[] t = toPrimitiveTypeArray(paramTypes);
		for (Method m : clazz.getMethods()) {
			Class<?>[] types = toPrimitiveTypeArray(m.getParameterTypes());
			if (m.getName().equals(name) && equalsTypeArray(types, t))
				return m;
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static void broadcastMessage(String message, String permission) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.hasPermission(permission)) {
				p.sendMessage(message);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void broadcastMessage(String message) {
		for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(message);
		}
	}
	
	public static String getSpaces(String forGetSpaces, int maxSpaces) {
		int esp = maxSpaces - forGetSpaces.length();
		String espacos = "";
		for(int i = 0; i < esp; i++) {
			espacos += " ";
		}
		return espacos;
	}
}
