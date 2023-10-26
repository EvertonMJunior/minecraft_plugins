package me.dark.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Title.TitleAPI;
import me.dark.Utils.DarkUtils;

public abstract class Kit implements Listener {
	
	private String name;
	private Material selector;
	private ItemStack[] items;
	private List<String> description;
	private ArrayList<Player> using;
	private HashMap<Player, Long> cooldown = new HashMap<>();
	
	public static ArrayList<Kit> kits = new ArrayList<Kit>();
	
	public Kit(String nome, Material kitSelector, ItemStack[] kitItems, List<String> desc) {
		name = nome;
		selector = kitSelector;
		items = kitItems;
		description = desc;
		using = new ArrayList<Player>();
		
		Bukkit.getPluginManager().registerEvents(this, Main.getMain());
		kits.add(this);
		DarkUtils.sendMessage("§3§l[!]§b Adicionando kit " + name + "!");
	}
	
	public String toString() {
		return name;
	}
	
	public Material getKitSelectorItem() {
		return selector;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	public void setItems(ItemStack[] item) {
		 items = item;
	}
	
	public List<String> getDesc() {
		return description;
	}
	
	public void addPlayer(Player p) {
		using.add(p);
	}
	
	protected boolean hasAbility(Player p) {
		return using.contains(p);
	}
	
	public void removePlayer(Player p) {
		using.remove(p);
	}
	
	public ArrayList<Player> getPlayers() {
		return using;
	}
	
	protected void addCooldown(final Player p, final int seconds) {
		long cooldownLength = System.currentTimeMillis() + seconds * 1000;
	    cooldown.remove(p);
	    cooldown.put(p, Long.valueOf(cooldownLength));
	    new BukkitRunnable() {
			int i = 0;
			public void run() {
				if (!using.contains(p)) {
					cooldown.remove(p);
					cancel();
					return;
				}else if (i==seconds) {
					TitleAPI.sendActionBar(p, "§bSeu cooldown acabou!");
					p.playSound(p.getLocation(), Sound.CLICK, 1f, 1f);
					cooldown.remove(p);
					cancel();
					return;
				}else if (!p.isOnline()) {
		  	          cooldown.remove(p);
		  	          cancel();
		  	          return;
		    	}else if (!cooldown.containsKey(p)) {
		        	cancel();
		        	return;
		        }
				i++;
			}
		}.runTaskTimer(Main.getMain(), 0, 20);
	}
	
	protected String getCooldown(Player p) {
		long cooldownLength = ((Long)cooldown.get(p)).longValue();
		long left = (cooldownLength - System.currentTimeMillis()) / 1000L;
		return left+"";
	}
	
	protected boolean hasCooldown(Player p) {
	    return cooldown.containsKey(p);
	}
	
	protected void removeCooldown(Player p) {
	    cooldown.remove(p);
	}
	
	protected void mensagemcooldown(Player p) {
	    p.sendMessage(Main.cooldown.replace("%cool", getCooldown(p)));
	}
	
	public void onStartGame() {}

	

}
