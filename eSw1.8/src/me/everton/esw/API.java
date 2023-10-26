package me.everton.esw;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class API {
	public static void sendTitle(Player p, String title, String subtitle) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent titleJSON = ChatSerializer.a("{'text':'" + title
				+ "'}");
		IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text':'"
				+ subtitle + "'}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(
				EnumTitleAction.TITLE, titleJSON);
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(
				EnumTitleAction.SUBTITLE, subtitleJSON);

		connection.sendPacket(titlePacket);
		connection.sendPacket(subtitlePacket);
	}

	public static void sendActionBar(Player player, String message) {
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message
				+ "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}

	public static void setHeaderFooter(Player p, String headerT, String footerT) {
		CraftPlayer craftplayer = (CraftPlayer) p;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent header = ChatSerializer.a("{\"text\": \"" + headerT
				+ "\"}");
		IChatBaseComponent footer = ChatSerializer.a("{\"text\": \"" + footerT
				+ "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, header);
			headerField.setAccessible(!headerField.isAccessible());

			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, footer);
			footerField.setAccessible(!footerField.isAccessible());
		} catch (Exception e) {
			e.printStackTrace();
		}

		connection.sendPacket(packet);
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
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.PRO)
						|| isRank(on, TagType.VIP)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.PRO) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)
						|| isRank(on, TagType.TRIAL)
						|| isRank(on, TagType.PRO)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.TRIAL) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
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
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)
						|| isRank(on, TagType.BUILDER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MOD) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)
						|| isRank(on, TagType.MOD)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.MODPLUS) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)
						|| isRank(on, TagType.MODPLUS)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.CODER) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))
						|| isRank(on, TagType.CODER)) {
					ranks.add(on.getName());
				}
			}
		} else if (rank == TagType.ADMIN) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				if ((isRank(on, TagType.DONO)
						|| isRank(on, TagType.ADMIN))) {
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
	
	@SuppressWarnings("deprecation")
	public static ItemStack item(int m, int q, String n, int t){
		ItemStack it = new ItemStack(m, q, (byte) t);
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
}
