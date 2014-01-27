package me.projectx.RankSystem.Events;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.BoardMethods;
import me.projectx.RankSystem.Utils.PointMethods;
import me.projectx.RankSystem.Utils.RankMethods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {
	
	Main m;
	PointMethods pm;
	RankMethods rm;
	BoardMethods bm;
	
	/**
	 * This class handles player interaction with signs 
	 * @param i : Pass the Main class into here
	 */
	public PlayerInteract(Main i){
		this.m = i;
		this.pm = new PointMethods(m);
		this.rm = new RankMethods(m);
		this.bm = new BoardMethods(m);
	}
	
	/*
	 * Bug Update:
	 * instanceof Bug Fixed
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			if((block.getType() == Material.WALL_SIGN) || (block.getType() == Material.SIGN_POST)){
				Sign s = (Sign) e.getClickedBlock().getState();
				ItemStack skull = new ItemStack(Material.SKULL_ITEM);
				if (p.getItemInHand() == skull){
					if (s.getLine(0).equals("[RankPoints]")){
						try{
							int current = pm.getPlayerPoints(p);
							Integer i = Integer.parseInt(s.getLine(1));
							int newBal = (current - i);
							
							pm.setPoints(p, newBal);
							rm.updateRank(p);
							bm.setBoardPoints(p);
							
						}catch(NumberFormatException ex){
	        				  p.sendMessage(ChatColor.DARK_RED + "Sign has not been formatted correctly. Please contact an admin");
						}
					}
				}
			}
		}else{
			
		}
	}
}
