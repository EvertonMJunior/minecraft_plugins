package me.everton.WocPvP;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {

	private SettingsManager() {
	}

	static SettingsManager instance = new SettingsManager();

	public static SettingsManager getInstance() {
		return instance;
	}

	Plugin p;

	FileConfiguration config;
	File cfile;

	FileConfiguration money;
	File mfile;

	public static FileConfiguration score;
	File sfile;

	FileConfiguration itens;
	File ifile;

	public void setup(Plugin p) {
		cfile = new File(p.getDataFolder(), "config.yml");
		config = p.getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}

		mfile = new File(p.getDataFolder(), "money.yml");

		if (!mfile.exists()) {
			try {
				mfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger()
						.severe(ChatColor.RED + "Erro ao criar money.yml!");
			}
		}

		money = YamlConfiguration.loadConfiguration(mfile);

		sfile = new File(p.getDataFolder(), "score.yml");

		if (!sfile.exists()) {
			try {
				sfile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger()
						.severe(ChatColor.RED + "Erro ao criar score.yml!");
			}
		}

		score = YamlConfiguration.loadConfiguration(sfile);

		ifile = new File(p.getDataFolder(), "itens.yml");

		if (!ifile.exists()) {
			try {
				ifile.createNewFile();
			} catch (IOException e) {
				Bukkit.getServer().getLogger()
						.severe(ChatColor.RED + "Erro ao criar itens.yml!");
			}
		}

		itens = YamlConfiguration.loadConfiguration(ifile);

		money.options().copyDefaults(true);
		score.options().copyDefaults(true);
		itens.options().copyDefaults(true);
		saveItens();
		saveMoney();
		saveScore();
		saveConfig();
	}

	public FileConfiguration getMoney() {
		return money;
	}

	public void saveMoney() {
		try {
			money.save(mfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Erro ao salvar money.yml!");
		}
	}

	public void reloadMoney() {
		money = YamlConfiguration.loadConfiguration(mfile);
	}

	public FileConfiguration getItens() {
		return itens;
	}

	public void saveItens() {
		try {
			itens.save(ifile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Erro ao salvar itens.yml!");
		}
	}

	public void reloadItens() {
		itens = YamlConfiguration.loadConfiguration(ifile);
	}

	public FileConfiguration getScore() {
		return score;
	}

	public void saveScore() {
		try {
			score.save(sfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Erro ao salvar score.yml!");
		}
	}

	public void reloadScore() {
		score = YamlConfiguration.loadConfiguration(sfile);
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger()
					.severe(ChatColor.RED + "Erro ao salvar config.yml!");
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}

	public PluginDescriptionFile getDesc() {
		return p.getDescription();
	}
}