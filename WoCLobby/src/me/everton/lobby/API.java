package me.everton.lobby;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import me.confuser.barapi.BarAPI;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.spigotmc.ProtocolInjector;
import org.spigotmc.ProtocolInjector.PacketTitle.Action;

public class API {
	public static ItemStack item(Material m){
		return new ItemStack(m);
	}
	
	public static ItemStack item(Material m, int q){
		return new ItemStack(m, q);
	}
	
	public static ItemStack item(Material m, int q, String n){
		ItemStack it = new ItemStack(m);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack item(Material m, int q, String n, String[] l){
		ItemStack it = new ItemStack(m);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}
	
	public static ItemStack item(Material m, int q, String n, int t){
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	public static ItemStack item(Material m, int q, String n, String[] l, int t){
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
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
	
	public static void deleteHashMapKey(HashMap<?, ?> h, String v){
		if(h.containsKey(v)){
			h.remove(v);
		}
	}
	
	public static void deleteArrayList(ArrayList<?> a, String v){
		if(a.contains(v)){
			a.remove(v);
		}
	}
	
	public static boolean isRanked(Player p) {
		if(p.hasPermission("woc.admin")) {
			return true;
		}
		if(p.hasPermission("tag.vip")) {
			return true;
		}
		if(p.hasPermission("tag.pro")) {
			return true;
		}
		return false;
	}
	
	public static String getSpaces(String forGetSpaces, int maxSpaces) {
		int esp = maxSpaces - forGetSpaces.length();
		String espacos = "";
		for(int i = 0; i < esp; i++) {
			espacos += " ";
		}
		return espacos;
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
}
