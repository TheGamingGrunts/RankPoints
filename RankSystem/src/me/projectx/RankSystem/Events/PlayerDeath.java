package me.projectx.RankSystem.Events;

import me.projectx.RankSystem.Main;
import me.projectx.RankSystem.Utils.ResetPoints;
import me.projectx.RankSystem.Utils.VaultMethods;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
	
	Main m;
	ResetPoints rp;
	public PlayerDeath(Main i){
		this.m = i;
		this.rp = new ResetPoints(m);
	}
	/*
	 * TODO
	 * Give the killer the dead player's skull
	 */
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Location loc = e.getEntity().getLocation();
		
		/*Block b = loc.getBlock();
		b.setType(Material.SKULL);
		BlockState bs = b.getState();
		//b.setData((byte)0x1);
		
		if (bs instanceof Skull){
		    Skull skull = (Skull)bs;
		    
		    skull.setRotation(BlockFace.SOUTH_SOUTH_EAST);
		    skull.setSkullType(SkullType.PLAYER);
		    skull.setOwner(e.getEntity().getName());
		    skull.update();
		}*/
		
		rp.resetPoints(e.getEntity());
		VaultMethods.updateMoney(e.getEntity());
	}
}
