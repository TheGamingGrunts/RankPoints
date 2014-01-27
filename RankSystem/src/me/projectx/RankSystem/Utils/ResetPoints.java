package me.projectx.RankSystem.Utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.projectx.RankSystem.Main;

public class ResetPoints {
	
	Main m;
	PointMethods pm;
	
	public ResetPoints(Main i){
		this.m = i;
		this.pm = new PointMethods(m);
	}
	
	/**
	 * This method is used to reset a given player's RankPoints to zero
	 * @param player : The player who's points will be reset
	 */
	public void resetPoints(Player player){
		FileConfiguration c = m.getPoints();
		String name = player.getName();
		
		if (!(c.getString(name) == null)){
			if (m.getConfig().getBoolean("Reset Points Using Percentage") == true){
				int startBal = pm.getPlayerPoints(player);
				int percentage = m.getConfig().getInt("Percent to Reset");
				int newBal = (startBal * (percentage/100));
				
				c.set(name, newBal);
				player.sendMessage(ChatColor.GRAY + "You died! Your RankPoints have been reduced by " + ChatColor.AQUA + percentage + "% "
						+ ChatColor.GRAY + "You now have " + ChatColor.AQUA + newBal + ChatColor.GRAY + " points");
			}else{
				c.set(name, 0);
				m.savePoints();
				player.sendMessage(ChatColor.GRAY + "You have died and your RankPoints have been set to " + ChatColor.AQUA + "0");
			}
		}else{
			c.set(name, 0);
			m.savePoints();
			System.out.println("[RankPoints] No data for " + name + ". Creating now and setting to default values");
		}
	}	
}
