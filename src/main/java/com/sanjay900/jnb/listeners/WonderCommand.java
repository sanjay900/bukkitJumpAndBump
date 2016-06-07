package com.sanjay900.jnb.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sanjay900.jnb.jnb;
import com.sanjay900.jnb.utils.WonderlandPlayer;


public class WonderCommand implements CommandExecutor {


	jnb plugin;

	public WonderCommand(jnb wonderland) {
		plugin = wonderland;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player senderPlayer = null;
		if (sender instanceof Player) {
			senderPlayer = (Player) sender;


			if (cmd.getName().equalsIgnoreCase("jnb")) {

				if (args[0].equals("start")) {
					if (plugin.getPlayer(senderPlayer)==null) {

						ArrayList<WonderlandPlayer> players = new ArrayList<>();
						for (Player p :Bukkit.getOnlinePlayers()) {
							p.setGameMode(GameMode.CREATIVE);
							p.setAllowFlight(false);
							p.setFlying(false);
							players.add(new WonderlandPlayer(p,plugin));
						}
						plugin.players.addAll(players);
						plugin.getGame(senderPlayer.getWorld().getName()).startgame(players.toArray(new WonderlandPlayer[0]));
					}

				}


				if (args[0].equals("stop")) {
					if (plugin.getPlayer(senderPlayer)!=null) {
						plugin.getPlayer(senderPlayer).stopGame();
						plugin.players.remove(plugin.getPlayer(senderPlayer));  
					}

				}

			}

		} else {
			senderPlayer = Bukkit.getPlayer(args[1]);
			if (cmd.getName().equalsIgnoreCase("jnb")) {
				if (args[0].equals("start")) {
					if (plugin.getPlayer(senderPlayer)==null) {
						plugin.players.add(new WonderlandPlayer(senderPlayer,plugin));
					}
				}

				if (args[0].equals("stop")) {
					if (plugin.getPlayer(senderPlayer)!=null) {
						plugin.getPlayer(senderPlayer).stopGame();
						plugin.players.remove(plugin.getPlayer(senderPlayer));  
					}

				}

			}



		}
		return true;
	}

	/**
	 * Combines the given array of {@link String} objects into a single (@link
	 * String}
	 *
	 * @param args the (@link String} array to combine
	 * @return the combined string
	 */
	public String getText(String[] args) {
		String rv = "";
		for (int t = 2; t < args.length; t++) {
			if (t == args.length - 1) {
				rv += args[t];
			} else {
				rv += args[t] + " ";
			}
		}
		return rv;
	}



}
