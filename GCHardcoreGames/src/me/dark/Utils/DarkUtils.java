package me.dark.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.MySQL.MySQL;
import net.goodcraft.Tag;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
import net.minecraft.util.com.google.common.io.ByteArrayDataOutput;
import net.minecraft.util.com.google.common.io.ByteStreams;

public class DarkUtils {
	
	private static ConsoleCommandSender console = Bukkit.getConsoleSender();

	public static ConsoleCommandSender getConsole() {
		return console;
	}
	public static void sendMessage(String msg) {
		console.sendMessage(msg);
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static void sendToServer(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(Main.getMain(), "BungeeCord", out.toByteArray());
	}
	
	public static void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
		for (PotionEffect pots : p.getActivePotionEffects()) {
			p.removePotionEffect(pots.getType());
		}
	}
	
	public static ItemStack create_item(Material mat, String name, int qnt, int sh, List<String> lore){
		  ItemStack i = new ItemStack(mat,qnt,(short)sh);
		  ItemMeta im = i.getItemMeta();
		  im.setLore(lore);
		  im.setDisplayName(name);
		  i.setItemMeta(im);
		  return i;
	}
	 public static ItemStack create_item(Material mat, Enchantment ench,int e,String name, int qnt, int sh, List<String> lore){
		 ItemStack i = new ItemStack(mat,qnt,(short)sh);
		 ItemMeta im = i.getItemMeta();
		 im.setLore(lore);
		 im.addEnchant(ench, e, true);
		 im.setDisplayName(name);
		 i.setItemMeta(im);
		 return i;
	}
		public static ItemStack leatherArmor(Material m, Color c) {
			ItemStack i = new ItemStack(m);
			if (i.getType() == Material.LEATHER_BOOTS
					|| i.getType() == Material.LEATHER_HELMET
					|| i.getType() == Material.LEATHER_CHESTPLATE
					|| i.getType() == Material.LEATHER_LEGGINGS) {
				LeatherArmorMeta itemmeta = (LeatherArmorMeta) i.getItemMeta();
				itemmeta.setColor(c);
				i.setItemMeta(itemmeta);
				return i;
			}
			return i;
		}
		@SuppressWarnings("deprecation")
		public static void sendBlockBreakParticles(Location loc, Material i){
		    PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), i.getId(), false);
		    for (Player p : Bukkit.getOnlinePlayers()) {
		    	((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		    }
		}
		 
	 public static void FireWork2(final Location loc,Color l) {
		 Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		 FireworkMeta fwm = fw.getFireworkMeta();
		 Color c1 = l;
		 Color c2 = l;
		 Color c3 = l;
		 FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c1).withColor(c2).withFade(c3).with(Type.BALL_LARGE).trail(true).build();
		 fwm.clearEffects();
		 fwm.addEffect(effect);
		 Field f;
		try {
			f = fwm.getClass().getDeclaredField("power");
			 f.setAccessible(true);
			 f.set(fwm, -2);
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 fw.setFireworkMeta(fwm);
	 }
	 public static void FireWork(final Location loc,Color l) {
		 Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		 FireworkMeta fwm = fw.getFireworkMeta();
		 fwm.clearEffects();
		 Random r = new Random();   

		 int rt = r.nextInt(3) + 1;
		 Type type = Type.BALL;       
		 if (rt == 1) type = Type.BALL_LARGE;
		 if (rt == 2) type = Type.BURST;
		 if (rt == 3) type = Type.CREEPER;
		 if (rt == 4) type = Type.STAR;
		 Color c1 = l;
		 Color c2 = l;
		 Color c3 = l;
		 FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withColor(c2).withFade(c3).with(type).trail(false).build();
		 fwm.addEffect(effect);
		 fwm.setPower(0);
		 fw.setFireworkMeta(fwm);
		 final List<Location> blocks = circle(loc, 10, 1, true, false, 0);
		 new BukkitRunnable() {
			 int maxIterations = 2;
			 int iterations = 0;
			 public void run() {
				 iterations = 0;
			     while (!blocks.isEmpty() && iterations++ < maxIterations) {
			    	 final Firework fw = (Firework) loc.getWorld().spawnEntity(blocks.get(0), EntityType.FIREWORK);
			         FireworkMeta meta = fw.getFireworkMeta();
			         meta.addEffect(FireworkEffect.builder().withColor(Color.RED).withColor(Color.WHITE).withFade(Color.WHITE).build());
			         fw.setFireworkMeta(meta);
			         fw.detonate();
			          blocks.remove(0);
			      }
			      if (blocks.isEmpty()) {
			    	 this.cancel();
			      }
			   }
			}.runTaskTimer(Main.getMain(), 0, 2);
		}
	 private static List<Location> circle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
		 List<Location> circleblocks = new ArrayList<Location>();
		 int cx = loc.getBlockX();
		 int cy = loc.getBlockY();
		 int cz = loc.getBlockZ();
		 int i = 0;
		 for (int x = cx - r; x <= cx + r; x++)
			 for (int z = cz - r; z <= cz + r; z++)
				 for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
					 double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					 if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
						 if(i++ < 2) {
							 continue;
						 } else {
							 i = 0;
						 }
						 Location l = new Location(loc.getWorld(), x, y + plus_y, z);
						 circleblocks.add(l);
					 }
			    }
			    return circleblocks;
	  }
	  public static String tempoInt(int tempo) {
		  int ss = tempo % 60;
	      tempo /= 60;
	      int min = tempo % 60;
	      tempo /= 60;
	      int hh = tempo % 24;
	      
	      if ((ss > 0) && (min == 0) && (hh == 0)) {
	    	  return "0:" + strzero(ss);
	      }
	      if (hh > 0) {
	    	  return strzero(hh) +":"+ strzero(min) + ":" + strzero(ss);
	      }
	      return strzero(min) + ":" + strzero(ss);
	 }
	 private static String strzero(int n) {
		  if (n < 10) {
			  return "0" + String.valueOf(n);
		  }
	      return String.valueOf(n);
     }
	 public static String getRank(Player p) {
		 Tag ta = null;
		 for (Tag t : Tag.tags) {
			 if (p.hasPermission("tag."+t.getNome())) {
				 ta = t;
			 }
		 }
		 if (ta == null) {
			 return "§7Normal";
		 }
		return ta.getCor()+ta.getNome();
	}
	public static void sqlConnect() {
		try {
			Main.sql = new MySQL();
			if (MySQL.ativo) {
				sendMessage("§3Conexão sql estabelecida!");
			} else {
				sendMessage("§3MySQL desativado!");
		    }
		}catch(Exception ex) {
			sendMessage("§4Erro ao conectar ao MySQL\n"+ex.getMessage());
			MySQL.ativo = false;
		}
    }
	public static void createBoard() {
			int xx = 500;
			for (int z = -500; z <=500; z++) {
				for (int y = 55; y < 150; y++) {
					Location loc = new Location(Main.usingWorld, xx, y, z);
					if (!loc.getChunk().isLoaded()) {
						loc.getChunk().load();
					}
					if (new Random().nextBoolean()) {
						loc.getBlock().setType(Material.GLASS);
					} else {
						loc.getBlock().setType(Material.STONE);
					}
				}
			}
			
			int zz = 500;
			for (int x = -500; x <=500; x++) {
				for (int y = 55; y < 150; y++) {
					Location loc = new Location(Main.usingWorld, x, y, zz);
					if (!loc.getChunk().isLoaded()) {
						loc.getChunk().load();
					}
					if (new Random().nextBoolean()) {
						loc.getBlock().setType(Material.GLASS);
					} else {
						loc.getBlock().setType(Material.STONE);
					}
				}
			}
			
			int xz = -500;
			for (int z = -500; z <=500; z++) {
				for (int y = 55; y < 150; y++) {
					Location loc = new Location(Main.usingWorld, xz, y, z);
					if (!loc.getChunk().isLoaded()) {
						loc.getChunk().load();
					}
					if (new Random().nextBoolean()) {
						loc.getBlock().setType(Material.GLASS);
					} else {
						loc.getBlock().setType(Material.STONE);
					}
				}
			}
			
			int z = -500;
			for (int x = -500; x <=500; x++) {
				for (int y = 55; y < 150; y++) {
					Location loc = new Location(Main.usingWorld, x, y, z);
					if (!loc.getChunk().isLoaded()) {
						loc.getChunk().load();
					}
					if (new Random().nextBoolean()) {
						loc.getBlock().setType(Material.GLASS);
					} else {
						loc.getBlock().setType(Material.STONE);
					}
				}
			}
		}
		
		public static void loadChunks() {
			for (int x = -510; x <= 510; x++) {
				for (int z = -510; z <= 510; z++) {
					for (int y = 55; y <= 150; y++) {
						Location loc = new Location(Main.usingWorld, x, y, z);
						if (!loc.getChunk().isLoaded()) {
							loc.getChunk().load();
						}
					}
				}
			}
		}
		
		public static void clear() {
		      for (int x = -33; x < 33; x++) {
		          for (int y = 190; y < 220; y++) {
		              for (int z = -33; z < 33; z++) {
		                  Block block = Main.usingWorld.getBlockAt(x, y, z);
		                  if (block.getType() == Material.FENCE) {
			                  block.setType(Material.AIR);
		                  }
		              }
		          }
		      }
		       
		}
		
	  

}
