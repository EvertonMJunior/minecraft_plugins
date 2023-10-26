package me.everton.hg.kits;

import java.util.ArrayList;

import me.everton.hg.API;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitSelector implements Listener {
	public static ArrayList<KitType> pagina1 = new ArrayList<>();
	public enum Inv{
		SEUS_KITS,OUTROS_KITS;
	}
	
	public static void openSelector(Player p, Inv inv) {
			if(inv == Inv.SEUS_KITS) {
				Inventory in = Bukkit.createInventory(p, 54, "§4Seus Kits");
				for (int i = 0; i < 8; i++) {
					in.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}

				in.setItem(3, API.item(Material.DIAMOND, 1, "§7> §a§lSeus Kits"));
				in.setItem(5, API.item(Material.EMERALD, 1, "§7> §c§lOutros Kits"));
				if (API.playerKits(p).size() > 45) {
					in.setItem(8, API.item(Material.CARPET, 1,
							"§a§lPróxima Página §7>", 5));
				} else {
					in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								pagina1.add(kit);
							}
							in.setItem(i, kit.getItemSelector());
							i++;
						}
					}
				}
				p.openInventory(in);
			} else if(inv == Inv.OUTROS_KITS) {
				Inventory in = Bukkit.createInventory(p, 54, "§4Outros Kits");
				for (int i = 0; i < 8; i++) {
					in.setItem(i, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}

				in.setItem(3, API.item(Material.DIAMOND, 1, "§7> §a§lSeus Kits"));
				in.setItem(5, API.item(Material.EMERALD, 1, "§7> §c§lOutros Kits"));
				if (API.playerKits(p).size() > 45) {
					in.setItem(8, API.item(Material.CARPET, 1,
							"§a§lPróxima Página §7>", 5));
				} else {
					in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (!p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								pagina1.add(kit);
							}
							in.setItem(i, kit.getItemSelector());
							i++;
						}
					}
				}
				p.openInventory(in);
			}
	}
	
	public static void setPage(Player p, Inv inv, int page, Inventory in) {
		if(inv == Inv.SEUS_KITS) {
			if(page == 1) {
				if (API.playerKits(p).size() > 45) {
					in.setItem(8, API.item(Material.CARPET, 1,
							"§a§lPróxima Página §7>", 5));
				} else {
					in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}
				in.setItem(0, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								pagina1.add(kit);
							}
							in.setItem(i, kit.getItemSelector());
							i++;
						}
					}
				}
			} else if(page == 2) {
				in.setItem(0, API.item(Material.CARPET, 1, "§7< §c§lPágina Anterior", 14));
				in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								in.setItem(i, kit.getItemSelector());
								i++;
							}
						}
					}
				}
			} else {
				p.closeInventory();
			}
		} else if(inv == Inv.OUTROS_KITS) {
			if(page == 1) {
				if (API.playerKits(p).size() > 45) {
					in.setItem(8, API.item(Material.CARPET, 1,
							"§a§lPróxima Página §7>", 5));
				} else {
					in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				}
				in.setItem(0, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (!p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								pagina1.add(kit);
							}
							in.setItem(i, kit.getItemSelector());
							i++;
						}
					}
				}
			} else if(page == 2) {
				in.setItem(0, API.item(Material.CARPET, 1, "§7< §c§lPágina Anterior", 14));
				in.setItem(8, API.item(Material.STAINED_GLASS_PANE, 1, " ", 14));
				int i = 9;
				for (KitType kit : KitType.values()) {
					if (kit.name() == kit.name()) {
						if (i == 54) {
							break;
						}
						if (!p.hasPermission("kit." + kit.name().toLowerCase())) {
							if (!pagina1.contains(kit)) {
								in.setItem(i, kit.getItemSelector());
								i++;
							}
						}
					}
				}
			} else {
				p.closeInventory();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void selectKit(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory in = e.getInventory();
		Material m = e.getCurrentItem().getType();
		ItemStack i = e.getCurrentItem();
		if (e.getInventory().getTitle().equalsIgnoreCase("§4Seus Kits")) {
			e.setCancelled(true);
			if(m == Material.AIR) {
				return;
			}
			if ((m != Material.CARPET) && (m != Material.STAINED_GLASS_PANE)) {
				for (KitType kit : KitType.values()) {
					if (ChatColor
							.stripColor(
									i.getItemMeta()
											.getDisplayName()).toLowerCase()
							.equalsIgnoreCase(kit.name().toLowerCase())) {
						KitManager.giveKit(p, kit);
						break;
					}
				}
			} else if (m == Material.CARPET) {
				if(i.getData().getData() == 5) {
					setPage(p, Inv.SEUS_KITS, 2, in);
				} else if(i.getData().getData() == 14) {
					setPage(p, Inv.SEUS_KITS, 1, in);
				}
			}
			if(ChatColor.stripColor(i.getItemMeta().getDisplayName()).contains("Outros Kits")) {
				openSelector(p, Inv.OUTROS_KITS);
			}
		} else if (e.getInventory().getTitle()
				.equalsIgnoreCase("§4Outros Kits")) {
			e.setCancelled(true);
			if (m == Material.CARPET) {
				if(i.getData().getData() == 5) {
					setPage(p, Inv.OUTROS_KITS, 2, in);
				} else if(i.getData().getData() == 14) {
					setPage(p, Inv.OUTROS_KITS, 1, in);
				}
			}
			
			if(ChatColor.stripColor(i.getItemMeta().getDisplayName()).contains("Seus Kits")) {
				openSelector(p, Inv.SEUS_KITS);
			}
		}
	}
}
