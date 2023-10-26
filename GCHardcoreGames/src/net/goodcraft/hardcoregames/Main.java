package net.goodcraft.hardcoregames;

import net.goodcraft.GameMode;
import net.goodcraft.api.Hologram;
import net.goodcraft.api.Item;
import net.goodcraft.api.Schematic;
import net.goodcraft.eventos.HologramKillEvent;
import net.goodcraft.hardcoregames.chests.Chests;
import net.goodcraft.hardcoregames.eventos.*;
import net.goodcraft.hardcoregames.game.GameTimer;
import net.goodcraft.hardcoregames.utils.SpawnVillager;
import net.goodcraft.minigames.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class Main extends JavaPlugin {

    private static Plugin plugin;
    private static Minigame minigame;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Minigame getMg() {
        return minigame;
    }

    @Override
    public void onLoad() {
        Bukkit.getServer().unloadWorld(usingWorld, false);
        deleteDir(new File("world"));
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {
        plugin = this;
        minigame = new Minigame("Hardcore Games", GameMode.HARDCOREGAMES, this);

        getMg().getUsingWorld().getEntities().stream().filter(en -> en instanceof Villager).forEach(Entity::remove);
        for(org.bukkit.entity.Item i : getMg().getUsingWorld().getEntitiesByClass(org.bukkit.entity.Item.class)){
            HologramKillEvent.allowedItems.add(i);
            i.remove();
        }
        getMg().getUsingWorld().getEntities().stream().filter(en -> en instanceof ArmorStand).forEach(Entity::remove);

        getMg().registerKits(this, "kit.habilidades");
        regSoup();

        createBord();
        loadChunks();

        getMg().getUsingWorld().setSpawnLocation(0, 201, 0);

        Location loca = new Location(getMg().getUsingWorld(),
                getMg().getUsingWorld().getSpawnLocation().getX(), 198,
                getMg().getUsingWorld().getSpawnLocation().getZ());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, getMg().getScoreboard(), 20L, 20L);
        new BukkitRunnable() {

            @Override
            public void run() {
                Hologram.hologram("§6§lLava Challenge", new Location(usingWorld, -12.5, 200, -9.5));
                Hologram.hologram("§ePule na Lava e treine seu Refil e Recraft!", new Location(usingWorld, -12.5, 199.5, -9.5));
                Hologram.floatingItem(Item.item(Material.LAVA_BUCKET, 1), new Location(usingWorld, -12.5, 199.5, -9.5));
                SpawnVillager.spawnVillager("§bLoja de Kits", new Location(usingWorld, -17.5, 200, 17.5));
                estado = GameState.PREGAME;
                GameTimer.preGame();
                Schematic.paste("Coliseu", loca);
                freeKits.retrieveAllData();
            }
        }.runTaskLater(Main.getMain(), 30);
    }

    @Override
    public void onDisable() {
        pl = null;
        sb = null;
        usingWorld = null;
        freeKits = null;
        super.onDisable();
    }

    @SuppressWarnings("deprecation")
    public void regSoup() {
        ItemStack is = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta im = is.getItemMeta();
        is.setItemMeta(im);

        ShapelessRecipe recipe = new ShapelessRecipe(is);
        recipe.addIngredient(1, Material.CACTUS);
        recipe.addIngredient(1, Material.BOWL);
        getServer().addRecipe(recipe);

        ShapelessRecipe sad = new ShapelessRecipe(is);
        sad.addIngredient(1, Material.INK_SACK, (short) 3);
        sad.addIngredient(1, Material.BOWL);
        getServer().addRecipe(sad);

        ShapelessRecipe das = new ShapelessRecipe(is);
        das.addIngredient(1, Material.YELLOW_FLOWER);
        das.addIngredient(1, Material.RED_ROSE);
        das.addIngredient(1, Material.BOWL);
        getServer().addRecipe(das);
    }

    private void registerListeners() {
        rL(new GamerDamage());
        rL(new GamerDeath());
        rL(new GamerDropItem());
        rL(new GamerJoin());
        rL(new GamerRelog());
        rL(new GeralGameL());
        rL(new HotBar());
        rL(new LavaChallenge());
        rL(new KitSelector());
        rL(new Chests());
    }

    private void rL(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    public static void createBord() {
        int xx = 500;
        for (int z = -500; z <= 500; z++) {
            for (int y = 1; y < 256; y++) {
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
        for (int x = -500; x <= 500; x++) {
            for (int y = 1; y < 256; y++) {
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
        for (int z = -500; z <= 500; z++) {
            for (int y = 1; y < 256; y++) {
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
        for (int x = -500; x <= 500; x++) {
            for (int y = 1; y < 256; y++) {
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
                for (int y = 1; y <= 150; y++) {
                    Location loc = new Location(Main.usingWorld, x, y, z);
                    if (!loc.getChunk().isLoaded()) {
                        loc.getChunk().load();
                    }
                }
            }
        }
    }
}
