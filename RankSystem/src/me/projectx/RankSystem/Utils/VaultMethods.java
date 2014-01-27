package me.projectx.RankSystem.Utils;

import me.projectx.RankSystem.Main;

import org.bukkit.entity.Player;

public class VaultMethods {
	
	public static void updateMoney(Player p) {
		int moneyConfig = Main.pmethods.getPlayerPoints(p);
		double vaultMoney = Main.economy.getBalance(p.getName());
		if(!(moneyConfig == vaultMoney)) {
			Main.economy.depositPlayer(p.getName(), (double) moneyConfig);
		}
	}

}
