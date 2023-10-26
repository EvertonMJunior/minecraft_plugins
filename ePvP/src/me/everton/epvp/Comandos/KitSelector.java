package me.everton.epvp.Comandos;

import java.util.HashMap;
import java.util.Random;

import me.everton.epvp.API;
import me.everton.epvp.KitManager;
import me.everton.epvp.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelector implements Listener{
	public static HashMap<String, String> confirmkit = new HashMap<>();
	
	public static void confirmar(Player p, String nome, String description, ItemStack item){
		Inventory cf = Bukkit.createInventory(p, 54, "§4" + nome);
		
		ItemStack vidroa = API.item(Material.STAINED_GLASS_PANE, 1, "§0", 11);
		ItemStack vidrov = API.item(Material.STAINED_GLASS_PANE, 1, "§0", 14);
		
		for(int i = 0; i < 54; i++){
			if(new Random().nextBoolean()){
				cf.setItem(i, vidroa);
			} else {
				cf.setItem(i, vidrov);
			}
		}
		
		cf.setItem(22, item);
		
		ItemStack voltar = API.item(Material.SUGAR, 1, "§c§l<< Voltar");
		cf.setItem(0, voltar);
		
		ItemStack descricao = API.item(Material.WOOL, 1, "§6§l>> Descrição do Kit <<", new String[] {description}, 15);
		
		ItemStack confirm =  API.item(Material.WOOL, 1, "§6§l>> Escolher Kit <<", 5);
		
		cf.setItem(38, descricao);
		cf.setItem(42, confirm);
		
		p.openInventory(cf);
		confirmkit.put(p.getName(), nome);
		
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
	}
	
	public static void abrirSelector(Player p, String inventario){
		confirmkit.remove(p.getName());
		Inventory kitsp = Bukkit.createInventory(p, 54, "§4§lKits que Você possui");
		Inventory kitst = Bukkit.createInventory(p, 54, "§4§lTodos os kits");
		ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);
		ItemMeta vm = vidro.getItemMeta();
		vm.setDisplayName("§0");
		vidro.setItemMeta(vm);
		
		ItemStack possui = API.item(Material.WOOL, 1, "§a§l>> Kits que você possui <<", 5);
		
		ItemStack todos = API.item(Material.WOOL, 1, "§a§l>> Todos os kits <<", 0);
		
		for(int i = 0; i < 9; i++){
			kitst.setItem(i, vidro);
			kitsp.setItem(i, vidro);
		}
		
		for(int i = 45; i < 54; i++){
			kitst.setItem(i, vidro);
			kitsp.setItem(i, vidro);
		}
		
		kitsp.setItem(3, possui);
		kitsp.setItem(5, todos);
		
		kitst.setItem(3, possui);
		kitst.setItem(5, todos);
		
		ItemStack pvp = API.item(Material.STONE_SWORD, 1, "§6§lPvP");
		kitst.addItem(pvp);
		if(p.hasPermission("kit.pvp")){
			kitsp.addItem(pvp);
		}
		
		ItemStack legolas = API.item(Material.BOW, 1, "§6§lLegolas");
		kitst.addItem(legolas);
		if(p.hasPermission("kit.legolas")){
			kitsp.addItem(legolas);
		}
		
		ItemStack grappler = API.item(Material.LEASH, 1, "§6§lGrappler");
		kitst.addItem(grappler);
		if(p.hasPermission("kit.grappler")){
			kitsp.addItem(grappler);
		}
		
		ItemStack stomper = API.item(Material.IRON_BOOTS, 1, "§6§lStomper");
		kitst.addItem(stomper);
		if(p.hasPermission("kit.stomper")){
			kitsp.addItem(stomper);
		}
		
		ItemStack quickdropper = API.item(Material.BOWL, 1, "§6§lQuickDropper");
		kitst.addItem(quickdropper);
		if(p.hasPermission("kit.quickdropper")){
			kitsp.addItem(quickdropper);
		}
		
		ItemStack sniper = API.item(Material.BLAZE_ROD, 1, "§6§lSniper");
		kitst.addItem(sniper);
		if(p.hasPermission("kit.sniper")){
			kitsp.addItem(sniper);
		}
		
		ItemStack avatar = API.item(Material.BEACON, 1, "§6§lAvatar");
		kitst.addItem(avatar);
		if(p.hasPermission("kit.avatar")){
			kitsp.addItem(avatar);
		}
		
		ItemStack kangaroo = API.item(Material.FIREWORK, 1, "§6§lKangaroo");
		kitst.addItem(kangaroo);
		if(p.hasPermission("kit.kangaroo")){
			kitsp.addItem(kangaroo);
		}
		
		ItemStack ninja = API.item(Material.COMPASS, 1, "§6§lNinja");
		kitst.addItem(ninja);
		if(p.hasPermission("kit.ninja")){
			kitsp.addItem(ninja);
		}
		
		ItemStack c4 = API.item(Material.SLIME_BALL, 1, "§6§lC4");
		kitst.addItem(c4);
		if(p.hasPermission("kit.c4")){
			kitsp.addItem(c4);
		}
		
		ItemStack flash = API.item(Material.REDSTONE_TORCH_ON, 1, "§6§lFlash");
		kitst.addItem(flash);
		if(p.hasPermission("kit.flash")){
			kitsp.addItem(flash);
		}
		
		ItemStack terrorista = API.item(Material.MAGMA_CREAM, 1, "§6§lTerrorista");
		kitst.addItem(terrorista);
		if(p.hasPermission("kit.terrorista")){
			kitsp.addItem(terrorista);
		}
		
		ItemStack sombra = API.item(Material.WOOL, 1, "§6§lSombra", 15);
		kitst.addItem(sombra);
		if(p.hasPermission("kit.sombra")){
			kitsp.addItem(sombra);
		}
		
		ItemStack cactus = API.item(Material.CACTUS, 1, "§6§lCactus");
		kitst.addItem(cactus);
		if(p.hasPermission("kit.cactus")){
			kitsp.addItem(cactus);
		}
		
		ItemStack poseidon = API.item(Material.WATER_BUCKET, 1, "§6§lPoseidon");
		kitst.addItem(poseidon);
		if(p.hasPermission("kit.poseidon")){
			kitsp.addItem(poseidon);
		}
		
		ItemStack vacuum = API.item(Material.EYE_OF_ENDER, 1, "§6§lVacuum");
		kitst.addItem(vacuum);
		if(p.hasPermission("kit.vacuum")){
			kitsp.addItem(vacuum);
		}
		
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		if(inventario.equalsIgnoreCase("possui")){
			p.openInventory(kitsp);
		} else if(inventario.equalsIgnoreCase("todos")){
			p.openInventory(kitst);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void inventoryClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if((e.getInventory().getName().equalsIgnoreCase("§4§lKits que Você possui")) || (e.getInventory().getName().equalsIgnoreCase("§4§lTodos os kits"))){
			e.setCancelled(true);
			if(e.getSlot() == 3){
				abrirSelector(p, "possui");
			} else if(e.getSlot() == 5){
				abrirSelector(p, "todos");
			} else if((e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) && (e.getInventory().getName().equalsIgnoreCase("§4§lKits que Você possui"))){
				if(e.getCurrentItem().getType() == Material.LEASH){
					confirmar(p, "Grappler", "§bAnde rapidamente com sua corda!", API.item(Material.LEASH, 1, "§a§lGrappler"));
				} else if(e.getCurrentItem().getType() == Material.STONE_SWORD){
					confirmar(p, "PvP", "§bGanhe uma espada de pedra!", API.item(Material.STONE_SWORD, 1, "§a§lPvP"));
				} else if(e.getCurrentItem().getType() == Material.BOW){
					confirmar(p, "Legolas", "§bCom seu arco, dê HeadShots e mate todos!", API.item(Material.BOW, 1, "§a§lLegolas"));
				} else if(e.getCurrentItem().getType() == Material.IRON_BOOTS){
					confirmar(p, "Stomper", "§bPule de lugares altos e mate todos que estiverem onde caíres!", API.item(Material.IRON_BOOTS, 1, "§a§lStomper"));
				} else if(e.getCurrentItem().getType() == Material.BOWL){
					confirmar(p, "QuickDropper", "§bAo tomar sopas, a tigela é automaticamente dropada!", API.item(Material.BOWL, 1, "§a§lQuickdropper"));
				} else if(e.getCurrentItem().getType() == Material.BEACON){
					confirmar(p, "Avatar", "§bGanhe 4 elementos: Ar, Água, Terra e Fogo! Com eles, mate todos!", API.item(Material.BEACON, 1, "§a§lAvatar"));
				} else if(e.getCurrentItem().getType() == Material.BLAZE_ROD){
					confirmar(p, "Sniper", "§bAcerte flechas na direção que estiver mirando!", API.item(Material.BLAZE_ROD, 1, "§a§lSniper"));
				} else if(e.getCurrentItem().getType() == Material.FIREWORK){
					confirmar(p, "Kangaroo", "§bAnde rapidamente dando jumps!", API.item(Material.FIREWORK, 1, "§a§lKangaroo"));
				} else if(e.getCurrentItem().getType() == Material.COMPASS){
					confirmar(p, "Ninja", "§bTeleporte-se para a última pessoa que você hitou apertando Shift e batendo no ar!", API.item(Material.COMPASS, 1, "§a§lNinja"));
				} else if(e.getCurrentItem().getType() == Material.SLIME_BALL){
					confirmar(p, "C4", "§bExploda todos com sua bomba!", API.item(Material.SLIME_BALL, 1, "§a§lC4"));
				} else if(e.getCurrentItem().getType() == Material.MAGMA_CREAM){
					confirmar(p, "Terrorista", "§bDê um super pulo e quando cair, faça uma explosão!", API.item(Material.MAGMA_CREAM, 1, "§a§lTerrorista"));
				} else if(e.getCurrentItem().getType() == Material.REDSTONE_TORCH_ON){
					confirmar(p, "Flash", "§bTeleporte-se com sua tocha!", API.item(Material.REDSTONE_TORCH_ON, 1, "§a§lFlash"));
				} else if((e.getCurrentItem().getType() == Material.WOOL) && (e.getCurrentItem().getData().getData() == 15)){
					confirmar(p, "Sombra", "§bFique nas sombras por 10 segundos, sem ninguém lhe vendo!", API.item(Material.WOOL, 1, "§a§lSombra", 15));
				} else if(e.getCurrentItem().getType() == Material.CACTUS){
					confirmar(p, "Cactus", "§bQuando te derem hits, você terá a chance de dar dano em quem lhe hitou!", API.item(Material.CACTUS, 1, "§a§lCactus"));
				} else if(e.getCurrentItem().getType() == Material.WATER_BUCKET){
					confirmar(p, "Poseidon", "§bCom seu balde, bote águas no chão e ao passar sobre água, ganhe força e velocidade!", API.item(Material.WATER_BUCKET, 1, "§a§lPoseidon"));
				} else if(e.getCurrentItem().getType() == Material.EYE_OF_ENDER){
					confirmar(p, "Vacuum", "§bPuxe tudo num raio de 15 blocos!", API.item(Material.EYE_OF_ENDER, 1, "§a§lVacuum"));
				}
				
				
			} else if(e.getInventory().getName().equalsIgnoreCase("§4§lTodos os kits")){
				if((e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) && (e.getCurrentItem().getType() != null)){
					abrirSelector(p, "todos");
				}
			}
		} else if(Main.listakits.contains(ChatColor.stripColor(e.getInventory().getName()).toLowerCase())){
			e.setCancelled(true);
			if(!confirmkit.containsKey(p.getName())) {
				p.closeInventory();
				return;
			}
			if((e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) && (e.getCurrentItem().getType() != null)){
				if(e.getSlot() == 0){
					abrirSelector(p, "possui");
				}
				
				if(e.getSlot() == 42){
					p.closeInventory();
					if(confirmkit.get(p.getName()).equalsIgnoreCase("grappler")){
						KitManager.kitGrappler(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("pvp")){
						KitManager.kitPvP(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("legolas")){
						KitManager.kitLegolas(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("stomper")){
						KitManager.kitStomper(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("quickdropper")){
						KitManager.kitQuickdropper(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("sniper")){
						KitManager.kitSniper(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("avatar")){
						KitManager.kitAvatar(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("kangaroo")){
						KitManager.kitKangaroo(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("ninja")){
						KitManager.kitNinja(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("c4")){
						KitManager.kitC4(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("terrorista")){
						KitManager.kitTerrorista(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("flash")){
						KitManager.kitFlash(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("sombra")){
						KitManager.kitSombra(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("cactus")){
						KitManager.kitCactus(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("poseidon")){
						KitManager.kitPoseidon(p);
					} else if(confirmkit.get(p.getName()).equalsIgnoreCase("vacuum")){
						KitManager.kitVacuum(p);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(confirmkit.containsKey(p.getName())){
			if(!Main.listakits.contains(ChatColor.stripColor(p.getOpenInventory().getTitle().toLowerCase()))) {
				confirmkit.remove(p.getName());
			}
		}
	}
}
