package me.projectx.RankSystem.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.projectx.RankSystem.Main;

public class RankMethods {
	
	Main m;
	int reqPoints, playerPoints;
	int attackDmg;
	public RankMethods(Main i){
		this.m = i;
	}
	
	/**
	 * Updates a player's rank if they have the required points
	 * 
	 * @param player : The player who's rank will be updated
	 */
	public void updateRank(Player player){
		File players = new File(m.getDataFolder(), "players.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(players);
		
		if (checkPoints(player) == getRequiredPoints("Novice")){
			//if (c.getString(player.getName() + ".rank") == "Weakling"){
				setRank(player, "Novice");
				player.sendMessage(ChatColor.GRAY + "You have earned enough points to become a " + ChatColor.AQUA + getRank(player));
			//}
		}

		if (checkPoints(player) == getRequiredPoints("Warrior")){
			//if (c.getString(player.getName() + ".rank") == "Novice"){
				setRank(player, "Warrior");
				player.sendMessage(ChatColor.GRAY + "You have earned enough points to become a " + ChatColor.AQUA + getRank(player));
			//}
		}
	}

	/**
	 * Checks the number of points that a player has
	 * 
	 * @param player : The player to check
	 * @return The number of points the player has
	 */
	public int checkPoints(Player player){
		FileConfiguration c = m.getPoints();
		String name = player.getName();
		playerPoints = c.getInt(name);
		
		return playerPoints;	
	}
	
	/**
	 * Gets the required points a given rank needs in order for a player to get the rank
	 * 
	 * @param rank : The rank to check. Must be spelled exactly as-is in the config.yml
	 * @return The number of points a player needs in order to get the rank
	 */
	public int getRequiredPoints(String rank){
		FileConfiguration c = m.getConfig();
		reqPoints = c.getInt("Ranks." + rank + ".requiredpoints");
		
		return reqPoints;		
	}
	
	/**
	 * Sets the rank of a given player to a specific rank
	 * 
	 * @param player : The player who's rank will be set
	 * @param rank : The name of the rank to set. Must typed as is in the config.yml 
	 */
	public void setRank(Player player, String rank){
		File players = new File(m.getDataFolder(), "players.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(players);
		String name = player.getName();
		
		c.set(name + ".rank", rank);
		try{
			c.save(players);
		}catch(IOException e){
		}
	}
	
	/**
	 * Gets the rank of a given player
	 * 
	 * @param player : The player to get the rank
	 * @return The player's rank
	 */
	public String getRank(Player player){
		File players = new File(m.getDataFolder(), "players.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(players);
		String rank = c.getString(player.getName() + ".rank");
		
		return rank;
	}
	
	/**
	 * Gets the amount of bonus damage a player can do to an entity
	 * 
	 * @param player : The player to get the attack value of
	 * @return The damage bonus value
	 */
	public int getPlayerAttackDamage(Player player){
		FileConfiguration c = m.getConfig();
		
		if (getRank(player).equals("Weakling")){
			attackDmg = c.getInt("Ranks.Weakling.attackdmg");
			System.out.println(attackDmg);
		}
		
		if (getRank(player).equals("Novice")){
			attackDmg = c.getInt("Ranks.Novice.attackdmg");
			System.out.println(attackDmg);
		}
		
		if (getRank(player).equals("Warrior")){
			attackDmg = c.getInt("Ranks.Warrior.attackdmg");
			System.out.println(attackDmg);
		}
		
		return attackDmg;
	}
}
