package com.goodcraft;

import com.goodcraft.api.Item;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public enum GameMode {
    LOBBY("Lobby", Item.item(Material.COMPASS), null, null, true),
    KITPVP("KitPvP", Item.item(Material.STONE_SWORD), new String[]{
        "§7Treine seu PvP com sopas. Cada sopa tomada",
        "§7irá regenerar 3 corações e meio!"
    }, new String[]{
        "Kills",
        "Deaths"
    }, true),
    SKYWARS("Sky Wars", Item.item(Material.GRASS), new String[]{
        "§7Enfrente seus adversários em ilhas flutuantes.",
        "§eQue vença o melhor§7!"
    }, new String[]{
        "Wins",
        "Kills",
        "Deaths"
    }, true),
    PARKOUR("Parkour", Item.item(Material.LADDER), new String[]{
        "§7Pule de bloco em bloco.",
        "§7A §cdificuldade§7 só aumenta,",
        "§7cuidado para não §ccair§7!"
    }, new String[]{
        "Wins",
        "Quedas"
    }, false),
    MEGASW("Mega SkyWars", Item.item(Material.MYCEL), new String[]{
        "§7Em partidas de muitos §ejogadores§7, §cbatalhe",
        "§7em equipe contra os outros times."
    }, new String[]{
        "Wins",
        "Perdas",
        "Kills",
        "Deaths"
    }, false),
    SPLEEF("Spleef", Item.item(Material.IRON_SPADE), new String[]{
        "§7Com uma pá, quebre blocos de neve.",
        "§7Seu objetivo é §cderrubar §7seu adversário!"
    }, new String[]{
        "Wins",
        "Kills",
        "Deaths"
    }, true),
    APOCALIPSE_ZUMBI("Apocalipse Zumbi", Item.getHead("MHF_Zombie", 1, ""), new String[]{
        "§7Sobreviva a um §capocalipse zumbi§7.",
        "§7A cada fase que passar,",
        "§7mais zumbis terá que §cenfrentar."
    }, new String[]{
        "Wins",
        "Fases",
        "Kills",
        "Deaths"
    }, false),
    TIRO_AO_ALVO("Tiro ao Alvo", Item.item(Material.BOW), new String[]{
        "§7Acerte flechas nos alvos. Cada §cc§eo§ar§7 vale ",
        "§7certos pontos. Quem tiver mais pontos, ganha."
    }, new String[]{
        "Wins",
        "Pontos",
    }, true),
    CRAZY_ISLAND("Crazy Island", Item.getHead("MrBowZz", 1, ""), new String[]{
        "§7Sobreviva em uma ilha no meio do Oceano.",
        "§7Seu objetivo é matar o §etime adversário§7.",
        "§7Após certo tempo de batalha, há uma disputa final."
    }, new String[]{
        "Wins",
        "Kills",
        "Deaths"
    }, false),
    HARDCOREGAMES("Hardcore Games", Item.item(Material.MUSHROOM_SOUP), new String[]{
        "§7Um modo de §cPvP§7 com sopas, assim como o §aKitPvP.",
        "§7Faça suas §9sopas§7 e sobreviva ao §9máximo§7!",
        "§eQue vença o melhor!"
    }, new String[]{
        "Wins",
        "Kills",
        "Deaths"
    }, true),
    BUILDBATTLE("Build Battle", Item.getHead("Miner", 1, ""), new String[]{
        "§7Jogue uma §9Batalha de Construção",
        "§7contra outros jogadores.",
        "§eVamos ver se você é bom mesmo!"
    }, new String[]{
        "Wins",
        "Perdas"
    }, false),
    OITC("One in The Chamber", Item.item(Material.ARROW), new String[]{
        "§7Em uma arena, mate seus adversários,",
        "§7com uma espada ou com um arco.",
        "§eQuem matar mais, vence o MiniGame!"
    }, new String[]{
        "Wins",
        "Perdas",
        "Kills",
        "Deaths"
    }, false),
    CREEPER_RUN("Creeper Run", Item.getHead("MHF_Creeper", 1, ""), new String[]{
        "§7Seu objetivo é §ccorrer§7 dos jogadores",
        "§7que são §aCreepers§7. Caso seja explodido",
        "§7por algum, você também poderá §cexplodir",
        "§7jogadores a cada §e5 segundos§7, mas",
        "§7ficará §cvulnerável§7 nesse meio-tempo"
    }, new String[]{
        "Wins",
        "Kills",
        "Deaths"
    }, false);
    
    public static File file;
    public static FileConfiguration cfg;

    private final String name;
    private final ItemStack item;
    private final String[] description;
    private final String[] status;
    private final boolean active;

    private GameMode(String name, ItemStack item, String[] description, String[] status, boolean active) {
        this.name = name;
        this.item = item;
        this.description = description;
        this.active = active;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public String[] getDescription() {
        return description;
    }
    
    public String[] getStatus(){
        return status;
    }
    
    public boolean isActive(){
        return active;
    }

    public static ArrayList<ItemStack> getItems() {
        ArrayList<ItemStack> tipos = new ArrayList<>();
        for (GameMode tp : values()) {
            tipos.add(tp.getItem());
        }

        return tipos;
    }

    public void setLocation(Location l) {
        cfg.set(name() + ".x", l.getX());
        cfg.set(name() + ".y", l.getY());
        cfg.set(name() + ".z", l.getZ());
        cfg.set(name() + ".yaw", l.getYaw());
        cfg.set(name() + ".pitch", l.getPitch());
        cfg.set(name() + ".world", l.getWorld().getName());
        try {
            cfg.save(GameMode.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Location getLocation() {
        Double x = cfg.getDouble(name() + ".x");
        Double y = cfg.getDouble(name() + ".y");
        Double z = cfg.getDouble(name() + ".z");
        Float yaw = (float) cfg.getDouble(name() + ".yaw");
        Float pitch = (float) cfg.getDouble(name() + ".pitch");
        World w;
        try {
            w = Bukkit.getWorld(cfg.getString(name() + ".world"));
        } catch (Exception e) {
            return null;
        }

        Location l = new Location(w, x, y, z, yaw, pitch);

        return l;
    }
}
