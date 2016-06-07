package com.sanjay900.jnb;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.sanjay900.jnb.listeners.PlayerListener;
import com.sanjay900.jnb.listeners.WonderCommand;
import com.sanjay900.jnb.utils.RandomSpawn;
import com.sanjay900.jnb.utils.WonderlandPlayer;

public class jnb extends JavaPlugin {
	public int rabbit = 0;
	public PlayerListener pl;
	public ArrayList<WonderlandPlayer> players = new ArrayList<>();
	public ArrayList<JnbGame> games = new ArrayList<>();
	public RandomSpawn randomspawn = new RandomSpawn();
    @Override
    public void onEnable() {
        getCommand("jnb").setExecutor(new WonderCommand(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        pl = new PlayerListener(this);
        	     Bukkit.getPluginManager().registerEvents(pl, this);
        	     for (World world: Bukkit.getWorlds()) {
        	    	 games.add(new JnbGame(this,world));
        	     }
        	   

        		

    }
   
    public WonderlandPlayer getPlayer(Player player) {
        for (WonderlandPlayer p : players) {
        	
            if (p.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return p;
            }
        }
        return null;
    }
    @Override
    public void onDisable() {
    	for (WonderlandPlayer p : players) 
    	{
    		p.stopGame();
    	}
    	
    	ProtocolLibrary.getProtocolManager().removePacketListeners(this);

    }

	public JnbGame getGame(String name) {
		for (JnbGame game : games) {
			if (game.world.equals(name)) return game;
		}
		return null;
	}


}

