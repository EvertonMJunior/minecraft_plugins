package net.goodcraft;

import net.goodcraft.api.Item;
import net.goodcraft.minigames.Stat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public enum GameMode {
    LOBBY("Lobby", Item.item(Material.COMPASS), new String[]{}, new Stat[]{}, true, "LOBBY"),
    KITPVP("KitPvP", Item.item(Material.STONE_SWORD), new String[]{
            "§7Treine seu PvP com sopas. Cada sopa tomada",
            "§7irá regenerar 3 corações e meio!"
    }, new Stat[]{
            new Stat("Kills", "KITPVP", Stat.Type.MAIOR),
            new Stat("Deaths", "KITPVP", Stat.Type.MENOR),
    }, true, "KITPVP"),
    SKYWARS("Sky Wars", Item.item(Material.GRASS), new String[]{
            "§7Enfrente seus adversários em ilhas flutuantes.",
            "§eQue vença o melhor§7!"
    }, new Stat[]{
            new Stat("Wins", "SKYWARS", Stat.Type.MAIOR),
            new Stat("Kills", "SKYWARS", Stat.Type.MAIOR),
            new Stat("Deaths", "SKYWARS", Stat.Type.MENOR)
    }, true, "SKYWARS"),
    PARKOUR("Parkour", Item.item(Material.LADDER), new String[]{
            "§7Pule de bloco em bloco.",
            "§7A §cdificuldade§7 só aumenta,",
            "§7cuidado para não §ccair§7!"
    }, new Stat[]{
            new Stat("Wins", "PARKOUR", Stat.Type.MAIOR),
            new Stat("Quedas", "PARKOUR", Stat.Type.MENOR)
    }, false, "PARKOUR"),
    MEGASW("Mega SkyWars", Item.item(Material.MYCEL), new String[]{
            "§7Em partidas de muitos §ejogadores§7, §cbatalhe",
            "§7em equipe contra os outros times."
    }, new Stat[]{
            new Stat("Wins", "MEGASW", Stat.Type.MAIOR),
            new Stat("Perdas", "MEGASW", Stat.Type.MENOR),
            new Stat("Kills", "MEGASW", Stat.Type.MAIOR),
            new Stat("Deaths", "MEGASW", Stat.Type.MENOR)
    }, false, "MEGASKYWARS"),
    SPLEEF("Spleef", Item.item(Material.IRON_SPADE), new String[]{
            "§7Com uma pá, quebre blocos de neve.",
            "§7Seu objetivo é §cderrubar §7seu adversário!"
    }, new Stat[]{
            new Stat("Wins", "SPLEEF", Stat.Type.MAIOR),
            new Stat("Kills", "SPLEEF", Stat.Type.MAIOR),
            new Stat("Deaths", "SPLEEF", Stat.Type.MENOR)
    }, true, "SPLEEF"),
    APOCALIPSE_ZUMBI("Apocalipse Zumbi", Item.getHead("MHF_Zombie", 1, ""), new String[]{
            "§7Sobreviva a um §capocalipse zumbi§7.",
            "§7A cada fase que passar,",
            "§7mais zumbis terá que §cenfrentar."
    }, new Stat[]{
            new Stat("Wins", "APOCALIPSE_ZUMBI", Stat.Type.MAIOR),
            new Stat("Perdas", "APOCALIPSE_ZUMBI", Stat.Type.MENOR),
            new Stat("Fases", "APOCALIPSE_ZUMBI", Stat.Type.MAIOR),
            new Stat("Kills", "APOCALIPSE_ZUMBI", Stat.Type.MAIOR),
            new Stat("Deaths", "APOCALIPSE_ZUMBI", Stat.Type.MENOR)
    }, false, "APOCALIPSEZUMBI"),
    TIRO_AO_ALVO("Tiro ao Alvo", Item.item(Material.BOW), new String[]{
            "§7Acerte flechas nos alvos. Cada §cc§eo§ar§7 vale ",
            "§7certos pontos. Quem tiver mais pontos, ganha."
    }, new Stat[]{
            new Stat("Wins", "TIRO_AO_ALVO", Stat.Type.MAIOR),
            new Stat("Perdas", "TIRO_AO_ALVO", Stat.Type.MENOR),
            new Stat("Pontos", "TIRO_AO_ALVO", Stat.Type.MAIOR),
    }, true, "TIROAOALVO"),
    CRAZY_ISLAND("Crazy Island", Item.getHead("MrBowZz", 1, ""), new String[]{
            "§7Sobreviva em uma ilha no meio do Oceano.",
            "§7Seu objetivo é matar o §etime adversário§7.",
            "§7Após certo tempo de batalha, há uma disputa final."
    }, new Stat[]{
            new Stat("Wins", "CRAZY_ISLAND", Stat.Type.MAIOR),
            new Stat("Perdas", "CRAZY_ISLAND", Stat.Type.MENOR),
            new Stat("Kills", "CRAZY_ISLAND", Stat.Type.MAIOR),
            new Stat("Deaths", "CRAZY_ISLAND", Stat.Type.MENOR)
    }, false, "CRAZYISLAND"),
    HARDCOREGAMES("Hardcore Games", Item.item(Material.MUSHROOM_SOUP), new String[]{
            "§7Um modo de §cPvP§7 com sopas, assim como o §aKitPvP.",
            "§7Faça suas §9sopas§7 e sobreviva ao §9máximo§7!",
            "§eQue vença o melhor!"
    }, new Stat[]{
            new Stat("Wins", "HARDCOREGAMES", Stat.Type.MAIOR),
            new Stat("Kills", "HARDCOREGAMES", Stat.Type.MAIOR),
            new Stat("Deaths", "HARDCOREGAMES", Stat.Type.MENOR)
    }, true, "HG"),
    BUILDBATTLE("Build Battle", Item.getHead("Miner", 1, ""), new String[]{
            "§7Jogue uma §9Batalha de Construção",
            "§7contra outros jogadores.",
            "§eVamos ver se você é bom mesmo!"
    }, new Stat[]{
            new Stat("Wins", "BUILDBATTLE", Stat.Type.MAIOR),
            new Stat("Perdas", "BUILDBATTLE", Stat.Type.MENOR),
            new Stat("Pontos", "BUILDBATTLE", Stat.Type.MAIOR)
    }, false, "BUILDBATTLE"),
    OITC("One in The Chamber", Item.item(Material.ARROW), new String[]{
            "§7Em uma arena, mate seus adversários,",
            "§7com uma espada ou com um arco.",
            "§eQuem matar mais, vence o MiniGame!"
    }, new Stat[]{
            new Stat("Wins", "OITC", Stat.Type.MAIOR),
            new Stat("Perdas", "OITC", Stat.Type.MENOR),
            new Stat("Kills", "OITC", Stat.Type.MAIOR),
            new Stat("Deaths", "OITC", Stat.Type.MENOR)
    }, false, "OITC"),
    CREEPER_RUN("Creeper Run", Item.getHead("MHF_Creeper", 1, ""), new String[]{
            "§7Seu objetivo é §ccorrer§7 dos jogadores",
            "§7que são §aCreepers§7. Caso seja explodido",
            "§7por algum, você também poderá §cexplodir",
            "§7jogadores a cada §e5 segundos§7, mas",
            "§7ficará §cvulnerável§7 nesse meio-tempo"
    }, new Stat[]{
            new Stat("Wins", "CREEPER_RUN", Stat.Type.MAIOR),
            new Stat("Perdas", "CREEPER_RUN", Stat.Type.MENOR),
            new Stat("Kills", "CREEPER_RUN", Stat.Type.MAIOR),
            new Stat("Deaths", "CREEPER_RUN", Stat.Type.MENOR)
    }, false, "CREEPERRUN");

    public static final ArrayList<String> statusType = new ArrayList<>();
    public static File file;
    public static FileConfiguration cfg;
    private final String name;
    private final ItemStack item;
    private final String[] description;
    private final Stat[] status;
    private final boolean active;
    private final String signName;

    GameMode(String name, ItemStack item, String[] description, Stat[] status, boolean active, String signName) {
        this.name = name;
        this.item = item;
        this.description = description;
        this.active = active;
        this.status = status;
        this.signName = signName;
        for(Stat s : status){
            s.setGameMode(this);
        }
    }

    public static ArrayList<ItemStack> getItems() {
        ArrayList<ItemStack> tipos = new ArrayList<>();
        for (GameMode tp : values()) {
            tipos.add(tp.getItem());
        }

        return tipos;
    }

    public static GameMode getGameMode(String name) {
        for (GameMode gameMode : values()) {
            if (gameMode.name().equalsIgnoreCase(name) || gameMode.getName().equalsIgnoreCase(name)) {
                return gameMode;
            }
        }
        return null;
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

    public Stat[] getStatus() {
        return status;
    }

    public boolean isActive() {
        return active;
    }

    public String getSignName() {
        return signName;
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
}
