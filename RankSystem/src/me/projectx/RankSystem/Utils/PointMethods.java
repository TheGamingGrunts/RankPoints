package me.projectx.RankSystem.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.projectx.RankSystem.Main;

public class PointMethods {
	
	Main m;
	PlayerBoard pb;
	BoardMethods bm;
	int points;
	
	public PointMethods(Main i){
		this.m = i;
		this.pb = new PlayerBoard(m);
		this.bm = new BoardMethods(m);
	}
	
	/**
	 * This method is used to set a given player's points to a specified value
	 * @param player : The player to set the points for
	 * @param value : The points
	 */
	public void setPoints(Player player, int value){
		FileConfiguration c = m.getPoints();
		String name = player.getName();
		
		if (c.getKeys(false).contains(name)){
			c.set(name, value);
			m.savePoints();
			bm.setBoardPoints(player);
		}
		VaultMethods.updateMoney(player);
	}
	
	/**
	 * This method is used to get the amount of points that a player has
	 * 
	 * @param player : The player to get the points
	 * @return The player's points
	 */
	public double getPlayerPoints(Player player){
		double points = VaultMethods.economy.getBalance(p.getName())
		VaultMethods.updateMoney(player);
		return points;
	}
}
