package me.dark.Cmd;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;

public class MobSpawn implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player p = (Player) sender;
		if (!(sender instanceof Player)) {
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("mobspawn")) {
			Location playerlocation = p.getLocation();
			if (p.hasPermission(Main.perm_mod)) {
				if (args.length == 1) {
					EntityType toSpawn = getMob(args[0]);
					if (toSpawn != null) {
						if (toSpawn.equals(EntityType.SKELETON)) {
							Skeleton skeleton = (Skeleton) p.getWorld()
									.spawnEntity(playerlocation, toSpawn);
							skeleton.setSkeletonType(Skeleton.SkeletonType.NORMAL);
							skeleton.getEquipment().setItemInHand(
									new ItemStack(Material.BOW));
							return true;
						}
						p.getWorld().spawnEntity(playerlocation, toSpawn);
						return true;
					}
					p.sendMessage(Main.prefix+"Mob invalido!");
					return true;
				}
				if (args.length == 2) {
					EntityType toSpawn = getMob(args[0]);
					if (toSpawn != null) {
						World world = p.getWorld();
						int spawnAmt = Integer.parseInt(args[1]);
						for (int i = 0; i < spawnAmt; i++) {
							if (toSpawn.equals(EntityType.SKELETON)) {
								Skeleton skeleton = (Skeleton) p.getWorld()
										.spawnEntity(playerlocation, toSpawn);
								skeleton.setSkeletonType(Skeleton.SkeletonType.NORMAL);
								skeleton.getEquipment().setItemInHand(
										new ItemStack(Material.BOW));
								return true;
							}
							world.spawnEntity(playerlocation, toSpawn);
						}

						return true;
					}

					p.sendMessage(Main.prefix+"Mob invalido!");
					return true;
				}
				p.sendMessage(Main.prefix+"Use: /mobspawn [mob] (quantidade)");
			}

		}
		return false;
	}


	private final EntityType getMob(String mob) {
		if ((mob.equalsIgnoreCase("bat")) || (mob.equalsIgnoreCase("bird"))) {
			return EntityType.BAT;
		}
		if (mob.equalsIgnoreCase("blaze")) {
			return EntityType.BLAZE;
		}
		if (mob.equalsIgnoreCase("cspider")
				|| mob.equalsIgnoreCase("cavespider")
				|| mob.equalsIgnoreCase("cave")) {
			return EntityType.CAVE_SPIDER;
		}
		if (mob.equalsIgnoreCase("chicken") || mob.equalsIgnoreCase("galinha")) {
			return EntityType.CHICKEN;
		}
		if (mob.equalsIgnoreCase("cow") || mob.equalsIgnoreCase("vaca")) {
			return EntityType.COW;
		}
		if (mob.equalsIgnoreCase("creeper")) {
			return EntityType.CREEPER;
		}
		if (mob.equalsIgnoreCase("enderdragon")
				|| mob.equalsIgnoreCase("dragon")) {
			return EntityType.ENDER_DRAGON;
		}
		if (mob.equalsIgnoreCase("enderman")
				|| mob.equalsIgnoreCase("eman")) {
			return EntityType.ENDERMAN;
		}
		if (mob.equalsIgnoreCase("ghast")) {
			return EntityType.GHAST;
		}
		if (mob.equalsIgnoreCase("giant")) {
			return EntityType.GIANT;
		}
		if ((mob.equalsIgnoreCase("iron")) || (mob.equalsIgnoreCase("igolem"))
				|| (mob.equalsIgnoreCase("irongolem"))) {
			return EntityType.IRON_GOLEM;
		}
		if ((mob.equalsIgnoreCase("magma")) || (mob.equalsIgnoreCase("mcube"))
				|| (mob.equalsIgnoreCase("cube"))
				|| (mob.equalsIgnoreCase("magmacube"))) {
			return EntityType.MAGMA_CUBE;
		}
		if (mob.equalsIgnoreCase("mcow")
				|| mob.equalsIgnoreCase("mooshroom")
				|| mob.equalsIgnoreCase("mushroomcow")) {
			return EntityType.MUSHROOM_COW;
		}
		if ((mob.equalsIgnoreCase("ocelot"))
				|| (mob.equalsIgnoreCase("cat") || mob.equalsIgnoreCase("gato"))) {
			return EntityType.OCELOT;
		}
		if (mob.equalsIgnoreCase("pig")) {
			return EntityType.PIG;
		}
		if ((mob.equalsIgnoreCase("pzombie"))
				|| (mob.equalsIgnoreCase("pigzombie"))) {
			return EntityType.PIG_ZOMBIE;
		}
		if (mob.equalsIgnoreCase("sheep") || mob.equalsIgnoreCase("ovelha")) {
			return EntityType.SHEEP;
		}
		if ((mob.equalsIgnoreCase("silverfish"))
				|| (mob.equalsIgnoreCase("sfish"))) {
			return EntityType.SILVERFISH;
		}
		if ((mob.equalsIgnoreCase("skeleton"))
				|| (mob.equalsIgnoreCase("skele") || mob
						.equalsIgnoreCase("esqueleto"))) {
			return EntityType.SKELETON;
		}
		if (mob.equalsIgnoreCase("slime")) {
			return EntityType.SLIME;
		}
		if ((mob.equalsIgnoreCase("snowman")) || (mob.equalsIgnoreCase("sman"))) {
			return EntityType.SNOWMAN;
		}
		if (mob.equalsIgnoreCase("spider") || mob.equalsIgnoreCase("aranha")) {
			return EntityType.SPIDER;
		}
		if (mob.equalsIgnoreCase("squid")) {
			return EntityType.SQUID;
		}
		if (mob.equalsIgnoreCase("villager")) {
			return EntityType.VILLAGER;
		}
		if (mob.equalsIgnoreCase("witch") || mob.equalsIgnoreCase("bruxa")) {
			return EntityType.WITCH;
		}
		if (mob.equalsIgnoreCase("wither")) {
			return EntityType.WITHER;
		}
		if (mob.equalsIgnoreCase("wolf") || mob.equalsIgnoreCase("dog")
				|| mob.equalsIgnoreCase("lobo")) {
			return EntityType.WOLF;
		}
		if (mob.equalsIgnoreCase("zombie") || mob.equalsIgnoreCase("zumbi")) {
			return EntityType.ZOMBIE;
		}
		return null;
	}

}
