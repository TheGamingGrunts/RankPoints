package me.projectx.RankSystem.Utils;

import me.projectx.RankSystem.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerBoard {
	
	public static Scoreboard b1;
	PointMethods pm;
	Main m;
	public static Score s1, s2;
	
	public PlayerBoard(Main i){
		this.m = i;
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
	
	/**
	 * Sets the RankPoint scoreboard for a given player
	 * @param player : The player to set the board for
	 */
	public void setBoard(Player player){
		player.setScoreboard(b1);
		s1.setScore(m.getPoints().getInt(player.getName()));
		s2.setScore(m.getPoints().getInt(player.getName()));
		//throws NPE. Need to fix
	}
	
	/**
	 * Updates the RankPoint board for a given player
	 * @param player : The player who's board will be updating
	 */
	public void updateBoard(Player player){
		s1.setScore(pm.getPlayerPoints(player));
		s2.setScore(pm.getPlayerPoints(player));	
	}
}
