package me.projectx.RankSystem.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.projectx.RankSystem.Main;

public class BoardMethods {
	
	Main m;
	
	public BoardMethods(Main i){
		this.m = i;
	}
	
	/**
	 * Used as a quick method to update the Scoreboard points
	 * @param player
	 */
	public void setBoardPoints(Player player){
		FileConfiguration c = m.getPoints();
		String name = player.getName();
		Main.s1.setScore(c.getInt(name));
		Main.s2.setScore(c.getInt(name));
	}
}
