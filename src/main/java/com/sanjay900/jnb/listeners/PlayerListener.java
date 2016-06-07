package com.sanjay900.jnb.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.sanjay900.jnb.jnb;
import com.sanjay900.jnb.utils.Utils;
import com.sanjay900.jnb.utils.WonderlandPlayer;

@SuppressWarnings("unused")
public class PlayerListener implements Listener {
	private jnb plugin;

	public PlayerListener(jnb plugin) {
		this.plugin = plugin;
	}

	boolean checked = false;
	public boolean stop;

	@EventHandler
	public void itemDrop(PlayerDropItemEvent evt) {
		if (plugin.getPlayer(evt.getPlayer()) != null) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void itemGetEvent(PlayerPickupItemEvent event) {
		if (plugin.getPlayer(event.getPlayer()) == null) return;
		if (Utils.compareLocation(event.getItem().getLocation().getBlock().getLocation(), event.getPlayer().getLocation().getBlock().getLocation())) {
			if (event.getItem().getItemStack().hasItemMeta() && event.getItem().getItemStack().getItemMeta().hasDisplayName()) {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt setstr "+event.getPlayer().getName()+" lastitem "+event.getItem().getItemStack().getItemMeta().getDisplayName().toLowerCase());
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "vt run wlevent:itempickup "+event.getPlayer().getName());
			}
			else {
				event.setCancelled(true);
			}

		} else {

			event.setCancelled(true);
		}
	}
	@EventHandler
	public void despawn(ItemDespawnEvent evt ) {
		if (evt.getEntity().getItemStack().hasItemMeta() && evt.getEntity().getItemStack().getItemMeta().hasDisplayName()) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void leave(PlayerQuitEvent evt) {
		if (plugin.getPlayer(evt.getPlayer())!=null) {
			plugin.getPlayer(evt.getPlayer()).stopGame();
			plugin.players.remove(plugin.getPlayer(evt.getPlayer()));  
		}

	}
	@EventHandler
	public void playerDamage(EntityDamageByEntityEvent evt) {
		if (evt.getCause() == DamageCause.ENTITY_ATTACK ) evt.setCancelled(true);
	}
	@EventHandler 
	public void playerMove(PlayerMoveEvent evt) {
		Vector vec = evt.getFrom().toVector().subtract(evt.getTo().toVector());
		if (!Utils.compareLocation(evt.getFrom().getBlock().getLocation(),evt.getTo().getBlock().getLocation())) {
			if (vec.getY()>0) {
			for (WonderlandPlayer wp : plugin.players) {
				if (wp.getPlayer().getLocation().getWorld() == evt.getTo().getWorld()) {
					if (wp.getPlayer().getUniqueId() == evt.getPlayer().getUniqueId()) continue;
					Location l = wp.getPlayer().getLocation();
					l = l.add(0, 0.5, 0);
					if (Utils.isClose(l,evt.getTo())) {
						if (evt.getTo().toVector().subtract(l.toVector()).distanceSquared(vec) < 1)
							wp.kill(evt.getPlayer());
					}
				}
			}
		}
	}
}




}