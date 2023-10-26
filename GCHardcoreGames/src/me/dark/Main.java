package me.dark;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.BarAPI.BarAPI;
import me.dark.Chests.Chests;
import me.dark.Cmd.AddChests;
import me.dark.Cmd.Admin;
import me.dark.Cmd.FeastCmd;
import me.dark.Cmd.ForceFeast;
import me.dark.Cmd.Jogo;
import me.dark.Cmd.MobSpawn;
import me.dark.Cmd.SKit;
import me.dark.Cmd.SetKit;
import me.dark.Cmd.Specs;
import me.dark.Cmd.Start;
import me.dark.Cmd.Status;
import me.dark.Cmd.Tempo;
import me.dark.Cmd.ToggleKits;
import me.dark.Cmd.TpAll;
import me.dark.Game.GameState;
import me.dark.Game.Timer.GameTimer;
import me.dark.MySQL.MySQL;
import me.dark.MySQL.SQLStatus;
import me.dark.Scoreboard.Scoreboard;
import me.dark.Utils.ClassGetter;
import me.dark.Utils.DarkUtils;
import me.dark.Utils.Schematic;
import me.dark.Utils.SpawnVillager;
import me.dark.Utils.VillagerType;
import me.dark.kit.Kit;
import me.dark.kit.KitCommand;
import me.dark.kit.KitSelector;

public class Main extends JavaPlugin{
	
	private static Main pl;
	
	public static MySQL sql;
	public static SQLStatus rank;
	public static World usingWorld;
	public static GameState estado = null;
	public static Scoreboard sb = new Scoreboard();
	
	public static Boolean toggleKits = true;
	
	public static HashMap<Player, Integer> rankPlayer = new HashMap<Player, Integer>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Block> blockf = new ArrayList<Block>();
	public static ArrayList<Kit> notToggled = new ArrayList<Kit>();

	public static String seta = "§7§l➛§f";
	public static String seta2 = "➛";
	public static String prefix = "§3§lGoodCraft " + seta + " ";
	public static String site = "htttp://good-craft.net/";
	public static String linha = "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
	
	public static String cooldown = "§bAguarde o cooldown de§3§l %cool §bsegundos!";
	public static String prefix_bussola = "§bBussola apontando para §3§l";
	public static String not_found = prefix+"Jogador não encontrado!";

	public static String perm_bronze = "good.bronze";
	public static String perm_prata = "good.prata";
	public static String perm_ouro = "good.ouro";
	public static String perm_yt = "good.yt";
	public static String perm_mod = "good.mod";
	public static String perm_modplus = "good.modplus";
	public static String perm_admin = "good.admin";
	
	public static int toStart = 300;
	public static int gameTime = 0;
	
	
	@Override
	public void onLoad(){
	    DarkUtils.getConsole().sendMessage("§3Apagando mundo...");
	    Bukkit.getServer().unloadWorld(usingWorld, false);
	    deleteDir(new File("world"));
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		pl = this;
		DarkUtils.sqlConnect();

		rank = new SQLStatus(this);
		reg();
		regSoup();
		usingWorld = Bukkit.getWorld("world");
		usingWorld.setAutoSave(false);
		DarkUtils.sendMessage("§3Carregando chunks...");
		DarkUtils.loadChunks();
		DarkUtils.sendMessage("§3Chunks carregados!");
		
		DarkUtils.sendMessage("§3Criando borda...");
		DarkUtils.createBoard();
		DarkUtils.sendMessage("§3Borda criada!");
		usingWorld.setSpawnLocation(0, 200, 0);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			sb.createBoard(p);
		}
		
		Location loca = new Location(usingWorld, usingWorld.getSpawnLocation().getX(), 198, usingWorld.getSpawnLocation().getZ());
		DarkUtils.sendMessage("§3Spawnando coliseu...");
		Schematic.spawn(loca, "Coliseu");
		DarkUtils.sendMessage("§3Coliseu spawnado!");

	    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		DarkUtils.getConsole().sendMessage(prefix + "Plugin iniciado!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, sb, 20, 20);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				SpawnVillager.spawnVillager("§cLavaChallenge", new Location(usingWorld, -12.5, 200, -9.5),VillagerType.LAVA);
				SpawnVillager.spawnVillager("§bLoja de Kits", new Location(usingWorld, -17.5, 200, 17.5),VillagerType.SHOP);
				DarkUtils.getConsole().sendMessage(prefix + "Villagers spawnados!");
				estado = GameState.PREGAME;
				GameTimer.preGame();
			}
		}.runTaskLater(Main.getMain(), 30);
	}
	

	public void reg() {
		
		regKits("me.dark.kit.habilidade");
		Collections.sort(Kit.kits, new Comparator<Object>() {  
            public int compare(Object o1, Object o2) {  
                Kit c1 = (Kit) o1;  
                Kit c2 = (Kit) o2;  
                return c1.toString().compareToIgnoreCase(c2.toString());
              }
         });
		registrarListenersEmPackage("me.dark.Listener");
		
		regL(new KitSelector());
		regL(new BarAPI());
		regL(new Admin());
		regL(new Chests());
		
		regCmd(new TpAll(), "tpall");
		regCmd(new Status(), "status");
		regCmd(new MobSpawn(), "mobspawn");
		regCmd(new Specs(), "specs");
		regCmd(new FeastCmd(), "feast");
		regCmd(new Jogo(), "jogo");
		regCmd(new Jogo(), "game");
		regCmd(new Jogo(), "info");
		regCmd(new Jogo(), "help");
		regCmd(new KitCommand(), "kit");
		regCmd(new Start(), "start");
		regCmd(new Tempo(), "tempo");
		regCmd(new Admin(), "admin");
		regCmd(new ForceFeast(), "ffeast");
		regCmd(new ToggleKits(), "togglekits");
		regCmd(new SKit(), "skit");
		regCmd(new AddChests(), "addchest");
		regCmd(new SetKit(), "setkit");
	}
	
	public static void deleteDir(File dir){
		if (dir.isDirectory()){
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(dir, children[i]));
			}
		}
	    dir.delete();
	}
	
	
	public static Main getMain() { 
		return pl; 
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
        sad.addIngredient(1, Material.INK_SACK, (short)3);
        sad.addIngredient(1, Material.BOWL);
        getServer().addRecipe(sad);
        
        ShapelessRecipe das = new ShapelessRecipe(is);
        das.addIngredient(1, Material.YELLOW_FLOWER);
        das.addIngredient(1, Material.RED_ROSE);
        das.addIngredient(1, Material.BOWL);
        getServer().addRecipe(das);
    }
    
    public void regKits(String packageName){
    	for (Class<?> kits : ClassGetter.getClassesForPackage(this, packageName)) {
    		if (Kit.class.isAssignableFrom(kits)) {
    			try {	        		        
					kits.newInstance();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
    
    
    public void registrarListenersEmPackage(String packageName){
    	for (Class<?> abilityClass : ClassGetter.getClassesForPackage(this, packageName)) {
    		if (Listener.class.isAssignableFrom(abilityClass)) {
    			try {	        		        
					Listener Listener = (Listener)abilityClass.newInstance();
					regL(Listener);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
    
	public void regL(Listener l) {
		Bukkit.getPluginManager().registerEvents(l, this);
		getServer().getConsoleSender().sendMessage("§3§l[!]§b Lendo classe" + l.getClass().toString().replace("class", "") + "!");
	}
	
	private void regCmd(CommandExecutor classe, String comando) {
		if (getCommand(comando) != null) {
			getCommand(comando).setExecutor(classe);
			getServer().getConsoleSender().sendMessage("§3§l[!]§b O comando /" + comando + " foi registrado");
		} else {
			getServer().getConsoleSender().sendMessage("§4§l[!]§4 O comando /" + comando + " retornou em null");
		}
	}
	
}
