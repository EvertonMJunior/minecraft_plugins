package net.goodcraft.lobby;

import com.dsh105.echopet.api.EchoPetAPI;
import net.goodcraft.GameMode;
import net.goodcraft.api.Item;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.lobby.eventos.*;
import net.goodcraft.lobby.npcs.NPCAPI;
import net.goodcraft.lobby.npcs.RefreshLobbyNPC;
import net.goodcraft.lobby.npcs.RefreshRankNPC;
import net.goodcraft.lobby.pets.PetBuyMenu;
import net.goodcraft.lobby.pets.PetDataMenuListener;
import net.goodcraft.lobby.pets.PetSelector;
import net.goodcraft.lobby.pets.PetSettings;
import net.goodcraft.lobby.signs.SignEvent;
import net.goodcraft.lobby.signs.SignsTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Plugin plugin;
    private static EchoPetAPI petAPI;
    private static Scoreboard sb;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static EchoPetAPI getPetAPI() {
        return petAPI;
    }

    public static Scoreboard getScoreboard() {
        return sb;
    }

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Enabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        setup();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disabled {0} v{1}", new Object[]{this.getName(), this.getDescription().getVersion()});
        disable();

        super.onDisable();
    }

    private void setup() {
        plugin = this;
        petAPI = EchoPetAPI.getAPI();
        sb = new Scoreboard();
        setBook();
        BussolaEvent.saveSocialMediasGui();
        ExtrasEvent.saveInv();

        Utils.registerCommands(getFile());
        registerListeners();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, sb, 60L, 20L);

        NPCAPI.file = new File("plugins/GCLobby/", "npcs.yml");
        NPCAPI.cfg = YamlConfiguration.loadConfiguration(NPCAPI.file);

        GameMode.file = new File("plugins/GCLobby/", "locations.yml");
        GameMode.cfg = YamlConfiguration.loadConfiguration(GameMode.file);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new RefreshLobbyNPC(), 5 * 20L, 5 * 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new RefreshRankNPC(), 5 * 20L, 5 * 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LobbySelectorEvent(), 5 * 20L, 5 * 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SignsTask(), 2 * 20L, 2 * 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                World w = Bukkit.getWorld("Mundo");
                w.setAutoSave(false);
                for (int x = -510; x <= 510; x++) {
                    for (int z = -510; z <= 510; z++) {
                        for (int y = 1; y <= 150; y++) {
                            Location loc = new Location(w, x, y, z);
                            if (!loc.getChunk().isLoaded()) {
                                loc.getChunk().load();
                            }
                        }
                    }
                }
                LobbySelectorEvent.initialize();
            }
        }.runTaskLater(this, 30L);
    }

    private void setBook() {
        ItemStack book = Item.item(Material.WRITTEN_BOOK, 1, "§9§lInformações §7(Clique Direito)");
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.setTitle("Informações");
        bm.setAuthor("GoodCraft");

        ArrayList<String> pages = new ArrayList<>();

        pages.add(0, "§0§l================\n   §4§lINFORMAÇõES \n§0§l================\n\n§0O servidor está em fase BETA, então bugs serão normais! Caso você  um, podes reportar em nosso fórum.");

        String ranks = "";
        for (Rank r : Rank.values()) {
            if (r.ordinal() >= Rank.OURO.ordinal()) {
                break;
            } else {
                String completo = r.getAliases().get(1);
                String prefixo = r.getColor() + "[" + r.getAliases().get(0) + "] ";
                completo = completo.substring(0, 1).toUpperCase() + completo.substring(1);
                ranks += prefixo + " §0- " + completo + " \n";
            }
        }


        pages.add(1, "§0§l================\n        §4§lRANKS \n§0§l================\n\n" + ranks);
        pages.add(2, "§0§l================\n      §4§lCOMPRAS \n§0§l================\n\n§0- Você pode comprar §4§lVIPs §0e §4§lGoodCoins" +
                " §0no servidor pela §4§lLOJA.\n§0" + "Passe a pagina para ver seu endereço!");
        pages.add(3, "§0§l================\n        §4§lLINKS \n§0§l================\n\n" +
                "§4§lSite§0: §0www.good-craft.net\n§4§lLoja§0: loja.good-craft.net\n" +
                "§4§lFórum§0: www.good-craft.net/forum\n" +
                "§4§lRedes Sociais§0: Veja na bússola");
        pages.add(4, "§0Obrigado por jogar em nosso servidor! Caso tenha alguma dúvida, entre em contato com a equipe através do fórum.\n" +
                "Bom jogo!\n \n§4§lRegras§0: www.good-craft.net\n \n \nObrigado,\n §3§lGoodCraft§0.");

        bm.setPages(pages);

        book.setItemMeta(bm);

        JoinEvent.book = book;
    }

    private void registerListeners() {
        registerListener(new JoinEvent());
        registerListener(new QuitEvent());
        registerListener(new DoubleJumpEvent());
        registerListener(new LocationChooserEvent());
        registerListener(new GeneralEvents());
        registerListener(new BussolaEvent());
        registerListener(new HidePlayersEvent());
        registerListener(new StatusEvent());
        registerListener(new ExtrasEvent());
        registerListener(new LobbySelectorEvent());
        registerListener(new PetSelector(null));
        registerListener(new PetSettings(null));
        registerListener(new PetDataMenuListener());
        registerListener(new PetCmdEvent());
        registerListener(new PulaPulaEvent());
        registerListener(new LavaChallengeEvent());
        registerListener(new PetBuyMenu(null, null));
        registerListener(new ArenaKnockEvent());
        registerListener(new SignEvent());
        registerListener(new LobbyNPCEvent());
    }

    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

    private void disable() {
        plugin = null;
        petAPI = null;
        sb = null;
    }
}
