package me.everton.WocPvP.Comandos;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kit implements CommandExecutor,Listener {
	public static Inventory kits1;
	public static Inventory kits2;
	public static Inventory hg2;
	public static Inventory hg1;

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("kitsel")) {
			InvKits((Player) sender, "1");
		}
		return false;
	}

	public static ChatColor cor(Player p, String kit) {
		if (p.hasPermission("kit." + kit)) {
			return ChatColor.GREEN;
		}
		return ChatColor.RED;
	}

	public static ChatColor corHG(Player p) {
		if ((p.hasPermission("tag.vip")) || (p.hasPermission("tag.pro"))
				|| (p.hasPermission("tag.youtuber"))
				|| (p.hasPermission("tag.trial"))
				|| (p.hasPermission("tag.mod"))
				|| (p.hasPermission("tag.admin"))
				|| (p.hasPermission("tag.dono")) || (p.hasPermission("*"))) {
			return ChatColor.GREEN;
		}
		return ChatColor.RED;
	}

	public static void InvKits(Player p, String kits) {
		p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 1F, 1F);
		kits1 = Bukkit.createInventory(p, 54, "§4Kits - 1");
		kits2 = Bukkit.createInventory(p, 54, "§4Kits - 2");
		hg1 = Bukkit.createInventory(p, 54, "§4Kits HG - Free");
		hg2 = Bukkit.createInventory(p, 54, "§4Kits HG - Vips");
		ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1,
				(byte) 0);
		ItemMeta vm = vidro.getItemMeta();
		vm.setDisplayName("§2");
		vidro.setItemMeta(vm);
		for (int i = 0; i < kits1.getContents().length; i++) {
			kits1.setItem(i, vidro);
			kits2.setItem(i, vidro);
			hg1.setItem(i, vidro);
			hg2.setItem(i, vidro);
		}

		ItemStack ir = new ItemStack(Material.CARPET, 1, (byte) 14);
		ItemMeta im = ir.getItemMeta();
		im.setDisplayName("§c--> Próxima Página -->");
		ir.setItemMeta(im);

		ItemStack voltar = new ItemStack(Material.CARPET, 1, (byte) 5);
		ItemMeta vom = voltar.getItemMeta();
		vom.setDisplayName("§2<-- Voltar <--");
		voltar.setItemMeta(vom);

		ItemStack vip = new ItemStack(Material.DIAMOND);
		ItemMeta vipm = vip.getItemMeta();
		vipm.setDisplayName("§b§lKits VIP");
		vip.setItemMeta(vipm);

		ItemStack vin = new ItemStack(Material.VINE);
		ItemMeta vim = vip.getItemMeta();
		vim.setDisplayName(" ");
		vin.setItemMeta(vim);

		ItemStack grade = new ItemStack(Material.IRON_FENCE);
		ItemMeta gm = vip.getItemMeta();
		gm.setDisplayName(" ");
		grade.setItemMeta(gm);

		ItemStack normal = new ItemStack(Material.IRON_INGOT);
		ItemMeta nm = normal.getItemMeta();
		nm.setDisplayName("§c§lKits FREE");
		normal.setItemMeta(nm);

		ItemStack kpvp = new ItemStack(Material.STONE_SWORD);
		ItemMeta metakpvp = kpvp.getItemMeta();
		metakpvp.setDisplayName(cor(p, "pvp") + "PvP");
		ArrayList<String> desckpvp = new ArrayList<>();
		desckpvp.add(ChatColor.WHITE + "Ganhe uma espada de pedra!");
		metakpvp.setLore(desckpvp);
		kpvp.setItemMeta(metakpvp);
		kits1.setItem(13, kpvp);

		ItemStack karcher = new ItemStack(Material.BOW);
		ItemMeta metakarcher = karcher.getItemMeta();
		metakarcher.setDisplayName(cor(p, "arqueiro") + "Arqueiro");
		ArrayList<String> desckarcher = new ArrayList<>();
		desckarcher.add(ChatColor.WHITE
				+ "Mate seus inimigos com seu arco e flecha");
		metakarcher.setLore(desckarcher);
		karcher.setItemMeta(metakarcher);
		kits1.setItem(20, karcher);

		ItemStack kshooter = new ItemStack(Material.NETHER_STAR);
		ItemMeta metakshooter = kshooter.getItemMeta();
		metakshooter.setDisplayName(cor(p, "shooter") + "Shooter");
		ArrayList<String> desckshooter = new ArrayList<>();
		desckshooter.add(ChatColor.WHITE
				+ "Jogue efeitos de wither em seu inimigo");
		metakshooter.setLore(desckshooter);
		kshooter.setItemMeta(metakshooter);
		kits1.setItem(21, kshooter);

		ItemStack kfrosty = new ItemStack(Material.SNOW_BLOCK);
		ItemMeta metakfrosty = kfrosty.getItemMeta();
		metakfrosty.setDisplayName(cor(p, "frosty") + "Frosty");
		ArrayList<String> desckfrosty = new ArrayList<>();
		desckfrosty.add(ChatColor.WHITE
				+ "Ganhe velocidade e regeneracao na neve!");
		metakfrosty.setLore(desckfrosty);
		kfrosty.setItemMeta(metakfrosty);
		kits1.setItem(22, kfrosty);

		ItemStack kpyro = new ItemStack(Material.FIREWORK_CHARGE);
		ItemMeta metakpyro = kpyro.getItemMeta();
		metakpyro.setDisplayName(cor(p, "pyro") + "Pyro");
		ArrayList<String> desckpyro = new ArrayList<>();
		desckpyro.add(ChatColor.WHITE + "Jogue bolas de fogo!");
		metakpyro.setLore(desckpyro);
		kpyro.setItemMeta(metakpyro);
		kits1.setItem(23, kpyro);

		ItemStack kninja = new ItemStack(Material.COMPASS);
		ItemMeta metakninja = kninja.getItemMeta();
		metakninja.setDisplayName(cor(p, "ninja") + "Ninja");
		ArrayList<String> desckninja = new ArrayList<>();
		desckninja.add(ChatColor.WHITE + "Bata em um jogador e se agache");
		desckninja.add(ChatColor.WHITE + "para se teleportar ate ele");
		metakninja.setLore(desckninja);
		kninja.setItemMeta(metakninja);
		kits1.setItem(24, kninja);

		ItemStack kfisherman = new ItemStack(Material.FISHING_ROD);
		ItemMeta metakfisherman = kfisherman.getItemMeta();
		metakfisherman.setDisplayName(cor(p, "fisherman") + "Fisherman");
		ArrayList<String> desckfisherman = new ArrayList<>();
		desckfisherman.add(ChatColor.WHITE
				+ "Use sua vara de pesca para puxar jogadores!");
		metakfisherman.setLore(desckfisherman);
		kfisherman.setItemMeta(metakfisherman);
		kits1.setItem(28, kfisherman);

		ItemStack ksoldado = new ItemStack(Material.IRON_CHESTPLATE);
		ItemMeta metaksoldado = ksoldado.getItemMeta();
		metaksoldado.setDisplayName(cor(p, "soldado") + "Soldado");
		ArrayList<String> descksoldado = new ArrayList<>();
		descksoldado.add(ChatColor.WHITE + "Use sua espada para saltar!");
		metaksoldado.setLore(descksoldado);
		ksoldado.setItemMeta(metaksoldado);
		kits1.setItem(29, ksoldado);

		ItemStack ktrocador = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta metaktrocador = ktrocador.getItemMeta();
		metaktrocador.setDisplayName(cor(p, "trocador") + "Trocador");
		ArrayList<String> descktrocador = new ArrayList<>();
		descktrocador
				.add(ChatColor.WHITE + "Aperte shift e mude sua armadura!");
		metaktrocador.setLore(descktrocador);
		ktrocador.setItemMeta(metaktrocador);
		kits1.setItem(30, ktrocador);

		ItemStack ktank = new ItemStack(Material.TNT);
		ItemMeta metaktank = ktank.getItemMeta();
		metaktank.setDisplayName(cor(p, "tank") + "Tank");
		ArrayList<String> descktank = new ArrayList<>();
		descktank.add(ChatColor.WHITE + "Ao matar um jogador");
		descktank.add(ChatColor.WHITE + "ele ira explodir!");
		metaktank.setLore(descktank);
		ktank.setItemMeta(metaktank);
		kits1.setItem(31, ktank);

		ItemStack kdarkmage = new ItemStack(Material.COAL);
		ItemMeta metakdarkmage = kdarkmage.getItemMeta();
		metakdarkmage.setDisplayName(cor(p, "darkmage") + "Darkmage");
		ArrayList<String> desckdarkmage = new ArrayList<>();
		desckdarkmage.add(ChatColor.WHITE
				+ "Ao bater em jogadores Você tem 33% de chance");
		desckdarkmage.add(ChatColor.WHITE
				+ "de dar a eles o efeito de Cegueira!");
		metakdarkmage.setLore(desckdarkmage);
		kdarkmage.setItemMeta(metakdarkmage);
		kits1.setItem(32, kdarkmage);

		ItemStack krhino = new ItemStack(Material.SAPLING);
		krhino.setDurability((short) 5);
		ItemMeta metakrhino = krhino.getItemMeta();
		metakrhino.setDisplayName(cor(p, "rhino") + "Rhino");
		ArrayList<String> desckrhino = new ArrayList<>();
		desckrhino.add(ChatColor.WHITE + "Se abaixe, carregue sua força e");
		desckrhino.add(ChatColor.WHITE + "mande o inimigo pelos ares!");
		metakrhino.setLore(desckrhino);
		krhino.setItemMeta(metakrhino);
		kits1.setItem(33, krhino);

		ItemStack kposeidon = new ItemStack(Material.WATER_BUCKET);
		ItemMeta metakposeidon = kposeidon.getItemMeta();
		metakposeidon.setDisplayName(cor(p, "poseidon") + "Poseidon");
		ArrayList<String> desckposeidon = new ArrayList<>();
		desckposeidon.add(ChatColor.WHITE
				+ "Ao entrar em contato com a agua ganhe forcas!");
		metakposeidon.setLore(desckposeidon);
		kposeidon.setItemMeta(metakposeidon);
		kits1.setItem(34, kposeidon);

		ItemStack kgranadier = new ItemStack(Material.DISPENSER);
		ItemMeta metakgranadier = kgranadier.getItemMeta();
		metakgranadier.setDisplayName(cor(p, "granadier") + "Granadier");
		ArrayList<String> desckgranadier = new ArrayList<>();
		desckgranadier.add(ChatColor.WHITE
				+ "Lance granadas contra seus inimigos!");
		metakgranadier.setLore(desckgranadier);
		kgranadier.setItemMeta(metakgranadier);
		kits1.setItem(37, kgranadier);

		ItemStack klauncher = new ItemStack(Material.SPONGE);
		ItemMeta metaklauncher = klauncher.getItemMeta();
		metaklauncher.setDisplayName(cor(p, "launcher") + "Launcher");
		ArrayList<String> descklauncher = new ArrayList<>();
		descklauncher.add(ChatColor.WHITE + "Com sua vara de pesca");
		descklauncher.add(ChatColor.WHITE + "Jogue os players pra cima!");
		metaklauncher.setLore(descklauncher);
		klauncher.setItemMeta(metaklauncher);
		kits1.setItem(38, klauncher);

		ItemStack kmilkman = new ItemStack(Material.MILK_BUCKET);
		ItemMeta metakmilkman = kmilkman.getItemMeta();
		metakmilkman.setDisplayName(cor(p, "milkman") + "Milkman");
		ArrayList<String> desckmilkman = new ArrayList<>();
		desckmilkman.add(ChatColor.WHITE
				+ "Ao clicar em seu balde de leite Você tera");
		desckmilkman.add(ChatColor.WHITE + "Efeitos!");
		metakmilkman.setLore(desckmilkman);
		kmilkman.setItemMeta(metakmilkman);
		kits1.setItem(39, kmilkman);

		ItemStack kturtle = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta metakturtle = kturtle.getItemMeta();
		metakturtle.setDisplayName(cor(p, "turtle") + "Turtle");
		ArrayList<String> desckturtle = new ArrayList<>();
		desckturtle.add(ChatColor.WHITE + "Ao cair de qualquer lugar");
		desckturtle
				.add(ChatColor.WHITE + "Segurando o shift Você nao morrera!");
		metakturtle.setLore(desckturtle);
		kturtle.setItemMeta(metakturtle);
		kits1.setItem(40, kturtle);

		ItemStack kcamel = new ItemStack(Material.SAND);
		ItemMeta metakcamel = kcamel.getItemMeta();
		metakcamel.setDisplayName(cor(p, "camel") + "Camel");
		ArrayList<String> desckcamel = new ArrayList<>();
		desckcamel.add(ChatColor.WHITE
				+ "Ganhe velocidade e regeneracao na areia");
		metakcamel.setLore(desckcamel);
		kcamel.setItemMeta(metakcamel);
		kits1.setItem(41, kcamel);

		ItemStack kthor = new ItemStack(Material.STONE_AXE);
		ItemMeta metakthor = kthor.getItemMeta();
		metakthor.setDisplayName(cor(p, "thor") + "Thor");
		ArrayList<String> desckthor = new ArrayList<>();
		desckthor.add(ChatColor.WHITE + "Solte raios com seu machado!");
		metakthor.setLore(desckthor);
		kthor.setItemMeta(metakthor);
		kits1.setItem(42, kthor);

		ItemStack kspecialist = new ItemStack(Material.EXP_BOTTLE);
		ItemMeta metakspecialist = kspecialist.getItemMeta();
		metakspecialist.setDisplayName(cor(p, "specialist") + "Specialist");
		ArrayList<String> desckspecialist = new ArrayList<>();
		desckspecialist.add(ChatColor.WHITE
				+ "Tenha uma mesa de encantamento portatil!");
		metakspecialist.setLore(desckspecialist);
		kspecialist.setItemMeta(metakspecialist);
		kits1.setItem(43, kspecialist);

		ItemStack kendermage = new ItemStack(Material.PORTAL);
		ItemMeta metakendermage = kendermage.getItemMeta();
		metakendermage.setDisplayName(cor(p, "endermage") + "Endermage");
		ArrayList<String> desckendermage = new ArrayList<>();
		desckendermage.add(ChatColor.WHITE + "Puxe jogadores com seu portal!");
		metakendermage.setLore(desckendermage);
		kendermage.setItemMeta(metakendermage);
		kits1.setItem(46, kendermage);

		ItemStack kstomper = new ItemStack(Material.IRON_BOOTS);
		ItemMeta metakstomper = kstomper.getItemMeta();
		metakstomper.setDisplayName(cor(p, "stomper") + "Stomper");
		ArrayList<String> desckstomper = new ArrayList<>();
		desckstomper
				.add(ChatColor.WHITE + "Receba 2 coracoes de dano de queda");
		desckstomper.add(ChatColor.WHITE + "e ao cair em cima de jogadores");
		desckstomper.add(ChatColor.WHITE
				+ "Você acabara matando-os a menos que estejam no shift!");
		metakstomper.setLore(desckstomper);
		kstomper.setItemMeta(metakstomper);
		kits1.setItem(47, kstomper);

		ItemStack kkangaroo = new ItemStack(Material.FIREWORK);
		ItemMeta metakkangaroo = kkangaroo.getItemMeta();
		metakkangaroo.setDisplayName(cor(p, "kangaroo") + "Kangaroo");
		ArrayList<String> desckkangaroo = new ArrayList<>();
		desckkangaroo.add(ChatColor.WHITE + "Com seu firework");
		desckkangaroo.add(ChatColor.WHITE + "De pulos duplos!");
		desckkangaroo.add(ChatColor.WHITE
				+ "Um otimo kit para fugir de inimigos");
		metakkangaroo.setLore(desckkangaroo);
		kkangaroo.setItemMeta(metakkangaroo);
		kits1.setItem(48, kkangaroo);

		ItemStack kreaper = new ItemStack(Material.WOOD_HOE);
		ItemMeta metakreaper = kreaper.getItemMeta();
		metakreaper.setDisplayName(cor(p, "reaper") + "Reaper");
		ArrayList<String> desckreaper = new ArrayList<>();
		desckreaper.add(ChatColor.WHITE + "Com sua enxada");
		desckreaper.add(ChatColor.WHITE + "bata em jogadores e evenene-os!");
		metakreaper.setLore(desckreaper);
		kreaper.setItemMeta(metakreaper);
		kits1.setItem(49, kreaper);

		ItemStack kfireman = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta metakfireman = kfireman.getItemMeta();
		metakfireman.setDisplayName(cor(p, "fireman") + "Fireman");
		ArrayList<String> desckfireman = new ArrayList<>();
		desckfireman.add(ChatColor.WHITE + "Use seu kit para");
		desckfireman.add(ChatColor.WHITE + "nao receber dano de fogo!");
		metakfireman.setLore(desckfireman);
		kfireman.setItemMeta(metakfireman);
		kits1.setItem(50, kfireman);

		ItemStack kanchor = new ItemStack(Material.ANVIL);
		ItemMeta metakanchor = kanchor.getItemMeta();
		metakanchor.setDisplayName(cor(p, "anchor") + "Anchor");
		ArrayList<String> desckanchor = new ArrayList<>();
		desckanchor.add(ChatColor.WHITE + "Com esse kit Você nao leva");
		desckanchor.add(ChatColor.WHITE + "nem da knockback com hits!");
		metakanchor.setLore(desckanchor);
		kanchor.setItemMeta(metakanchor);
		kits1.setItem(51, kanchor);

		ItemStack kflash = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta metakflash = kflash.getItemMeta();
		metakflash.setDisplayName(cor(p, "flash") + "Flash");
		ArrayList<String> desckflash = new ArrayList<>();
		desckflash.add(ChatColor.WHITE + "Use seu kit para");
		desckflash.add(ChatColor.WHITE + "Teleportar-se!");
		metakflash.setLore(desckflash);
		kflash.setItemMeta(metakflash);
		kits1.setItem(52, kflash);

		// Pagina 2
		// Pagina 2
		// Pagina 2
		// Pagina 2
		// Pagina 2
		// Pagina 2

		ItemStack kviper = new ItemStack(Material.SPIDER_EYE);
		ItemMeta metakviper = kviper.getItemMeta();
		metakviper.setDisplayName(cor(p, "viper") + "Viper");
		ArrayList<String> desckviper = new ArrayList<>();
		desckviper.add(ChatColor.WHITE
				+ "Ao bater em jogadores Você tem 33% de chance");
		desckviper.add(ChatColor.WHITE + "de dar a eles o efeito de Veneno!");
		metakviper.setLore(desckviper);
		kviper.setItemMeta(metakviper);
		kits2.setItem(13, kviper);

		ItemStack kmonk = new ItemStack(Material.BLAZE_ROD);
		ItemMeta metakmonk = kmonk.getItemMeta();
		metakmonk.setDisplayName(cor(p, "monk") + "Monk");
		ArrayList<String> desckmonk = new ArrayList<>();
		desckmonk.add(ChatColor.WHITE + "Coloque items em um local");
		desckmonk.add(ChatColor.WHITE + "aleatorio no inventario do inimigo!");
		metakmonk.setLore(desckmonk);
		kmonk.setItemMeta(metakmonk);
		kits2.setItem(20, kmonk);

		ItemStack ksnail = new ItemStack(Material.SOUL_SAND);
		ItemMeta metaksnail = ksnail.getItemMeta();
		metaksnail.setDisplayName(cor(p, "snail") + "Snail");
		ArrayList<String> descksnail = new ArrayList<>();
		descksnail.add(ChatColor.WHITE
				+ "Ao bater em jogadores Você tem 33% de chance");
		descksnail.add(ChatColor.WHITE + "de dar a eles o efeito de Lentidao!");
		metaksnail.setLore(descksnail);
		ksnail.setItemMeta(metaksnail);
		kits2.setItem(21, ksnail);

		ItemStack kneji = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta metakneji = kneji.getItemMeta();
		metakneji.setDisplayName(cor(p, "neji") + "Neji");
		ArrayList<String> desckneji = new ArrayList<>();
		desckneji.add(ChatColor.WHITE + "Faça um HAKKESHOU KAITEN (rotacao) e");
		desckneji.add(ChatColor.WHITE + "afaste quem estiver por perto!");
		metakneji.setLore(desckneji);
		kneji.setItemMeta(metakneji);
		kits2.setItem(22, kneji);

		ItemStack kwither = new ItemStack(Material.SKULL_ITEM);
		kwither.setDurability((short) 1);
		ItemMeta metakwither = kwither.getItemMeta();
		metakwither.setDisplayName(cor(p, "wither") + "Wither");
		ArrayList<String> desckwither = new ArrayList<>();
		desckwither.add(ChatColor.WHITE
				+ "Ao bater em jogadores Você tem 33% de chance");
		desckwither.add(ChatColor.WHITE + "de dar a eles o efeito do Wither!");
		metakwither.setLore(desckwither);
		kwither.setItemMeta(metakwither);
		kits2.setItem(23, kwither);

		ItemStack kgladiator = new ItemStack(Material.IRON_FENCE);
		ItemMeta metakgladiator = kgladiator.getItemMeta();
		metakgladiator.setDisplayName(cor(p, "gladiator") + "Gladiator");
		ArrayList<String> desckgladiator = new ArrayList<>();
		desckgladiator.add(ChatColor.WHITE
				+ "Crie uma arena e fique frente a frente");
		desckgladiator.add(ChatColor.WHITE + "contra os seus inimigos!");
		metakgladiator.setLore(desckgladiator);
		kgladiator.setItemMeta(metakgladiator);
		kits2.setItem(24, kgladiator);

		ItemStack kswitcher = new ItemStack(Material.SNOW_BALL);
		ItemMeta metakswitcher = kswitcher.getItemMeta();
		metakswitcher.setDisplayName(cor(p, "switcher") + "Switcher");
		ArrayList<String> desckswitcher = new ArrayList<>();
		desckswitcher.add(ChatColor.WHITE
				+ "Use suas bolas de neve para trocar");
		desckswitcher.add(ChatColor.WHITE + "de lugar com seus jogadores!");
		metakswitcher.setLore(desckswitcher);
		kswitcher.setItemMeta(metakswitcher);
		kits2.setItem(28, kswitcher);

		ItemStack kjumper = new ItemStack(Material.TRAP_DOOR);
		ItemMeta metakjumper = kjumper.getItemMeta();
		metakjumper.setDisplayName(cor(p, "jumper") + "Jumper");
		ArrayList<String> desckjumper = new ArrayList<>();
		desckjumper.add(ChatColor.WHITE + "Use esse kit e jogue");
		desckjumper.add(ChatColor.WHITE + "seus inimigos para cima!");
		metakjumper.setLore(desckjumper);
		kjumper.setItemMeta(metakjumper);
		kits2.setItem(29, kjumper);

		ItemStack kvitality = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta metakvitality = kvitality.getItemMeta();
		metakvitality.setDisplayName(cor(p, "vitality") + "Vitality");
		ArrayList<String> desckvitality = new ArrayList<>();
		desckvitality
				.add(ChatColor.WHITE + "Ao matar um player seu inventario");
		desckvitality.add(ChatColor.WHITE + "será preenchido com sopas!");
		metakvitality.setLore(desckvitality);
		kvitality.setItemMeta(metakvitality);
		kits2.setItem(30, kvitality);

		ItemStack kmadman = new ItemStack(Material.JACK_O_LANTERN);
		ItemMeta metakmadman = kmadman.getItemMeta();
		metakmadman.setDisplayName(cor(p, "madman") + "Madman");
		ArrayList<String> desckmadman = new ArrayList<>();
		desckmadman.add(ChatColor.WHITE
				+ "Ao bater em jogadores Você tem 33% de chance");
		desckmadman.add(ChatColor.WHITE + "de dar a eles Nausea e Cegueira.");
		metakmadman.setLore(desckmadman);
		kmadman.setItemMeta(metakmadman);
		kits2.setItem(31, kmadman);

		ItemStack kviking = new ItemStack(Material.GOLD_AXE);
		ItemMeta metakviking = kviking.getItemMeta();
		metakviking.setDisplayName(cor(p, "viking") + "Viking");
		ArrayList<String> desckviking = new ArrayList<>();
		desckviking.add(ChatColor.WHITE + "Ao bater em jogadores com machados");
		desckviking.add(ChatColor.WHITE + "o dano será 2 vezes maior.");
		metakviking.setLore(desckviking);
		kviking.setItemMeta(metakviking);
		kits2.setItem(32, kviking);

		ItemStack kgrandpa = new ItemStack(Material.STICK);
		kgrandpa.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		ItemMeta metakgrandpa = kgrandpa.getItemMeta();
		metakgrandpa.setDisplayName(cor(p, "grandpa") + "Grandpa");
		ArrayList<String> desckgrandpa = new ArrayList<>();
		desckgrandpa.add(ChatColor.WHITE
				+ "Aplique Knockback nos seus adversários!");
		metakgrandpa.setLore(desckgrandpa);
		kgrandpa.setItemMeta(metakgrandpa);
		kits2.setItem(33, kgrandpa);

		ItemStack kquickdropper = new ItemStack(Material.BOWL);
		ItemMeta metakquickdropper = kquickdropper.getItemMeta();
		metakquickdropper.setDisplayName(cor(p, "quickdropper")
				+ "QuickDropper");
		ArrayList<String> desckquickdropper = new ArrayList<>();
		desckquickdropper
				.add(ChatColor.WHITE + "Ao tomar uma sopa o pote será");
		desckquickdropper.add(ChatColor.WHITE + "dropado automaticamente!");
		metakquickdropper.setLore(desckquickdropper);
		kquickdropper.setItemMeta(metakquickdropper);
		kits2.setItem(34, kquickdropper);

		ItemStack kteleporter = new ItemStack(Material.ENDER_PEARL);
		ItemMeta metakteleporter = kteleporter.getItemMeta();
		metakteleporter.setDisplayName(cor(p, "teleporter") + "Teleporter");
		ArrayList<String> desckteleporter = new ArrayList<>();
		desckteleporter.add(ChatColor.WHITE
				+ "Se teleporte com perolas do fim!");
		metakteleporter.setLore(desckteleporter);
		kteleporter.setItemMeta(metakteleporter);
		kits2.setItem(37, kteleporter);

		ItemStack kspiderman = new ItemStack(Material.WEB);
		ItemMeta metakspiderman = kspiderman.getItemMeta();
		metakspiderman.setDisplayName(cor(p, "spiderman") + "SpiderMan");
		ArrayList<String> desckspiderman = new ArrayList<>();
		desckspiderman.add(ChatColor.WHITE + "Atire teia em seus inimigos!");
		metakspiderman.setLore(desckspiderman);
		kspiderman.setItemMeta(metakspiderman);
		kits2.setItem(38, kspiderman);

		ItemStack kbarbarian = new ItemStack(Material.IRON_ORE);
		ItemMeta metakbarbarian = kbarbarian.getItemMeta();
		metakbarbarian.setDisplayName(cor(p, "barbarian") + "Barbarian");
		ArrayList<String> desckbarbarian = new ArrayList<>();
		desckbarbarian.add(ChatColor.WHITE + "Mate um player com a espada");
		desckbarbarian.add(ChatColor.WHITE + "e faça um UPGRADE!");
		metakbarbarian.setLore(desckbarbarian);
		kbarbarian.setItemMeta(metakbarbarian);
		kits2.setItem(39, kbarbarian);

		ItemStack kberserker = new ItemStack(Material.REDSTONE);
		ItemMeta metakberserker = kberserker.getItemMeta();
		metakberserker.setDisplayName(cor(p, "berserker") + "Berserker");
		ArrayList<String> desckberserker = new ArrayList<>();
		desckberserker.add(ChatColor.WHITE + "Mate um player e ganhe força!");
		metakberserker.setLore(desckberserker);
		kberserker.setItemMeta(metakberserker);
		kits2.setItem(40, kberserker);

		ItemStack kindio = new ItemStack(Material.PUMPKIN_SEEDS);
		ItemMeta metakindio = kindio.getItemMeta();
		metakindio.setDisplayName(cor(p, "indio") + "Indio");
		ArrayList<String> desckindio = new ArrayList<>();
		desckindio.add(ChatColor.WHITE
				+ "Atire dardos venenosos em seus inimigos!");
		metakindio.setLore(desckindio);
		kindio.setItemMeta(metakindio);
		kits2.setItem(41, kindio);

		ItemStack kryu = new ItemStack(Material.BEACON);
		ItemMeta metakryu = kryu.getItemMeta();
		metakryu.setDisplayName(cor(p, "ryu") + "Ryu");
		ArrayList<String> desckryu = new ArrayList<>();
		desckryu.add(ChatColor.WHITE + "Dê um HADOUKEN em seus inimigos!");
		metakryu.setLore(desckryu);
		kryu.setItemMeta(metakryu);
		kits2.setItem(42, kryu);

		ItemStack klobisomem = new ItemStack(Material.MONSTER_EGG);
		ItemMeta metaklobisomem = klobisomem.getItemMeta();
		metaklobisomem.setDisplayName(cor(p, "lobisomem") + "Lobisomem");
		ArrayList<String> descklobisomem = new ArrayList<>();
		descklobisomem.add(ChatColor.WHITE + "Ganhe efeitos e receba ajuda de");
		descklobisomem.add(ChatColor.WHITE + "seus amigos Lobos!");
		metaklobisomem.setLore(descklobisomem);
		klobisomem.setItemMeta(metaklobisomem);
		kits2.setItem(43, klobisomem);

		ItemStack kphantom = new ItemStack(Material.FEATHER);
		ItemMeta metakphantom = kphantom.getItemMeta();
		metakphantom.setDisplayName(cor(p, "phantom") + "Phantom");
		ArrayList<String> desckphantom = new ArrayList<>();
		desckphantom.add(ChatColor.WHITE + "Use a pena e voe por 5 segundos!");
		metakphantom.setLore(desckphantom);
		kphantom.setItemMeta(metakphantom);
		kits2.setItem(46, kphantom);

		ItemStack kneo = new ItemStack(Material.EGG);
		ItemMeta metakneo = kneo.getItemMeta();
		metakneo.setDisplayName(cor(p, "neo") + "Neo");
		ArrayList<String> desckneo = new ArrayList<>();
		desckneo.add(ChatColor.WHITE
				+ "Os projeteis atirados contra Você nao lhe");
		desckneo.add(ChatColor.WHITE + "causarao dano e voltarao ao atirador!");
		metakneo.setLore(desckneo);
		kneo.setItemMeta(metakneo);
		kits2.setItem(47, kneo);

		ItemStack khulk = new ItemStack(Material.SADDLE);
		ItemMeta metakhulk = khulk.getItemMeta();
		metakhulk.setDisplayName(cor(p, "hulk") + "Hulk");
		ArrayList<String> desckhulk = new ArrayList<>();
		desckhulk.add(ChatColor.WHITE + "Pegue e lance seus inimigos!");
		metakhulk.setLore(desckhulk);
		khulk.setItemMeta(metakhulk);
		kits2.setItem(48, khulk);

		ItemStack kassassin = new ItemStack(Material.GOLDEN_APPLE);
		kassassin.setDurability((short) 1);
		ItemMeta metakassassin = kassassin.getItemMeta();
		metakassassin.setDisplayName(cor(p, "assassin") + "Assassin");
		ArrayList<String> desckassassin = new ArrayList<>();
		desckassassin.add(ChatColor.WHITE + "30% de chance de dar um ");
		desckassassin.add(ChatColor.WHITE + "golpe critico ao bater! ");
		metakassassin.setLore(desckassassin);
		kassassin.setItemMeta(metakassassin);
		kits2.setItem(49, kassassin);

		ItemStack kurgal = new ItemStack(Material.POTION, 1, (short) 8201);
		ItemMeta metakurgal = kurgal.getItemMeta();
		metakurgal.setDisplayName(ChatColor.DARK_RED + "Urgal");
		ArrayList<String> desckurgal = new ArrayList<>();
		desckurgal.add(ChatColor.WHITE + "Receba 3 pocoes de forca");
		metakurgal.setLore(desckurgal);
		kurgal.setItemMeta(metakurgal);
		kits2.setItem(50, kurgal);

		ItemStack kskeleton = new ItemStack(Material.BONE);
		ItemMeta metakskeleton = kskeleton.getItemMeta();
		metakskeleton.setDisplayName(ChatColor.DARK_RED + "Skeleton");
		ArrayList<String> desckskeleton = new ArrayList<>();
		desckskeleton.add(ChatColor.WHITE + "Apos bater em qualquer jogador");
		desckskeleton.add(ChatColor.WHITE + "Você o matara!");
		metakskeleton.setLore(desckskeleton);
		kskeleton.setItemMeta(metakskeleton);
		kits2.setItem(51, kskeleton);

		ItemStack kghost = new ItemStack(Material.IRON_HOE);
		ItemMeta metakghost = kghost.getItemMeta();
		metakghost.setDisplayName(ChatColor.DARK_RED + "Ghost");
		ArrayList<String> desckghost = new ArrayList<>();
		desckghost.add(ChatColor.WHITE + "Transforme em um fantasma e");
		desckghost.add(ChatColor.WHITE + "assombre seus inimigos!");
		metakghost.setLore(desckghost);
		kghost.setItemMeta(metakghost);
		kits2.setItem(52, kghost);

		kits1.setItem(8, ir);
		kits2.setItem(0, voltar);

		hg1.setItem(0, normal);
		hg2.setItem(8, vip);
		hg2.setItem(0, normal);
		hg1.setItem(8, vip);

		for (int i = 1; i < 8; i++) {
			hg1.setItem(i, grade);
		}

		for (int i = 46; i < 53; i++) {
			hg1.setItem(i, grade);
		}

		for (int i = 1; i < 8; i++) {
			hg2.setItem(i, grade);
		}

		for (int i = 46; i < 53; i++) {
			hg2.setItem(i, grade);
		}

		hg1.setItem(9, vin);
		hg1.setItem(17, vin);
		hg1.setItem(18, vin);
		hg1.setItem(26, vin);
		hg1.setItem(27, vin);
		hg1.setItem(35, vin);
		hg1.setItem(36, vin);
		hg1.setItem(44, vin);
		hg1.setItem(45, vin);
		hg1.setItem(53, vin);

		hg2.setItem(9, vin);
		hg2.setItem(17, vin);
		hg2.setItem(18, vin);
		hg2.setItem(26, vin);
		hg2.setItem(27, vin);
		hg2.setItem(35, vin);
		hg2.setItem(36, vin);
		hg2.setItem(44, vin);
		hg2.setItem(45, vin);
		hg2.setItem(53, vin);

		if (kits.equalsIgnoreCase("1")) {
			p.openInventory(kits1);
		} else if (kits.equalsIgnoreCase("2")) {
			p.openInventory(kits2);
		} else if (kits.equalsIgnoreCase("hg1")) {

			ItemStack hgkangaroo = new ItemStack(Material.FIREWORK);
			ItemMeta metahgkangaroo = hgkangaroo.getItemMeta();
			metahgkangaroo.setDisplayName(ChatColor.GREEN + "Kangaroo");
			ArrayList<String> deschgkangaroo = new ArrayList<>();
			deschgkangaroo.add(ChatColor.WHITE + "Com seu firework");
			deschgkangaroo.add(ChatColor.WHITE + "De pulos duplos!");
			deschgkangaroo.add(ChatColor.WHITE
					+ "Um otimo kit para fugir de inimigos");
			metahgkangaroo.setLore(deschgkangaroo);
			hgkangaroo.setItemMeta(metahgkangaroo);

			ItemStack hgarcher = new ItemStack(Material.BOW);
			ItemMeta metahgarcher = hgarcher.getItemMeta();
			metahgarcher.setDisplayName(ChatColor.GREEN + "Arqueiro");
			ArrayList<String> deschgarcher = new ArrayList<>();
			deschgarcher.add(ChatColor.WHITE
					+ "Mate seus inimigos com seu arco e flecha");
			metahgarcher.setLore(deschgarcher);
			hgarcher.setItemMeta(metahgarcher);

			ItemStack hgnormal = new ItemStack(Material.STONE_SWORD);
			ItemMeta metahgnormal = hgnormal.getItemMeta();
			metahgnormal.setDisplayName(ChatColor.GREEN + "Normal");
			ArrayList<String> deschgnormal = new ArrayList<>();
			deschgnormal.add(ChatColor.WHITE + "Kit sem habilidades,");
			deschgnormal.add(ChatColor.WHITE
					+ "apenas espada de pedra com sharpness.");
			metahgnormal.setLore(deschgnormal);
			hgnormal.setItemMeta(metahgnormal);

			ItemStack hgmonk = new ItemStack(Material.BLAZE_ROD);
			ItemMeta metahgmonk = hgmonk.getItemMeta();
			metahgmonk.setDisplayName(ChatColor.GREEN + "Monk");
			ArrayList<String> deschgmonk = new ArrayList<>();
			deschgmonk.add(ChatColor.WHITE + "Coloque items em um local");
			deschgmonk.add(ChatColor.WHITE
					+ "aleatorio no inventario do inimigo!");
			metahgmonk.setLore(deschgmonk);
			hgmonk.setItemMeta(metahgmonk);

			ItemStack hgfisherman = new ItemStack(Material.FISHING_ROD);
			ItemMeta metahgfisherman = hgfisherman.getItemMeta();
			metahgfisherman.setDisplayName(ChatColor.GREEN + "Fisherman");
			ArrayList<String> deschgfisherman = new ArrayList<>();
			deschgfisherman.add(ChatColor.WHITE
					+ "Use sua vara de pesca para puxar jogadores!");
			metahgfisherman.setLore(deschgfisherman);
			hgfisherman.setItemMeta(metahgfisherman);

			hg1.setItem(13, hgnormal);
			hg1.setItem(20, hgkangaroo);
			hg1.setItem(29, hgarcher);
			hg1.setItem(24, hgmonk);
			hg1.setItem(33, hgfisherman);
			p.openInventory(hg1);
		} else if (kits.equalsIgnoreCase("hg2")) {

			ItemStack hgstomper = new ItemStack(Material.IRON_BOOTS);
			ItemMeta metahgstomper = hgstomper.getItemMeta();
			metahgstomper.setDisplayName(corHG(p) + "Stomper");
			ArrayList<String> deschgstomper = new ArrayList<>();
			deschgstomper.add(ChatColor.WHITE
					+ "Receba 2 coracoes de dano de queda");
			deschgstomper.add(ChatColor.WHITE
					+ "e ao cair em cima de jogadores");
			deschgstomper.add(ChatColor.WHITE
					+ "Você acabara matando-os a menos que estejam no shift!");
			metahgstomper.setLore(deschgstomper);
			hgstomper.setItemMeta(metahgstomper);

			ItemStack hgendermage = new ItemStack(Material.PORTAL);
			ItemMeta metahgendermage = hgendermage.getItemMeta();
			metahgendermage.setDisplayName(corHG(p) + "Endermage");
			ArrayList<String> deschgendermage = new ArrayList<>();
			deschgendermage.add(ChatColor.WHITE
					+ "Puxe jogadores com seu portal!");
			metahgendermage.setLore(deschgendermage);
			hgendermage.setItemMeta(metahgendermage);

			ItemStack hgninja = new ItemStack(Material.COMPASS);
			ItemMeta metahgninja = hgninja.getItemMeta();
			metahgninja.setDisplayName(corHG(p) + "Ninja");
			ArrayList<String> deschgninja = new ArrayList<>();
			deschgninja.add(ChatColor.WHITE + "Bata em um jogador e se agache");
			deschgninja.add(ChatColor.WHITE + "para se teleportar ate ele");
			metahgninja.setLore(deschgninja);
			hgninja.setItemMeta(metahgninja);

			ItemStack hgurgal = new ItemStack(Material.POTION, 1, (short) 8201);
			ItemMeta metahgurgal = hgurgal.getItemMeta();
			metahgurgal.setDisplayName(corHG(p) + "Urgal");
			ArrayList<String> deschgurgal = new ArrayList<>();
			deschgurgal.add(ChatColor.WHITE + "Receba 3 pocoes de forca");
			metahgurgal.setLore(deschgurgal);
			hgurgal.setItemMeta(metahgurgal);

			hg2.setItem(20, hgstomper);
			hg2.setItem(24, kendermage);
			hg2.setItem(29, kninja);
			hg2.setItem(33, hgurgal);
			p.openInventory(hg2);
		}
	}
}
