package me.projectx.RankSystem.Events;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.BoardMethods;
import me.projectx.RankSystem.Utils.PlayerBoard;
import me.projectx.RankSystem.Utils.RankMethods;
import me.projectx.RankSystem.Utils.VaultMethods;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKill implements Listener {
	
	Main m;
	RankMethods rm;
	PlayerBoard pb;
	String name;
	BoardMethods bm;
	int currentPoints;
	
	public EntityKill(Main i){
		this.m = i;
		this.rm = new RankMethods(m);
		this.pb = new PlayerBoard(m);
		this.bm = new BoardMethods(m);
	}
	
	/*
	 * TODO
	 * Create methods to reduce redundant code
	 */
	@EventHandler
	public void onKill(EntityDeathEvent e){
		Entity entity = e.getEntity();
		Entity killer = e.getEntity().getKiller();
		FileConfiguration config = m.getConfig();
		FileConfiguration points = m.getPoints();
		
		if (killer instanceof Player){
			Player p = (Player)killer;
			name = p.getName();
			currentPoints = points.getInt(name);
	
			if (entity instanceof Zombie){
				if (!(config.getString("Points.Zombie") == null)){
					if (!(points.getString(name) == null)){
						int newPoints = (currentPoints + config.getInt("Points.Zombie"));
						int difference = (newPoints - currentPoints);
						points.set(name, newPoints);
						m.savePoints();
						bm.setBoardPoints(p);
						p.sendMessage(ChatColor.GRAY + "You received " + ChatColor.AQUA + 
								difference + ChatColor.GRAY + " points for killing a " + ChatColor.AQUA + "Zombie");
						rm.updateRank(p);
					}else{
						System.out.println("Points file does not contain player name!");
					}
				}else{
					System.out.println("Zombie point value not set!");
				}
			}
			
			if (entity instanceof Skeleton){
				if (!(config.getString("Points.Skeleton") == null || config.getString("Points.Wither Skeleton") == null)){
					if (!(points.getString(name) == null)){
						if (((Skeleton) entity).getSkeletonType() == Skeleton.SkeletonType.NORMAL){
							int newPoints = (currentPoints + config.getInt("Points.Skeleton"));
							int difference = (newPoints - currentPoints);
							points.set(name, newPoints);
							m.savePoints();
							bm.setBoardPoints(p);
							p.sendMessage(ChatColor.GRAY + "You received " + ChatColor.AQUA + 
									difference + ChatColor.GRAY + " points for killing a " + ChatColor.AQUA + "Normal Skeleton");
							rm.updateRank(p);
						}
						
						else if (((Skeleton) entity).getSkeletonType() == Skeleton.SkeletonType.WITHER){
							int newPoints = (currentPoints + config.getInt("Points.Wither Skeleton"));
							int difference = (newPoints - currentPoints);
							points.set(name, newPoints);
							m.savePoints();
							bm.setBoardPoints(p);
							p.sendMessage(ChatColor.GRAY + "You received " + ChatColor.AQUA + 
									difference + ChatColor.GRAY + " points for killing a " + ChatColor.AQUA + "Wither Skeleton");
							rm.updateRank(p);
						}
					}else{
						System.out.println("Points file does not contain player name!");
					}
				}else{
					System.out.println("Skeleton point value not set!");
				}
			}
			
			if (entity instanceof Creeper){
				if (!(config.getString("Points.Creeper") == null)){
					if (!(points.getString(name) == null)){
						int newPoints = (currentPoints + config.getInt("Points.Creeper"));
						int difference = (newPoints - currentPoints);
						points.set(name, newPoints);
						m.savePoints();
						bm.setBoardPoints(p);
						p.sendMessage(ChatColor.GRAY + "You received " + ChatColor.AQUA + 
								difference + ChatColor.GRAY + " points for killing a " + ChatColor.AQUA + "Creeper");
						rm.updateRank(p);
					}else{
						System.out.println("Points file does not contain player name!");	
					}
				}else{
					System.out.println("Creeper point value not set!");	
				}
			}
			
			if (entity instanceof Player){
				if (!(config.getString("Points.Player") == null)){
					if (!(points.getString(name) == null)){
						int newPoints = (currentPoints + config.getInt("Points.Player"));
						int difference = (newPoints - currentPoints);
						points.set(name, newPoints);
						m.savePoints();
						bm.setBoardPoints(p);
						p.sendMessage(ChatColor.GRAY + "You received " + ChatColor.AQUA + 
								difference + ChatColor.GRAY + " points for killing a " + ChatColor.AQUA + "Player");
						rm.updateRank(p);
					}else{
						System.out.println("Points file does not contain player name!");	
					}
				}else{
					System.out.println("Player point value not set!");	
				}	
			}
			VaultMethods.updateMoney(p);
		}else{
		}
	}
}
