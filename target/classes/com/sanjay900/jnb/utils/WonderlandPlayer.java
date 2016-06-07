package com.sanjay900.jnb.utils;

import java.util.UUID;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.RabbitType;
import me.libraryaddict.disguise.disguisetypes.watchers.RabbitWatcher;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

import com.sanjay900.jnb.JnbGame;
import com.sanjay900.jnb.jnb;

public class WonderlandPlayer{
	
	
	private UUID player;
	public boolean stop = false;
	RabbitType rt;
	public JnbGame game;
	public jnb plugin;
	public WonderlandPlayer(Player p, jnb plugin) {
		this.plugin = plugin;
		rt = RabbitType.getType(plugin.rabbit);
		plugin.rabbit++;
		if (plugin.rabbit > 6) plugin.rabbit = 0;
		this.setPlayer(p);
		@SuppressWarnings("deprecation")
		MobDisguise mobDisguise = new MobDisguise(DisguiseType.RABBIT, false, true);
		DisguiseAPI.disguiseToAll(p,mobDisguise);
		RabbitWatcher ocelotWatcher = (RabbitWatcher) mobDisguise.getWatcher();
		ocelotWatcher.setType(rt);
		ocelotWatcher.setCustomName(p.getDisplayName());
		ocelotWatcher.setCustomNameVisible(true);
	}
	public void kill(Player killer) {
		
		getPlayer().getLocation().getWorld().playEffect(getPlayer().getLocation(), Effect.STEP_SOUND, 152);
		respawn();
		game.addScore(killer);
		game.sendMessageToAll(getPlayer().getDisplayName()+" was squashed by "+killer.getDisplayName());
	}
	public void respawn() {
		getPlayer().teleport(plugin.randomspawn.chooseSpawn(getPlayer().getWorld()));
	}
	public void stopGame() {
		game.stopGame();
		stop = true;
		DisguiseAPI.undisguiseToAll(getPlayer());
		
		 
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(player);
	}

	public void setPlayer(Player player) {
		this.player = player.getUniqueId();
	}
}
