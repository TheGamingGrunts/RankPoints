package me.projectx.RankSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import me.projectx.RankSystem.Commands.RankCommand;
import me.projectx.RankSystem.Events.*;
import me.projectx.RankSystem.Utils.PointMethods;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin{
	
	File points;
	FileConfiguration config;
	public static Scoreboard b1;
	public static PointMethods pmethods;
	public static Score s1, s2;
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EntityDamage(this), this);
		pm.registerEvents(new EntityKill(this), this);
		pm.registerEvents(new PlayerDeath(this), this);
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		
		getCommand("points").setExecutor(new RankCommand(this));
		getCommand("getrank").setExecutor(new RankCommand(this));
		getCommand("getPoints").setExecutor(new RankCommand(this));
		
		createFiles();
		registerRankboards();
		setupPermissions();
		setupChat();
		setupEconomy();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					int moneyConfig = pmethods.getPlayerPoints(p);
					double vaultMoney = economy.getBalance(p.getName());
					if(!(moneyConfig == vaultMoney)) {
						economy.depositPlayer(p.getName(), (double) moneyConfig);
					}
				}
			}
			
		}, 30 * 20, 15);
	}
/*-------------------------------------------------[Vault Methods]---------------------------------------------------------------------*/
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
/*---------------------------------------------[Point File Methods]---------------------------------------------------------------------*/
	public void saveDefaultPoints(){
		if (points == null){
			points = new File (getDataFolder(), "points.yml");
		}
		
		if (!(points.exists())){
			saveResource("points.yml", false);	
		}
		
		config = YamlConfiguration.loadConfiguration(points);
	}
	
	public void savePoints(){
		if (config == null || points == null){
			saveDefaultPoints();
			return;
		}
		
		try{
			getPoints().save(points);
		}catch(IOException e){
		}
	}
	
	public FileConfiguration getPoints(){
		if (config == null){
			reloadPoints();
		}
		
		return config;
	}

	public void reloadPoints() {
		if (points == null){
			points = new File(getDataFolder(), "points.yml");
		}
		
		config = YamlConfiguration.loadConfiguration(points);
		
		InputStream configStream = getResource("points.yml");
		if (!(configStream == null)){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(configStream);
			config.setDefaults(defConfig);
		}
	}
	
	/**
	 * Checks if config.yml , points.yml, and players.yml exist and creates them, if necessary 
	 */
	public void createFiles(){
		File config = new File(this.getDataFolder(), "config.yml");
		if (!config.exists()){
			this.saveDefaultConfig();
		}
		
		File points = new File(this.getDataFolder(), "points.yml");
		if (!points.exists()){
			this.saveDefaultPoints();
		}
		
		File players = new File(this.getDataFolder(), "players.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(players);
		if (!players.exists()){
			try{
				c.save(players);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Registers and creates the RankPoint scoreboards
	 */
	public void registerRankboards(){
		ScoreboardManager sb = Bukkit.getScoreboardManager();
		b1 = sb.getNewScoreboard();
		
		Objective obj1 = b1.registerNewObjective("herp", "derp");
		Objective obj2 = b1.registerNewObjective("herpity", "derpity");
		
		obj1.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj1.setDisplayName(ChatColor.AQUA + "RankPoints");
		
		obj2.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj2.setDisplayName(ChatColor.AQUA + "RankPoints");
		
		s1 = obj1.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA + "RankPoints"));
		s2 = obj2.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "RankPoints"));
	}
}