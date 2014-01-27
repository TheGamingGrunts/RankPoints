package me.projectx.RankSystem.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.BoardMethods;
import me.projectx.RankSystem.Utils.PlayerBoard;

public class PlayerJoin implements Listener{
	
	Main m;
	PlayerBoard pb;
	BoardMethods bm;

	public PlayerJoin(Main i){
		this.m = i;
		this.pb = new PlayerBoard(m);
		this.bm = new BoardMethods(m);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		FileConfiguration c = m.getPoints();
		String name = p.getName();
		File players = new File(m.getDataFolder(), "players.yml");
		FileConfiguration playerConf = YamlConfiguration.loadConfiguration(players);
		
		if (c.getString(name) == null){
			c.set(name, 0);
			m.savePoints();
			System.out.println("Points file didn't contain " + name + " and they were added");
		}
		
		if (playerConf.getString(name) == null){
			playerConf.set(name + ".rank", m.getConfig().getString("Default Rank"));
			try{
				playerConf.save(players);
			}catch(IOException ex){
			}
		}
		
		bm.setBoardPoints(p);
		p.setScoreboard(Main.b1);	
	}
}
