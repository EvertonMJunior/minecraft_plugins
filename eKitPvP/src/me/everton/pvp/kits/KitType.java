package me.everton.pvp.kits;

import me.everton.pvp.API;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum KitType {
	PVP("PvP", "Ganhe uma espada com Sharpness", null, Material.STONE_SWORD, true),
	ARCHER("Arqueiro", "Mate todos com seu arco!", Material.BOW, Material.BOW, false),
	KANGAROO("Kangaroo", "Dê double-jumps e se locomova rapidamente", Material.FIREWORK, Material.FIREWORK, false),
	GRAPPLER("Grappler", "Se locomava rapidamente com seu laço", Material.LEASH, Material.LEASH, false),
	STOMPER("Stomper", "Caia de lugares altos e cause estragos", null, Material.IRON_BOOTS, false),
	ENDERMAGE("Endermage", "Puxe todos com seu portal", Material.PORTAL, Material.PORTAL, false),
	FISHERMAN("Fisherman", "Fisgue os outros e teleporte-os até você", Material.FISHING_ROD, Material.FISHING_ROD, false),
	VIKING("Viking", "Dê um dano superior ao de uma espada com seu machado", null, Material.STONE_AXE, false),
	C4("C4", "Exploda tudo com sua bomba", Material.SLIME_BALL, Material.SLIME_BALL, false),
	DESHFIRE("Deshfire", "Dê Boosts e bote fogo em quem passares", Material.REDSTONE_BLOCK, Material.REDSTONE_BLOCK, false),
	QUICKDROPPER("Quickdropper", "Drope tigelas automaticamente", null, Material.BOWL, false),
	TITAN("Titan", "Fique invencível, mas sem poder dar dano em ninguém", Material.BEDROCK, Material.BEDROCK, false),
	FORCEFIELD("Forcefield", "Simule estar usando o hack Forcefield", Material.IRON_FENCE, Material.IRON_FENCE, false),
	AVATAR("Avatar", "Tenha os 4 poderes do Avatar", Material.GRASS, Material.BEACON, false),
	FLASH("Flash", "Se teleporte com sua tocha", Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_ON, false),
	NINJA("Ninja", "Teleporte-se para o último jogador hitado", null, Material.NETHER_STAR, false),
	POSEIDON("Poseidon", "Ganhe força e velocidade na água", null, Material.WATER_BUCKET, false),
	SNIPER("Sniper", "Atire nos outros e cause-os dano! Não há gravidade na bala", Material.BLAZE_ROD, Material.BLAZE_ROD, false),
	SOMBRA("Sombra", "Fique completamente invisível por 5 segundos", Material.COAL_BLOCK, Material.COAL_BLOCK, false),
	TERRORISTA("Terrorista", "Pule para o céu e exploda todos onde caíres", Material.MAGMA_CREAM, Material.MAGMA_CREAM, false),
	VACUUM("Vacuum", "Puxe todas as entidades num raio de 15 blocos", Material.EYE_OF_ENDER, Material.EYE_OF_ENDER, false),
	SPECTRE("Spectre", "Fique invisível por 20 segundos", Material.SUGAR, Material.SUGAR, false),
	BACKPACKER("Backpacker", "Tenha uma mochila de sopas", Material.CHEST, Material.CHEST, false),
	SNAIL("Snail", "Dê Slowness 2 em seu inimigo por 5 segundos", null, Material.FERMENTED_SPIDER_EYE, false),
	HULK("Hulk", "Pegue jogadores em sua cabeça e jogue-os para o alto hitando-os", Material.SADDLE, Material.SADDLE, false),
	VIPER("Viper", "Dê Veneno 2 em seu inimigo por 5 segundos", null, Material.SPIDER_EYE, false),
	THOR("Thor", "Lançe raios com seu machado", Material.WOOD_AXE, Material.WOOD_AXE, false),
	WEAKHAND("Weakhand", "Dê Fraqueza 2 em seu inimigo por 5 segundos", null, Material.WOOD_SWORD, false),
	ALADDIN("Aladdin", "Voe com seu tapete, como o Aladdin", Material.CARPET, Material.CARPET, false),
	FENIX("Fênix", "Voe e bote fogo em quem estiver por perto", Material.FEATHER, Material.FEATHER, false),
	MONK("Monk", "Deixe o inventário de seu inimigo bagunçado", Material.BLAZE_ROD, Material.BLAZE_ROD, false),
	TIMELORD("Timelord", "Congele todos num raio de 5 blocos por 5 segundos", Material.WATCH, Material.WATCH, false),
	GRANDPA("Grandpa", "Ganhe um Stick com Knockback 2", Material.STICK, Material.STICK, false),
	ANCHOR("Anchor", "Não leve nem dê knockback", null, Material.ANVIL, false),
	FIREMAN("Fireman", "Não leve dano de fogo", null, Material.LAVA_BUCKET, false),
	PYRO("Pyro", "Jogue bolas de fogo e bote fogo em seu inimigo", Material.FIREBALL, Material.FIREBALL, false),
	BOXER("Boxer", "Dê mais dano e receba menos dano", null, Material.APPLE, false),
	SUMO("Sumo", "Jogue todos para o alto", Material.BEDROCK, Material.BEDROCK, false),
	GLADIATOR("Gladiator", "Lute contra seu inimigo numa arena", Material.IRON_FENCE, Material.IRON_FENCE, false),
	WITHER("Wither", "Atire cabeças de Wither", Material.DISPENSER, Material.DISPENSER, false),
	ICICLES("Icicles", "Congele a hotbar de quem você está hitando", null, Material.PACKED_ICE, false),
	JELLYFISH("JellyFish", "Envenene quem passar por suas águas", null, Material.STATIONARY_WATER, false),
	MADMAN("Madman", "Deixe todo jogador perto de você fraco", null, Material.DIAMOND_SWORD, false),
	SWITCHER("Switcher", "Troque de lugar com quem acertares suas bolas de neve", Material.SNOW_BALL, Material.SNOW_BALL, false),
	SURPRISE("Surprise", "Escolha um kit aleatório entre todos os do servidor", null, Material.CAKE, false),
	NONE("Sem Kit", null, null, null, false);
	
	String kitName;
	String kitDesc;
	Material kitItem;
	Material kitSelectorItem;
	boolean sharpSword;
	
	private KitType(String n, String d, Material ki, Material ksi, boolean sS) {
		kitName = n;
		kitDesc = d;
		kitItem = ki;
		kitSelectorItem = ksi;
		sharpSword = sS;
	}
	
	public String getName() {
		return kitName;
	}
	
	public String getDesc() {
		return kitDesc;
	}
	
	public ItemStack getKitItem() {
		if(kitItem != null) {
			return API.item(kitItem, 1, "§b§l" + getName(), new String[] {"§7" + getDesc()});
		} else {
			return null;
		}
	}
	
	public ItemStack getKitSelectorItem() {
		if(kitSelectorItem != null) {
			return API.item(kitSelectorItem, 1, "§6" + getName(), new String[] {"§7" + getDesc()});
		} else {
			return null;
		}
	}
	
	public boolean hasSharpness() {
		return sharpSword;
	}
}
