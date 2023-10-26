package me.everton.od;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import me.everton.od.cmds.Time.Team;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class API {

	public static void playSound(Player p, Sound sound, Float one, Float two) {
		p.playSound(p.getLocation(), sound, one, two);
	}

	public static double getDamage(ItemStack item) {
		if (item.getType() == Material.WOOD_SWORD) {
			return 5.5D;
		}

		if (item.getType() == Material.STONE_SWORD) {
			return 7.5D;
		}

		if (item.getType() == Material.GOLD_SWORD) {
			return 5.5D;
		}

		if (item.getType() == Material.IRON_SWORD) {
			return 9.5D;
		}

		if (item.getType() == Material.DIAMOND_SWORD) {
			return 12.5D;
		}

		return 1.0D;
	}

	public static boolean isFeast(Player p) {
		ApplicableRegionSet region = getWorldGuard().getRegionManager(
				p.getWorld()).getApplicableRegions(p.getLocation());
		for (ProtectedRegion pr : region) {
			if (pr.getId().equalsIgnoreCase("feast")) {
				return true;
			}
		}
		return false;
	}

	private static WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager()
				.getPlugin("WorldGuard");

		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}

		return (WorldGuardPlugin) plugin;
	}

	public static enum GameStage {
		PRE_JOGO, INVENCIBILIDADE, JOGO, UNDEFINED;
	}

	public static GameStage getGameStage() {
		if (Main.PRE_JOGO) {
			return GameStage.PRE_JOGO;
		} else if (Main.INVENCIBILIDADE) {
			return GameStage.INVENCIBILIDADE;
		} else if (Main.EM_JOGO) {
			return GameStage.JOGO;
		} else {
			return GameStage.UNDEFINED;
		}
	}

	public static Location getSpawn(Player p) {
		if (Main.sb.getTeam("RED").getPlayers().contains(p)) {
			return new Location(p.getWorld(), -352.5, 169.5, 53.5);
		} else if (Main.sb.getTeam("BLUE").getPlayers().contains(p)) {
			return new Location(p.getWorld(), -370.5, 169.5, -329.5);
		} else {
			p.sendMessage("§c§lErro! Notifique EvertonPvP pelo skype marcelinojr.everton dizendo que ocorreu o erro 1 no plugin de ObsidianDestroy!");
			return p.getWorld().getSpawnLocation();
		}
	}

	public static Location getSpawn(Team t) {
		if (t == Team.RED) {
			return new Location(Bukkit.getWorld("world"), -352.5, 169.5, 53.5);
		} else if (t == Team.BLUE) {
			return new Location(Bukkit.getWorld("world"), -370.5, 169.5, -329.5);
		} else {
			return Bukkit.getWorld("world").getSpawnLocation();
		}
	}

	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	public static void startPartida() {
		if (Bukkit.getOnlinePlayers().length % 2 != 0) {
			Bukkit.broadcastMessage("§7[§c!§7] É necessário mais 1 ou menos 1 jogador para o jogo começar!");
			return;
		}
		
		if(Main.red.getPlayers().size() == 0 && Main.blue.getPlayers().size() == 0) {
			Bukkit.broadcastMessage("§7[§c!§7] Os times §cvermelho §7 e §9azul §7estao vazios!");
			return;
		}
		
		if(Main.blue.getPlayers().size() == 0) {
			Bukkit.broadcastMessage("§7[§c!§7] O time §9azul §7está vazio!");
			return;
		}
		if(Main.red.getPlayers().size() == 0) {
			Bukkit.broadcastMessage("§7[§c!§7] O time §cvermelho §7está vazio!");
			return;
		}
		
		if(Main.blue.getPlayers().size() != Main.red.getPlayers().size()) {
			Bukkit.broadcastMessage("§7[§c!§7] É necessario o mesmo número de jogadores nos times!");
			return;
		}
		for (int i = 0; i < 25; i++) {
			Bukkit.broadcastMessage(" ");
		}
		Bukkit.broadcastMessage("§f          ********* §b+ §f*********");
		Bukkit.broadcastMessage("§6                 Partida Iniciada");
		Bukkit.broadcastMessage("§6                Boa sorte à todos!");
		Bukkit.broadcastMessage("§6                   Jogadores: §f"
				+ Bukkit.getOnlinePlayers().length);
		Bukkit.broadcastMessage("§f          ********* §b+ §f*********");

		TimerInvencibility.contagem();

		for (int i = 0; i < 2; i++) {
			Bukkit.broadcastMessage(" ");
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
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
			p.setGameMode(GameMode.SURVIVAL);
			p.teleport(getSpawn(p));
		}
	}

	public static boolean isInvFull(Player p) {
		int comItem = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) {
				comItem++;
			}
		}
		if (comItem == p.getInventory().getSize()) {
			return true;
		}
		return false;
	}

	public static void resetAll(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getEnderChest().clear();
		for (PotionEffect pot : p.getActivePotionEffects()) {
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
		if (!m.name().contains("LEATHER_")) {
			return null;
		}
		ItemStack it = new ItemStack(m);
		LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
		me.setColor(cor);
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	public static ItemStack leatherArmor(Material m, int q, String n,
			String[] l, Color cor) {
		if (!m.name().contains("LEATHER_")) {
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

	public static void startCd(final Player p, final int coolDown,
			int startDelay, final HashMap<String, Integer> task,
			final HashMap<String, Integer> cd, final boolean isCd,
			final String msgFinalCd) {
		cd.put(p.getName(), coolDown);
		int shedId = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {
					int tempo = coolDown;

					@Override
					public void run() {
						if (tempo == 0) {
							cancelTaskCd(task, cd, p, isCd, msgFinalCd);
						} else {
							cd.put(p.getName(), tempo);
							tempo--;
						}
					}
				}, startDelay * 20L, 1 * 20L);
		task.put(p.getName(), shedId);
	}

	public static void cancelTaskCd(HashMap<String, Integer> task,
			HashMap<String, Integer> cd, Player p, boolean withMsg, String msg) {
		if (p.isOnline()) {
			if (withMsg) {
				p.sendMessage(msg);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F, 15.5F);
			}
		}
		if (task.containsKey(p.getName())) {
			Main.sh.cancelTask(task.get(p.getName()));
			task.remove(p.getName());
		}
		if (cd.containsKey(p.getName())) {
			cd.remove(p.getName());
		}
	}

	public static void startTimer(final Player p, final int coolDown,
			int startDelay, final HashMap<String, Integer> task,
			final HashMap<String, Integer> cd, final boolean isCd,
			final String msgFinalCd, final String timer) {
		cd.put(p.getName(), coolDown);
		int shedId = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {
					int tempo = coolDown;

					@Override
					public void run() {
						if (tempo == 0) {
							cancelTaskCd(task, cd, p, isCd, msgFinalCd);
						} else {
							cd.put(p.getName(), tempo);
							if (tempo % 30 == 0) {
								p.sendMessage(timer.replace("<tempo>",
										secToMinSec(tempo)));
								p.playSound(p.getLocation(), Sound.CLICK, 1F,
										1F);
							}
							tempo--;
						}
					}
				}, startDelay * 20L, 1 * 20L);
		task.put(p.getName(), shedId);
	}

	public static void cancelTaskTimer(HashMap<String, Integer> task,
			HashMap<String, Integer> cd, Player p, boolean withMsg, String msg) {
		if (p.isOnline()) {
			if (withMsg) {
				p.sendMessage(msg);
				p.playSound(p.getLocation(), Sound.ANVIL_LAND, 15.5F, 15.5F);
			}
		}
		if (task.containsKey(p.getName())) {
			Main.sh.cancelTask(task.get(p.getName()));
			task.remove(p.getName());
		}
		if (cd.containsKey(p.getName())) {
			cd.remove(p.getName());
		}

		Main.INVENCIBILIDADE = false;
	}

	public static String secToMin(int i) {
		if (i >= 86400) {
			int d = (int) TimeUnit.SECONDS.toDays(i);
			int h = (int) (TimeUnit.SECONDS.toHours(i) - (d * 24));
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(d) + ":" + doisDigitos(h) + ":" + doisDigitos(m)
					+ ":" + doisDigitos(s);
		} else if (i >= 3600) {
			int h = (int) TimeUnit.SECONDS.toHours(i);
			int m = (int) (TimeUnit.SECONDS.toMinutes(i) - (TimeUnit.SECONDS
					.toHours(i) * 60));
			int s = (int) (TimeUnit.SECONDS.toSeconds(i) - (TimeUnit.SECONDS
					.toMinutes(i) * 60));
			return doisDigitos(h) + ":" + doisDigitos(m) + ":" + doisDigitos(s);
		} else {
			int m = (int) TimeUnit.SECONDS.toMinutes(i);
			int s = i - (m * 60);
			return doisDigitos(m) + ":" + doisDigitos(s);
		}
	}

	public static String secToMinSec(int i) {
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
}
