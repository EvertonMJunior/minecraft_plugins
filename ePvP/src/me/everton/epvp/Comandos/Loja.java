package me.everton.epvp.Comandos;

import java.util.ArrayList;

import me.everton.epvp.Main;
import me.everton.epvp.MoneyManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Loja implements CommandExecutor, Listener {

	public static int money(String nome) throws Exception {
		return MoneyManager.checkMoney(nome);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("loja")) {
			Boolean lojao = false;
			if(!lojao) {
				ComprarBuycraft.openMenu(p, 1);
				return true;
			}
			Inventory loja = Bukkit.createInventory(p, 27, "§4§lLoja");
			ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1,
					(byte) 0);
			ItemMeta vm = vidro.getItemMeta();
			vm.setDisplayName("§2");
			vidro.setItemMeta(vm);

			for (int i = 0; i < loja.getContents().length; i++) {
				loja.setItem(i, vidro);
			}

			ItemStack buy = new ItemStack(Material.DIAMOND);
			ItemMeta bum = buy.getItemMeta();
			bum.setDisplayName("§aComprar Kits/Vip/Unban/Doar");
			buy.setItemMeta(bum);

			ItemStack kstomper = new ItemStack(Material.IRON_BOOTS);
			ItemMeta metakstomper = kstomper.getItemMeta();
			metakstomper.setDisplayName("§aStomper");
			ArrayList<String> desckstomper = new ArrayList<>();
			desckstomper.add("§5Preço: §l17k");
			metakstomper.setLore(desckstomper);
			kstomper.setItemMeta(metakstomper);
			loja.setItem(0, kstomper);

			ItemStack kgladiator = new ItemStack(Material.IRON_FENCE);
			ItemMeta metakgladiator = kgladiator.getItemMeta();
			metakgladiator.setDisplayName("§aGladiator");
			ArrayList<String> desckgladiator = new ArrayList<>();
			desckgladiator.add("§5Preço: §l16k");
			metakgladiator.setLore(desckgladiator);
			kgladiator.setItemMeta(metakgladiator);
			loja.setItem(2, kgladiator);

			ItemStack kswitcher = new ItemStack(Material.SNOW_BALL);
			ItemMeta metakswitcher = kswitcher.getItemMeta();
			metakswitcher.setDisplayName("§aSwitcher");
			ArrayList<String> desckswitcher = new ArrayList<>();
			desckswitcher.add("§5Preço: §l13k");
			metakswitcher.setLore(desckswitcher);
			kswitcher.setItemMeta(metakswitcher);
			loja.setItem(4, kswitcher);

			ItemStack kflash = new ItemStack(Material.REDSTONE_TORCH_ON);
			ItemMeta metakflash = kflash.getItemMeta();
			metakflash.setDisplayName("§aFlash");
			ArrayList<String> desckflash = new ArrayList<>();
			desckflash.add("§5Preço: §l18k");
			metakflash.setLore(desckflash);
			kflash.setItemMeta(metakflash);
			loja.setItem(6, kflash);

			ItemStack kfireman = new ItemStack(Material.LAVA_BUCKET);
			ItemMeta metakfireman = kfireman.getItemMeta();
			metakfireman.setDisplayName("§aFireman");
			ArrayList<String> desckfireman = new ArrayList<>();
			desckfireman.add("§5Preço: §l10k");
			metakfireman.setLore(desckfireman);
			kfireman.setItemMeta(metakfireman);
			loja.setItem(10, kfireman);

			ItemStack kninja = new ItemStack(Material.COMPASS);
			ItemMeta metakninja = kninja.getItemMeta();
			metakninja.setDisplayName("§aNinja");
			ArrayList<String> desckninja = new ArrayList<>();
			desckninja.add("§5Preço: §l14k");
			metakninja.setLore(desckninja);
			kninja.setItemMeta(metakninja);
			loja.setItem(12, kninja);

			ItemStack kviper = new ItemStack(Material.SPIDER_EYE);
			ItemMeta metakviper = kviper.getItemMeta();
			metakviper.setDisplayName("§aViper");
			ArrayList<String> desckviper = new ArrayList<>();
			desckviper.add("§5Preço: §l12k");
			metakviper.setLore(desckviper);
			kviper.setItemMeta(metakviper);
			loja.setItem(18, kviper);

			ItemStack kryu = new ItemStack(Material.BEACON);
			ItemMeta metakryu = kryu.getItemMeta();
			metakryu.setDisplayName("§aRyu");
			ArrayList<String> desckryu = new ArrayList<>();
			desckryu.add("§5Preço: §l12k");
			metakryu.setLore(desckryu);
			kryu.setItemMeta(metakryu);
			loja.setItem(14, kryu);

			ItemStack kphantom = new ItemStack(Material.FEATHER);
			ItemMeta metakphantom = kphantom.getItemMeta();
			metakphantom.setDisplayName("§aPhantom");
			ArrayList<String> desckphantom = new ArrayList<>();
			desckphantom.add("§5Preço: §l17k");
			metakphantom.setLore(desckphantom);
			kphantom.setItemMeta(metakphantom);
			loja.setItem(20, kphantom);

			ItemStack kanchor = new ItemStack(Material.ANVIL);
			ItemMeta metakanchor = kanchor.getItemMeta();
			metakanchor.setDisplayName("§aAnchor");
			ArrayList<String> desckanchor = new ArrayList<>();
			desckanchor.add("§5Preço: §l15k");
			metakanchor.setLore(desckanchor);
			kanchor.setItemMeta(metakanchor);
			loja.setItem(22, kanchor);

			ItemStack kgrandpa = new ItemStack(Material.STICK);
			kgrandpa.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
			ItemMeta metakgrandpa = kgrandpa.getItemMeta();
			metakgrandpa.setDisplayName("§aGrandpa");
			ArrayList<String> desckgrandpa = new ArrayList<>();
			desckgrandpa.add("§5Preço: §l11k");
			metakgrandpa.setLore(desckgrandpa);
			kgrandpa.setItemMeta(metakgrandpa);
			loja.setItem(24, kgrandpa);

			loja.setItem(8, buy);

			p.openInventory(loja);
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		}
		return false;
	}

	public static void comprarKit(final String kit, final Player p, int valor) throws Exception {
		if (!p.hasPermission("kit." + kit.toLowerCase())) {
			if (money(p.getName()) >= valor) {
				PermissionsEx.getUser(p.getName()).addPermission(
						"kit." + kit.toLowerCase());
				Main.sh.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					@Override
					public void run() {
						p.kickPlayer("§aO kit foi adquirido com sucesso! \n§aPara que nao ocorram bugs, pedimos que relogue!");
					}
				}, 20L);
				p.sendMessage("§aKit §l" + kit + " §aadquirido com sucesso!");
				MoneyManager.removeMoney(p.getName(), valor);
				p.closeInventory();
			} else {
				p.sendMessage("§cVocê nao tem moedas suficientes para comprar este kit! Faltam "
						+ (valor - money(p.getName())) + " moedas.");
				p.closeInventory();
			}
		} else {
			p.closeInventory();
			p.sendMessage("§cVocê ja possui este kit!");
		}
	}

	@EventHandler
	public void aoClicar(InventoryClickEvent e) throws Exception {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName() == "§4§lLoja") {
			p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
			if (e.getCurrentItem().getType() == Material.DIAMOND) {
				p.chat("/comprar");
			}
			if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
				comprarKit("Stomper", p, 17000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.REDSTONE_TORCH_ON) {
				comprarKit("Flash", p, 18000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.SPIDER_EYE) {
				comprarKit("Viper", p, 12000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.BEACON) {
				comprarKit("Ryu", p, 12000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
				comprarKit("Gladiator", p, 16000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.LAVA_BUCKET) {
				comprarKit("Fireman", p, 10000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.FEATHER) {
				comprarKit("Phantom", p, 17000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.SNOW_BALL) {
				comprarKit("Switcher", p, 13000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.COMPASS) {
				comprarKit("Ninja", p, 14000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.ANVIL) {
				comprarKit("Anchor", p, 15000);
				return;
			}
			if (e.getCurrentItem().getType() == Material.STICK) {
				comprarKit("Grandpa", p, 11000);
				return;
			}
			e.setCancelled(true);
		}
	}
}
