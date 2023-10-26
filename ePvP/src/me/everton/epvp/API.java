package me.everton.epvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.ProtocolInjector;

public class API {
	
	public static void setHeaderFooter(Player p, String header, String footer) {
		if(Title.getProtocolVersion(p) < 47) {
			return;
		}
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
	    IChatBaseComponent headerc = ChatSerializer.a("{'text': '" + header + "'}");
	    IChatBaseComponent footerc = ChatSerializer.a("{'text': '" + footer + "'}");
	    connection.sendPacket(new ProtocolInjector.PacketTabHeader(headerc, footerc));
	}
	
	public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stayTime, int fadeOut) {
		if(Title.getProtocolVersion(p) < 47) {
			return;
		}
		Title t = new Title(title, subtitle, fadeIn, stayTime, fadeOut);
		t.setTimingsToSeconds();
		t.send(p);
	}
	
	public static void sendActionBar(Player p, String msg){
		if(Title.getProtocolVersion(p) < 47) {
			return;
		}
        CraftPlayer cp = (CraftPlayer) p;
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) cp).getHandle().playerConnection.sendPacket(ppoc);
    }
	
	public static int ping(Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		return ep.ping;
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
	
	public static String ip(Player p, String text) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		IChatBaseComponent headerc = ChatSerializer.a("{'text': '" + text + "'}");
		ep.sendMessage(headerc);
		return cp.getAddress().getAddress().toString();
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
