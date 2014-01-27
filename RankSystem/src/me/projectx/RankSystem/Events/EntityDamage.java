package me.projectx.RankSystem.Events;

import java.io.File;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.RankMethods;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {
	
	RankMethods rm;
	Main m;
	
	public EntityDamage(Main i){
		this.m = i;
		this.rm = new RankMethods(m);
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		Entity damager = e.getDamager();
		Player p = (Player) damager;
		File players = new File(m.getDataFolder(), "players.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(players);
		
		if (c.contains(p.getName())){
			e.setDamage(e.getDamage() + rm.getPlayerAttackDamage(p));
			System.out.println(e.getDamage() + rm.getPlayerAttackDamage(p));
		}
	}
}
