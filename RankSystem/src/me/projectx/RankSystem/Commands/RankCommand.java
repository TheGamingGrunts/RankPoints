package me.projectx.RankSystem.Commands;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.BoardMethods;
import me.projectx.RankSystem.Utils.RankMethods;
import me.projectx.RankSystem.Utils.PointMethods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
	
	PointMethods pm;
	RankMethods rm;
	BoardMethods bm;
	Main m;
	
	/*
	 * TODO
	 * Implement Separate classes for each command to neaten up code
	 */
	
	public RankCommand(Main i){
		this.m = i;
		this.pm = new PointMethods(m);
		this.rm = new RankMethods(m);
		this.bm = new BoardMethods(m);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		//Player p = (Player) sender;
		
		if (commandLabel.equalsIgnoreCase("points")){
			if (args.length > 2){
				Player t = Bukkit.getServer().getPlayer(args[1]);
				if (args[0].equalsIgnoreCase("add")){
					try{
						Integer value = Integer.parseInt(args[2]);
						pm.setPoints(t, value);
						bm.setBoardPoints(t);
						t.sendMessage(ChatColor.GRAY + "You have just received " + 
								ChatColor.AQUA + value + ChatColor.GRAY + " points");
					}catch(NumberFormatException e){
						sender.sendMessage(ChatColor.DARK_RED + "Points can only be numbers, silly");
					}
				}
				
				if (args[0].equalsIgnoreCase("remove")){
					//implement methods
				}
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "Invalid Arguments. Try /points <add/remove> <player> <value>");
			}
		}
		
		if (commandLabel.equalsIgnoreCase("getrank")){
			if (args.length > 0){
				Player t = Bukkit.getServer().getPlayer(args[0]);
				
				sender.sendMessage(ChatColor.AQUA + t.getName() + ChatColor.GRAY + " is a " + ChatColor.AQUA + rm.getRank(t));
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "Invalid Arguments. Try /getrank <player>");
			}
		}
		
		if (commandLabel.equalsIgnoreCase("getpoints")){
			if (args.length > 0){
				Player t = Bukkit.getServer().getPlayer(args[0]);
				
				sender.sendMessage(ChatColor.AQUA + t.getName() + ChatColor.GRAY + " has " + 
						ChatColor.AQUA + pm.getPlayerPoints(t) + ChatColor.GRAY + " points");
			}else{
				sender.sendMessage(ChatColor.DARK_RED + "Invalid Arguments. Try /getpoints <player>");
			}
		}
		return false;
	}
}

