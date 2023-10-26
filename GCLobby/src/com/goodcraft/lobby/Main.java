package com.goodcraft.lobby;

import com.dsh105.echopet.api.EchoPetAPI;
import com.goodcraft.GameMode;
import com.goodcraft.api.Item;
import com.goodcraft.lobby.eventos.ArenaKnockEvent;
import com.goodcraft.lobby.eventos.BussolaEvent;
import com.goodcraft.lobby.eventos.DoubleJumpEvent;
import com.goodcraft.lobby.eventos.ExtrasEvent;
import com.goodcraft.lobby.eventos.GeneralEvents;
import com.goodcraft.lobby.eventos.HidePlayersEvent;
import com.goodcraft.lobby.eventos.JoinEvent;
import com.goodcraft.lobby.eventos.LavaChallengeEvent;
import com.goodcraft.lobby.eventos.LobbySelectorEvent;
import com.goodcraft.lobby.eventos.LocationChooserEvent;
import com.goodcraft.lobby.eventos.PetCmdEvent;
import com.goodcraft.lobby.eventos.PulaPulaEvent;
import com.goodcraft.lobby.eventos.QuitEvent;
import com.goodcraft.lobby.eventos.StatusEvent;
import com.goodcraft.lobby.npcs.NPCAPI;
import com.goodcraft.lobby.npcs.RefreshLobbyNPC;
import com.goodcraft.lobby.npcs.RefreshRankNPC;
import com.goodcraft.lobby.pets.PetBuyMenu;
import com.goodcraft.lobby.pets.PetDataMenuListener;
import com.goodcraft.lobby.pets.PetSelector;
import com.goodcraft.lobby.pets.PetSettings;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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
        try {
            registerCommands();
            registerListeners();
        } catch (Exception ex) {
        }

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

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, sb, 20L, 20L);
        
        NPCAPI.file = new File("plugins/GCLobby/", "npcs.yml");
        NPCAPI.cfg = YamlConfiguration.loadConfiguration(NPCAPI.file);
        
        GameMode.file = new File("plugins/GCLobby/", "locations.yml");
        GameMode.cfg = YamlConfiguration.loadConfiguration(GameMode.file);
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new RefreshLobbyNPC(), 5 * 20L, 5 * 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new RefreshRankNPC(), 5 * 20L, 60 * 20L);
    }

    private void setBook() {
        ItemStack book = Item.item(Material.WRITTEN_BOOK, 1, "§9§lInformações §7(Clique Direito)");
        BookMeta bm = (BookMeta) book.getItemMeta();
        bm.setTitle("Informações");
        bm.setAuthor("GoodCraft Network");

        ArrayList<String> pages = new ArrayList<>();

        pages.add(0, "§0§l================\n   §4§lINFORMAÇÕES \n§0§l================\n\n§0O servidor está em fase BETA, então bugs serão normais! Caso veres um, podes reportar em nosso fórum.");
        pages.add(1, "§0§l================\n        §4§lTAGS \n§0§l================\n\n§4DONO §0- Dono Geral\n§cADM §0- Administrador\n§5MOD §0-Moderador\n§aCNT §0- Construtor\n§3DEV §0- Programador\n§dAJD §0- Ajudante");
        pages.add(2, "§0§l================\n      §4§lCOMPRAS \n§0§l================\n\n§0- Você pode fazer compras de §4§lVIPs/XP/GoodCoins §0no servidor pelo §4§lSITE.");
        pages.add(3, "§0§l================\n        §4§lLINKS \n§0§l================\n\n§4§lSite§0: §0Good-Craft.net\n§4§lFórum§0: Good-Craft.net/forum\n§4§lTwitter§0: §0@GoodCraft_GMG\n§4§lFacebook§0: §0fb.com/GoodCraft_Oficial");
        pages.add(4, "§0Obrigado por jogar em nosso servidor! Caso tenha alguma dúvida, entre em contato com a equipe através do fórum.\nBom jogo!\n \n§4§lRegras§0: Good-Craft.net\n \n \nObrigado,\n §3§lGoodCraft§0.");

        bm.setPages(pages);

        book.setItemMeta(bm);

        JoinEvent.book = book;
    }

    private void disable() {
        plugin = null;
        petAPI = null;
        sb = null;
    }

    private void registerCommands() throws Exception {
        try (JarInputStream is = new JarInputStream(new FileInputStream(getFile()))) {
            for (JarEntry entry; (entry = is.getNextJarEntry()) != null;) {
                if (!entry.getName().endsWith(".class")) {
                    continue;
                }
                String path = entry.getName().replace("/", ".").replace(".class", "");
                if (path.contains(".comandos.")) {
                    Class cl = Class.forName(path);
                    Object o = cl.newInstance();
                    Method md = cl.getMethod("register");
                    md.invoke(o);
                }
            }
        }
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
    }

    private void registerListener(Listener l) {
        getServer().getPluginManager().registerEvents(l, this);
    }

}
