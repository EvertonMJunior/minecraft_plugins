package me.everton.hg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import me.confuser.barapi.BarAPI;
import me.everton.hg.Cmds.Admin;
import me.everton.hg.kits.KitManager;
import me.everton.hg.kits.KitType;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.spigotmc.ProtocolInjector;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;

public class API {

	public static ArrayList<String> playerKits(Player p) {
		ArrayList<String> kitsP = new ArrayList<String>();
		for (KitType kit : KitType.values()) {
			if (p.hasPermission("kit." + kit.name().toLowerCase())) {
				kitsP.add(kit.name().toLowerCase());
			}
		}
		return kitsP;
	}

	@SuppressWarnings("deprecation")
	public static void addRecipes() {
		ItemStack sopa = item(Material.MUSHROOM_SOUP, 1, "§5Sopa de Cogumelos");
		ShapelessRecipe sr = new ShapelessRecipe(sopa);
		sr.addIngredient(Material.RED_MUSHROOM);
		sr.addIngredient(Material.BROWN_MUSHROOM);
		sr.addIngredient(Material.BOWL);
		Bukkit.addRecipe(sr);

		ItemStack sopac = item(Material.MUSHROOM_SOUP, 1, "§6Sopa de Nescau");
		ShapelessRecipe sc = new ShapelessRecipe(sopac);
		sc.addIngredient(1, Material.INK_SACK, 3);
		sc.addIngredient(Material.BOWL);
		Bukkit.addRecipe(sc);

		ItemStack sopaf = item(Material.MUSHROOM_SOUP, 1, "§cSopa de Flor");
		ShapelessRecipe sf = new ShapelessRecipe(sopaf);
		sf.addIngredient(Material.RED_ROSE);
		sf.addIngredient(Material.YELLOW_FLOWER);
		sf.addIngredient(Material.BOWL);
		Bukkit.addRecipe(sf);

		ItemStack sopaaf = item(Material.MUSHROOM_SOUP, 1, "§bSopa Divina");
		ShapelessRecipe sa = new ShapelessRecipe(sopaaf);
		sa.addIngredient(Material.PUMPKIN_SEEDS);
		sa.addIngredient(Material.BOWL);
		Bukkit.addRecipe(sa);

		ItemStack sopaca = item(Material.MUSHROOM_SOUP, 1, "§2Sopa de Cactus");
		ShapelessRecipe sca = new ShapelessRecipe(sopaca);
		sca.addIngredient(Material.CACTUS);
		sca.addIngredient(Material.BOWL);
		Bukkit.addRecipe(sca);
	}

	@SuppressWarnings("deprecation")
	public static void respawnPlayer(Player p) {
		p.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
		p.getPlayer().getInventory().clear();
		p.getPlayer().setItemOnCursor(new ItemStack(0));
		p.teleport(randomLocation());
		p.setFoodLevel(20);
		p.setSaturation(3.0F);
		p.setFireTicks(0);
		@SuppressWarnings("rawtypes")
		Iterator localIterator = p.getActivePotionEffects().iterator();
		if (localIterator.hasNext()) {
			PotionEffect pot = (PotionEffect) localIterator.next();
			p.removePotionEffect(pot.getType());
		}
	}

	public static void playSound(Player p, Sound sound, Float one, Float two) {
		p.playSound(p.getLocation(), sound, one, two);
	}
	
	public static double getDamage(ItemStack item) {
		if(item.getType() == Material.WOOD_SWORD) {
			return 5.5D;
		}
		
		if(item.getType() == Material.STONE_SWORD) {
			return 7.5D;
		}
		
		if(item.getType() == Material.GOLD_SWORD) {
			return 5.5D;
		}
		
		if(item.getType() == Material.IRON_SWORD) {
			return 9.5D;
		}
		
		if(item.getType() == Material.DIAMOND_SWORD) {
			return 12.5D;
		}
		
		return 1.0D;
	}
	
	public static enum GameStage{
		PREJOGO, INVENCIBILIDADE, JOGO, UNDEFINED;
	}
	
	public static GameStage getGameStage() {
		if(Main.PRE_JOGO) {
			return GameStage.PREJOGO;
		} else if(Main.INVENCIBILIDADE) {
			return GameStage.INVENCIBILIDADE;
		} else if(Main.EM_JOGO) {
			return GameStage.JOGO;
		} else {
			return GameStage.UNDEFINED;
		}
	}
	
	public static void sendWinParticle(Player p) {
	}

	@SuppressWarnings("deprecation")
	public static void startPartida() {
		for (int i = 0; i < 25; i++) {
			Bukkit.broadcastMessage(" ");
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendTitle(p, "§b§lWoC§6§lHG",
					"Partida iniciada, Que vença o melhor!", 1, 3, 1);
		}
		Bukkit.broadcastMessage("§f          §m--------- §b+ §f§m---------");
		Bukkit.broadcastMessage("§6                 Partida Iniciada");
		Bukkit.broadcastMessage("§6                Boa sorte à todos!");
		Bukkit.broadcastMessage("§6                   Jogadores: §f"
				+ (Bukkit.getOnlinePlayers().length - Admin.admins.size()));
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage("§6                   Kit: §f"
					+ ChatColor.stripColor(KitManager.getKit(p).getName()));
		}
		Bukkit.broadcastMessage("§f          §m--------- §b+ §f§m---------");

		for (int i = 0; i < 2; i++) {
			Bukkit.broadcastMessage(" ");
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!Admin.admins.contains(p.getName())) {
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory()
						.addItem(item(Material.COMPASS, 1, "§6Bússola"));
				Random r = new Random();
				if (r.nextBoolean()) {
					Location sp = Main.spawn.add(r.nextInt(15), 0, 15);
					sp.setY(sp.getWorld().getHighestBlockYAt(sp) + 1);
					p.teleport(sp);
				} else {
					Location sp = Main.spawn.subtract(r.nextInt(15), 0, 15);
					sp.setY(sp.getWorld().getHighestBlockYAt(sp) + 1);
					p.teleport(sp);
				}
				for (PotionEffect pot : p.getActivePotionEffects()) {
					p.removePotionEffect(pot.getType());
				}
				p.setExp(0F);
				p.setLevel(0);
				p.setHealth(20.0D);
				p.setFoodLevel(20);
				p.setFlying(false);
				p.setAllowFlight(false);
				p.closeInventory();
				if(KitManager.getKit(p).getItemKit() != null) {
					p.getInventory().addItem(KitManager.getKit(p).getItemKit());
				}
			}
		}
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
	
	@SuppressWarnings("deprecation")
	public static void shutdownServer(String msg) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			resetAll(p);
			p.kickPlayer("§f> §b§lWoC§6§lHG§7 < \n \n§6" + msg + " \nServidor reiniciando!");
		}
		
		Main.spawn.getWorld().getWorldFolder().delete();
		Bukkit.shutdown();
	}
	
	@SuppressWarnings({ "deprecation" })
	public static void checkWin() {
		if(Bukkit.getOnlinePlayers().length == 1) {
			Player winner = null;
			for(Player on : Bukkit.getOnlinePlayers()) {
				if(!Admin.admins.contains(on.getName())) {
					winner = on;
					break;
				}
			}
			
			Bukkit.broadcastMessage("§7[§6!§a!§6!§7] §4§l" + ChatColor.stripColor(winner.getDisplayName()) + " §c§lganhou a partida!");
			
			Location loc = winner.getLocation();
	        Location loc2 = loc.getWorld().getHighestBlockAt(loc).getLocation().add(0.0D, 50.0D, 0.0D);
	        for (int x = -1; x <= 3; x++) {
	          for (int z = -1; z <= 3; z++)
	          {
	            Block b = loc2.clone().add(x, 0.0D, z).getBlock();
	            b.setType(Material.CAKE_BLOCK);
	            Block a = loc2.clone().add(x, -1.0D, z).getBlock();
	            a.setType(Material.GLASS);
	          }
	        }
	        winner.teleport(loc2.clone().add(1.5, 2.5, 1.5));
	        winner.getInventory().clear();
	        winner.getInventory().setArmorContents(null);
	        BarAPI.setMessage("§4§l" + ChatColor.stripColor(winner.getDisplayName()) + " §c§lganhou a partida!");
	        winner.getInventory().setItem(4, API.item(Material.WATER_BUCKET, 1, "§6MLG"));
	        winner.getInventory().setItem(0, API.item(Material.MAP, 1, "§4§lVocê ganhou!"));
		}
	}
	
	public static void resetAll(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getEnderChest().clear();
		for(PotionEffect pot : p.getActivePotionEffects()) {
			p.removePotionEffect(pot.getType());
		}
		p.setFireTicks(0);
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

	public static ItemStack item(Material m) {
		return new ItemStack(m);
	}

	public static ItemStack item(Material m, int q) {
		return new ItemStack(m, q);
	}

	public static ItemStack item(Material m, int q, String n) {
		ItemStack it = new ItemStack(m);
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

	public static ItemStack item(Material m, int q, String n, String[] l) {
		ItemStack it = new ItemStack(m);
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

	public static void itensIniciais(Player p) {
		p.getInventory().clear();
		p.getInventory().setItem(4,
				item(Material.ENDER_CHEST, 1, "§6§lKits §7(Clique Direito)"));
	}
	
	public static void startCd(final Player p, final int coolDown, int startDelay, final HashMap<String, Integer> task, final HashMap<String, Integer> cd, final boolean isCd, final String msgFinalCd) {
		cd.put(p.getName(), coolDown);
		int shedId = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			int tempo = coolDown;
			@Override
			public void run() {
				if(tempo == 0) {
					cancelTaskCd(task, cd, p, isCd, msgFinalCd);
				} else {
					cd.put(p.getName(), tempo);
					tempo--;
				}
			}
		}, startDelay * 20L, 1 * 20L);
		task.put(p.getName(), shedId);
	}
	
	public static void cancelTaskCd(HashMap<String, Integer> task, HashMap<String, Integer> cd, Player p, boolean withMsg, String msg) {
		if(p.isOnline()) {
			if(withMsg) {
				p.sendMessage(msg);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
			}
		}
		if(task.containsKey(p.getName())) {
			Main.sh.cancelTask(task.get(p.getName()));
			task.remove(p.getName());
		}
		if(cd.containsKey(p.getName())) {
			cd.remove(p.getName());
		}
	}

	public static Location randomLocation() {
		int x = new Random().nextInt(500);
		int z = new Random().nextInt(500);
		if (new Random().nextBoolean()) {
			x = -x;
		}
		if (new Random().nextBoolean()) {
			z = -z;
		}
		Location loc = new Location(Bukkit.getWorld("world"), x, Bukkit
				.getWorld("world").getHighestBlockYAt(x, z) + 1, z);
		return loc;
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
		char cs1 = s.charAt(0);
		String sr = cs1 + s.substring(1, s.length());
		return sr;
	}

	@SuppressWarnings("deprecation")
	public static ArrayList<String> upperRanks(TagType rank) {
		ArrayList<String> ranks = new ArrayList<>();
		if (rank == TagType.NORMAL) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				ranks.add(on.getName());
			}
		} else if (rank == TagType.VIP) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.TRIAL) || isRank(on, TagType.PRO)
						|| isRank(on, TagType.VIP)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.PRO) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.TRIAL) || isRank(on, TagType.PRO)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.TRIAL) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.TRIAL)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.BUILDER) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MOD) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MODPLUS) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.CODER) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.ADMIN) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO) || isRank(on, TagType.ADMIN))) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.DONO) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (isRank(on, TagType.DONO)) {
					ranks.add(on.getName());
				}
			}
		}
		return ranks;
	}

	@SuppressWarnings("deprecation")
	public static ArrayList<String> belowRanks(TagType rank) {
		ArrayList<String> ranks = new ArrayList<>();
		if (rank == TagType.NORMAL) {

		} else if (rank == TagType.VIP) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (isRank(on, TagType.NORMAL)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.PRO) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if (isRank(on, TagType.VIP) || isRank(on, TagType.NORMAL)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.TRIAL) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.BUILDER) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MOD) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.BUILDER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MODPLUS) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.MOD)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.CODER) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.MODPLUS)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.ADMIN) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.CODER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.DONO) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.NORMAL) || isRank(on, TagType.VIP))
						|| isRank(on, TagType.PRO) || isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.ADMIN)) {
					ranks.add(on.getName());
				}
			}
		}
		return ranks;
	}

	public static boolean isRank(Player p, TagType tag) {
		if (tag == TagType.NORMAL) {
			return true;
		} else {
			if (p.hasPermission("tag." + tag.name().toLowerCase())) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void sendTitle(Player p, String t, String st, int fi, int s, int fo) {
		if(isOneEight(p)) {
			PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
			IChatBaseComponent title = ChatSerializer.a("{'text': '"
					+ t + "'}");
			IChatBaseComponent subtitle = ChatSerializer.a("{'text': '"
					+ st + "'}");
			
			connection.sendPacket(new ProtocolInjector.PacketTitle(Action.TIMES, fi * 20, s * 20, fo * 20));
			connection.sendPacket(new ProtocolInjector.PacketTitle(Action.TITLE, title));
			connection.sendPacket(new ProtocolInjector.PacketTitle(Action.SUBTITLE, subtitle));
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
}
