package me.dark.Listener;

import java.util.List;
import me.dark.Chests.Chests;
import me.dark.Cmd.Admin;
import me.dark.Game.GameState;
import me.dark.Main;
import me.dark.Utils.DarkUtils;
import me.dark.kit.KitSelector;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GeralGameL implements Listener {
	
	public static ItemStack chests = DarkUtils.create_item(Material.CHEST, "�3Kits�7!", 1, 0, null);
	
	@EventHandler
	public void food(FoodLevelChangeEvent e) {
		if (Main.estado == GameState.PREGAME) {
			e.setCancelled(true);
			e.setFoodLevel(20);
		} else if (Main.estado == GameState.INVENCIBILITY) {
			e.setCancelled(true);
			e.setFoodLevel(20);
		}else if (Main.estado == GameState.WIN) {
			e.setCancelled(true);
			e.setFoodLevel(20);
		} else {
			((Player) e.getEntity()).setSaturation(4.0F);
		}
	}
	@EventHandler
	public void reciveDamage(EntityDamageEvent e) {
		
		if (Main.estado == GameState.PREGAME) {
			if (e.getCause() == DamageCause.LAVA) {
				if (e.getEntity() instanceof Player) {
					if (!LavaChallenge.inLava.contains(e.getEntity())) {
						e.setCancelled(true);
					}
				}
			}else if (!PreGameFight.in1v1.contains(e.getEntity())) {
				e.setCancelled(true);	
			} 
		}else if (Main.estado == GameState.WIN) {
			e.setDamage(0.0);
		}else if (Main.estado == GameState.INVENCIBILITY) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void asd(PlayerInteractEntityEvent e){
		if (Main.estado == GameState.PREGAME) {
			Player p = e.getPlayer();
			if (!PreGameFight.in1v1.contains(p)) {
				e.setCancelled(true);
				if (e.getRightClicked() instanceof Villager) {
					Villager v = (Villager) e.getRightClicked();
					if (v.getCustomName().equalsIgnoreCase("�bLoja de Kits")) {
						
					} else if (v.getCustomName().equalsIgnoreCase("�cLavaChallenge")) {
						p.teleport(new Location(p.getWorld(), -15, 201, -13));
					}
				}
			} else {
				if (e.getRightClicked() instanceof Player) {
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
	    if (e.getItem() != null) {
	    	if (e.getItem().equals(chests)) {
	    		e.setCancelled(true);
	    		KitSelector.openInv(e.getPlayer());
	    	}else if (e.getItem().equals(Chests.chest)) {
	    		e.setCancelled(true);
	    		Chests.inv(e.getPlayer());
	    	}else if (e.getItem().equals(HotBar.soup)) {
	    		e.setCancelled(true);
	    		HotBar.invSoup(e.getPlayer());
	    	}else if (e.getItem().equals(HotBar.status)) {
	    		e.setCancelled(true);
	    		HotBar.invStatus(e.getPlayer());
	    	}else if (e.getItem().equals(PreGameFight.p1v1)) {
	    		e.setCancelled(true);
	    		PreGameFight.add1v1(e.getPlayer());
	    	}
	    }
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
	    if (Main.estado == GameState.PREGAME) {
			if (!PreGameFight.in1v1.contains(e.getPlayer())) {
			      e.setCancelled(true);	
			}
	    }
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e){
	    if (Main.estado == GameState.PREGAME) {
	    	if (LavaChallenge.inLava.contains(e.getPlayer())) return;
			if (!PreGameFight.in1v1.contains(e.getPlayer())) {
			      e.setCancelled(true);	
			} else if (e.getItemDrop().getItemStack().getType()==Material.STONE_SWORD){
				e.setCancelled(true);
			}
	    }
	}
	
	@EventHandler
	public void pickup(PlayerPickupItemEvent e){
	    if (Main.estado == GameState.PREGAME) {
	      e.setCancelled(true);
	    }else if (Admin.admin.contains(e.getPlayer())) {
	    	e.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void click(InventoryClickEvent e){
		if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;
	    if (Main.estado == GameState.PREGAME) {
	    	if (LavaChallenge.inLava.contains(e.getWhoClicked())) return;
			if (!PreGameFight.in1v1.contains(e.getWhoClicked())) {
				e.setCancelled(true);	
			}
	    }
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
            Player p = e.getPlayer();
            if (e.getAction() != Action.PHYSICAL && e.getItem() != null
    				&& e.getItem().getType() == Material.COMPASS) {
    			Boolean found = Boolean.valueOf(false);
    			for (int i = 0; i < 1000; i++) {
    				List<Entity> entities = p.getNearbyEntities(i, 128.0D, i);
    				for (Entity ent : entities) {
    					if ((!ent.getType().equals(EntityType.PLAYER))
    							|| (p.getLocation().distance(ent.getLocation()) <= 20.0D)
    							|| (!Main.players.contains(ent)))
    						continue;
    					p.setCompassTarget(ent.getLocation());
    					p.sendMessage(Main.prefix_bussola + ((Player) ent).getName());
    					found = Boolean.valueOf(true);
    					break;
    				}
    				if (found.booleanValue()) {
    					break;
    				}
    			}
    			if (!found.booleanValue()) {
    				p.sendMessage(ChatColor.RED
    						+ "�cNenhum jogador encontrado. Apotando para o Spawn");
    				p.setCompassTarget(p.getWorld().getSpawnLocation());
    			}
    		}
            ItemStack bowl = new ItemStack(Material.BOWL, 1);
            ItemMeta meta = bowl.getItemMeta();
            EntityPlayer p2 = ((CraftPlayer)p).getHandle();
            if (p2.getHealth() <= 19
                            && e.getAction() == Action.RIGHT_CLICK_AIR
                            || e.getAction() == Action.RIGHT_CLICK_BLOCK
                            && p.getItemInHand().getType() == Material.MUSHROOM_SOUP
                            && p2.getHealth() < p2.getMaxHealth()) {
                    int heal = 7;
                    if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                            if (p2.getHealth() >= 13) {
                                    p.setHealth(20);
                            } else {
                                    p.setHealth(p2.getHealth() + heal > p2
                                                    .getMaxHealth() ? p2.getMaxHealth() : p2
                                                    .getHealth() + heal);
                            }
                            p.getItemInHand().setType(Material.BOWL);
                            p.getItemInHand().setItemMeta(meta);
                            p.setItemInHand(bowl);
                            p.updateInventory();
                    }
            } else if (p.getFoodLevel() <= 19
                            && e.getAction() == Action.RIGHT_CLICK_AIR
                            || e.getAction() == Action.RIGHT_CLICK_BLOCK
                            && p.getItemInHand().getType() == Material.MUSHROOM_SOUP
                            && p.getFoodLevel() < 20) {
                    if (!(p.getFoodLevel() == 20)) {
                            if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                                    if (p.getFoodLevel() >= 15) {
                                            p.setFoodLevel(20);
                                    } else {
                                            int tofeed = 20 - e.getPlayer().getFoodLevel();
                                            tofeed = 5;
                                            e.getPlayer().setFoodLevel(
 e.getPlayer().getFoodLevel() + tofeed);
                                    }
                           
                                    p.getItemInHand().setType(Material.BOWL);
                                    p.getItemInHand().setItemMeta(meta);
                                    p.setItemInHand(bowl);
                                    p.updateInventory();
                            }
                    }
            }
    }
	@EventHandler
	public void spawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Player) {
			return;
		}
		if (Main.estado == GameState.PREGAME) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void blockbreak(BlockBreakEvent e) {
		if (e.getBlock().getLocation().getBlockX() > 490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode quebrar blocos perto do forcefield!");
		} else if (e.getBlock().getLocation().getBlockX() < -490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode quebrar blocos perto do forcefield!");
		}else if (e.getBlock().getLocation().getBlockZ() > 490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode quebrar blocos perto do forcefield!");
		}else if (e.getBlock().getLocation().getBlockZ() < -490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode quebrar blocos perto do forcefield!");
		}
	}
	@EventHandler
	public void place(BlockPlaceEvent e) {
		if (e.getBlock().getLocation().getBlockX() > 490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode colocar blocos perto do forcefield!");
		} else if (e.getBlock().getLocation().getBlockX() < -490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode colocar blocos perto do forcefield!");
		}else if (e.getBlock().getLocation().getBlockZ() > 490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode colocar blocos perto do forcefield!");
		}else if (e.getBlock().getLocation().getBlockZ() < -490) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("�cVoc� n�o pode colocar blocos perto do forcefield!");
		}
	}

}
