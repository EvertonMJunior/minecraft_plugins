package me.everton.jpvp.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.everton.jpvp.Main;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.packetwrapper.WrapperPlayServerPlayerListHeaderFooter;
import com.comphenix.packetwrapper.WrapperPlayServerScoreboardTeam;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.collect.Multimap;

public class API {
	public static HashMap<String, String> setSkin = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	public static void setPrefix(Player p, String prefix) {
		PacketContainer pa = Main.getPm().createPacket(
				PacketType.Play.Server.SCOREBOARD_TEAM);
		WrapperPlayServerScoreboardTeam wr = new WrapperPlayServerScoreboardTeam(
				pa);
		String pr = prefix;
		if (pr.length() > 16) {
			pr = prefix.substring(0, 15);
		}
		wr.setDisplayName(pr);
		wr.setPrefix(pr);
		wr.setName(UUID.randomUUID().toString().substring(0, 15));
		ArrayList<String> al = new ArrayList<>();
		al.add(p.getName());
		wr.setPlayers(al);
		p.setDisplayName(prefix + p.getName() + "§r");

		for (Player on : Bukkit.getOnlinePlayers()) {
			try {
				Main.getPm().sendServerPacket(on, pa);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public Multimap<String, WrappedSignedProperty> getWrappedSignedProperty(String name){
		WrappedGameProfile profile = WrappedGameProfile
				.fromOfflinePlayer(Bukkit.getOfflinePlayer(name));
		Object handle = profile.getHandle();
		Object sessionService = MojangUtils.getSessionService();
		try {
			Method method = MojangUtils.getFillMethod(sessionService);
			method.invoke(sessionService, handle, true);
		} catch (IllegalAccessException ex) {
			return null;
		} catch (InvocationTargetException ex) {
			return null;
		}
		profile = WrappedGameProfile.fromHandle(handle);
		return profile.getProperties();
	}

	public void changeSkin(Player p, String nome) {
		if(!setSkin.containsKey(p.getName())) {
			setSkin.put(p.getName(), nome);
			p.kickPlayer(Main.getPlugin().getConfig().getString("NomeServidor") + " \n§eRelogue para que a skin seja setada.");
		}
	}

	/**
	 * 
	 * @return Sopa
	 */
	public ItemStack getSoup() {
		return item(Material.MUSHROOM_SOUP, 1, "§eSopa",
				new String[] { "§5Recupera 3.5§d♥" });
	}

	/**
	 * 
	 * @param p
	 *            Jogador à receber o recraft
	 * 
	 */
	public void giveMushrooms(Player p) {
		p.getInventory().setItem(13, item(Material.BOWL, 64, "§eTigela"));
		p.getInventory().setItem(14,
				item(Material.RED_MUSHROOM, 64, "§cCogumelo"));
		p.getInventory().setItem(15,
				item(Material.BROWN_MUSHROOM, 64, "§7Cogumelo"));
	}

	/**
	 * Retorna uma boolean dizendo se o inventário do player está vazio ou não.
	 * p = Jogador;
	 */
	public boolean isInvFull(Player p) {
		int comItem = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) {
				comItem++;
			}
		}
		if (comItem == p.getInventory().getSize()) {
			return true;
		}
		return false;
	}

	/**
	 * Retorna a quantidade de Slots vazios que o jogador possui no inventário.
	 * p = Jogador;
	 */
	public int getHowMuchClearSlots(Player p) {
		int slots = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) == null) {
				slots++;
			}
		}
		return slots;
	}

	/**
	 * Retorna a quantidade de itens que o jogador possui no inventário de
	 * determinado Material. p = Jogador (Ex: (Player) sender); m = Material
	 * (Ex: MUSHROOM_SOUP);
	 */
	public int getAmount(Player p, Material m) {
		int itens = 0;
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			try {
				if (p.getInventory().getItem(i).getType() == m) {
					itens += p.getInventory().getItem(i).getAmount();
				}
			} catch (Exception e) {

			}
		}
		return itens;
	}

	/**
	 * Enche o inventário com vidros, de forma personalizada. inv = Inventário
	 * (Ex: p.getOpenInventory());
	 */
	public void fillInvOfGlasses(Inventory inv) {
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, item(Material.STAINED_GLASS_PANE, 1, " ", 7));
		}

		if (inv.getSize() == 27) {
			inv.setItem(0, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(8, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(18, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(22, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(26, item(Material.STAINED_GLASS_PANE, 1, " "));
		} else if (inv.getSize() == 54) {
			inv.setItem(1, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(7, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(9, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(17, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(36, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(44, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(46, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(49, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(52, item(Material.STAINED_GLASS_PANE, 1, " "));
		} else if (inv.getSize() >= 27) {
			inv.setItem(0, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(4, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(8, item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 1,
					item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 5,
					item(Material.STAINED_GLASS_PANE, 1, " "));
			inv.setItem(inv.getSize() - 9,
					item(Material.STAINED_GLASS_PANE, 1, " "));
		}
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD);
	 */
	public ItemStack item(Material m) {
		return new ItemStack(m);
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD); q = Quantidade de itens
	 * (Ex: 3);
	 */
	public ItemStack item(Material m, int q) {
		return new ItemStack(m, q);
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD); q = Quantidade de itens
	 * (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada);
	 */
	public ItemStack item(Material m, int q, String n) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD); q = Quantidade de itens
	 * (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada); t = Tipo do item (Ex:
	 * 2);
	 */
	public ItemStack item(Material m, int q, String n, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna um item encantado. m = Material do item (Ex: WOOD); q =
	 * Quantidade de itens (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada);
	 * en = Encantamento desejado (Ex: DAMAGE_ALL); level = Nível do
	 * encantamento (Ex: 1);
	 */
	public ItemStack item(Material m, int q, String n, Enchantment en, int level) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		it.addEnchantment(en, level);
		return it;
	}

	/**
	 * Retorna um item encantado. m = Material do item (Ex: WOOD); q =
	 * Quantidade de itens (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada);
	 * en = Encantamento desejado (Ex: DAMAGE_ALL); level = Nível do
	 * encantamento (Ex: 1); t = Tipo do item (Ex: 3);
	 */
	public ItemStack item(Material m, int q, String n, Enchantment en,
			int level, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setDisplayName(n);
		it.setItemMeta(me);
		it.addEnchantment(en, level);
		return it;
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD); q = Quantidade de itens
	 * (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada); l = Lore (Ex: new
	 * String[] {"...", "..."});
	 */
	public ItemStack item(Material m, int q, String n, String[] l) {
		ItemStack it = new ItemStack(m, q);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna um item. m = Material do item (Ex: WOOD); q = Quantidade de itens
	 * (Ex: 3); n = Nome do item (Ex: §cMadeira Refinada); l = Lore (Ex: new
	 * String[] {"...", "..."}); t = Tipo do item (Ex: 2);
	 */
	public ItemStack item(Material m, int q, String n, String[] l, int t) {
		ItemStack it = new ItemStack(m, q, (byte) t);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna uma poção. pot = Tipo da poção (Ex: JUMP); q = Quantidade de
	 * poções (Ex: 3); n = Nome do item da poção (Ex: Poção Divina); l = Lore
	 * (Ex: new String[] {"...", "..."});
	 */
	public ItemStack potion(PotionType pot, int q, String n, String[] l,
			boolean splash, boolean extended, int level) {
		Potion pot1 = new Potion(pot, level);
		if (!pot.name().contains("INSTANT")) {
			pot1.setHasExtendedDuration(extended);
		}
		pot1.setSplash(splash);
		ItemStack it = pot1.toItemStack(5);
		ItemMeta me = it.getItemMeta();
		me.setLore(Arrays.asList(l));
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna uma armadura de couro colorida. m = Armadura (Ex:
	 * LEATHER_CHESTPLATE); n = Nome do item (Ex: §6Armadura de jesus ;-;); cor
	 * = Cor da armadura (Ex: AQUA);
	 */
	public ItemStack leatherArmor(Material m, String n, Color cor) {
		if (!m.name().contains("LEATHER_")) {
			return null;
		}
		ItemStack it = new ItemStack(m);
		LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
		me.setColor(cor);
		me.setDisplayName(n);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna uma armadura de couro colorida. m = Armadura (Ex:
	 * LEATHER_CHESTPLATE); q = Quantidade de armaduras (Ex: 5); n = Nome do
	 * item (Ex: §6Armadura de jesus ;-;); l = Lore (Ex: new String[] {"...",
	 * "..."}); cor = Cor da armadura (Ex: AQUA);
	 */
	public ItemStack leatherArmor(Material m, int q, String n, String[] l,
			Color cor) {
		if (!m.name().contains("LEATHER_")) {
			return null;
		}
		ItemStack it = new ItemStack(m, q);
		LeatherArmorMeta me = (LeatherArmorMeta) it.getItemMeta();
		me.setColor(cor);
		me.setDisplayName(n);
		me.setLore(Arrays.asList(l));
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna a cabeça de um jogador. name = Nome do jogador (Ex: EvertonPvP);
	 * q = Quantidade de cabeças (Ex: 5); n = Nome do item (Ex: §6Cabeça de
	 * EvertonPvP);
	 */
	public ItemStack getHead(String name, int q, String n) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta me = (SkullMeta) it.getItemMeta();
		me.setDisplayName(n);
		me.setOwner(name);
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna a cabeça de um jogador. name = Nome do jogador (Ex: EvertonPvP);
	 * q = Quantidade de cabeças (Ex: 5); n = Nome do item (Ex: §6Cabeça de
	 * EvertonPvP); l = Lore (Ex: new String[] {"...", "..."});
	 */
	public ItemStack getHead(String name, int q, String n, String[] l) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, q,
				(short) SkullType.PLAYER.ordinal());
		SkullMeta me = (SkullMeta) it.getItemMeta();
		me.setDisplayName(n);
		me.setOwner(name);
		me.setLore(Arrays.asList(l));
		it.setItemMeta(me);
		return it;
	}

	/**
	 * Retorna o ping do jogador.
	 */
	public int getPing(Player p) {
		PlayerConnection pc = ((CraftPlayer) p).getHandle().playerConnection;
		return pc.player.ping;
	}

	/**
	 * Dá os itens iniciais.
	 */
	public void itensIniciais(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(4,
				item(Material.ENDER_CHEST, 1, "§e§lKits §7(Clique Direito)"));
	}

	/**
	 * Inicia um cooldown.
	 */
	public void startCd(final Player p, final int coolDown, int startDelay,
			final HashMap<String, Integer> cd, final String msgFinalCd) {
		new BukkitRunnable() {
			int time = coolDown;

			@Override
			public void run() {
				if (!p.isOnline()) {
					cd.remove(p.getName());
					cancel();
					return;
				}
				if (time > 0) {
					cd.put(p.getName(), time);
					time--;
				} else {
					if (msgFinalCd != null) {
						p.sendMessage(msgFinalCd);
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.5F,
								15.5F);
					}
					cd.remove(p.getName());
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), startDelay * 1L, 20L);
	}

	/**
	 * Transforma segundos em texto. Máximo de 60 minutos para ser correto!
	 */
	public String secToString(int i) {
		int m = (int) TimeUnit.SECONDS.toMinutes(i);
		int s = i - (m * 60);
		if ((m == 0) && (s != 0)) {
			if (s > 1) {
				return doisDigitos(s) + " segundos";
			} else {
				return doisDigitos(s) + " segundo";
			}
		} else if ((m != 0) && (s == 0)) {
			if (m > 1) {
				return doisDigitos(m) + " minutos";
			} else {
				return doisDigitos(m) + " minuto";
			}
		} else {
			if ((s > 1) && (m > 1)) {
				return doisDigitos(m) + " minutos e " + s + " segundos";
			} else if ((s > 1) && (!(m > 1))) {
				return doisDigitos(m) + " minuto e " + s + " segundos";
			} else if ((m > 1) && (!(s > 1))) {
				return doisDigitos(m) + " minutos e " + s + " segundo";
			}
		}
		return doisDigitos(s) + " minutos e " + s + " segundos";
	}

	public String doisDigitos(int number) {

		if (number == 0) {
			return "00";
		}

		if (number / 10 == 0) {
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public void deleteHashMapKey(HashMap<?, ?> h, String v) {
		if (h.containsKey(v)) {
			h.remove(v);
		}
	}

	public void deleteArrayList(ArrayList<?> a, String v) {
		if (a.contains(v)) {
			a.remove(v);
		}
	}

	public String format(String s) {
		String sr = s.substring(0, 1).toUpperCase()
				+ s.substring(1, s.length()).toLowerCase();
		return sr;
	}

	public void sendTitle(Player p, String t, String st, int fi, int s, int fo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent title = ChatSerializer.a("{'text': '" + t + "'}");
		IChatBaseComponent subtitle = ChatSerializer
				.a("{'text': '" + st + "'}");

		PacketPlayOutTitle packetTimes = new PacketPlayOutTitle(fi * 20,
				s * 20, fo * 20);
		connection.sendPacket(packetTimes);

		if (t != null) {
			PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(
					EnumTitleAction.TITLE, title);
			connection.sendPacket(packetTitle);
		}

		if (st != null) {
			PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(
					EnumTitleAction.SUBTITLE, subtitle);
			connection.sendPacket(packetSubtitle);
		}
	}

	public void sendActionBar(Player p, String msg) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		IChatBaseComponent cbc = ChatSerializer
				.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat pa = new PacketPlayOutChat(cbc, (byte) 2);
		connection.sendPacket(pa);
	}

	public void setHeaderFooter(Player p, String headerT, String footerT) {
		PacketContainer pa = Main.getPm().createPacket(
				PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
		WrapperPlayServerPlayerListHeaderFooter wr = new WrapperPlayServerPlayerListHeaderFooter(
				pa);
		WrappedChatComponent header = WrappedChatComponent.fromText(headerT);
		WrappedChatComponent footer = WrappedChatComponent.fromText(footerT);
		wr.setHeader(header);
		wr.setFooter(footer);
		try {
			Main.getPm().sendServerPacket(p, pa);
		} catch (InvocationTargetException e) {
		}
	}

	@SuppressWarnings("deprecation")
	public void broadcastMessage(String msg, String permission) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.hasPermission(permission)) {
				p.sendMessage(msg);
			}
		}
		Bukkit.getConsoleSender().sendMessage(msg);
	}

	@SuppressWarnings("deprecation")
	public void broadcastMessage(String msg) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(msg);
		}
		Bukkit.getConsoleSender().sendMessage(msg);
	}

	public String getSpaces(String forGetSpaces, int maxSpaces) {
		int esp = maxSpaces - forGetSpaces.length();
		String espacos = "";
		for (int i = 0; i < esp; i++) {
			espacos += " ";
		}
		return espacos;
	}
}
