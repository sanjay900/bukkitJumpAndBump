package com.sanjay900.jnb.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Utils
{
	public static List<Block> getNearbyLiquids(Location location, int Radius) {
		List<Block> Blocks = new ArrayList<Block>();
		List<Block> nearby = getNearbyBlocks(location,Radius);
		for (Block b : nearby) {
			if (b.isLiquid()) {
				Blocks.add(b);
			}
		}

		return Blocks;
	}
	public static List<Block> getNearbyBlocks(Location location, int Radius) {
		List<Block> Blocks = new ArrayList<Block>();

		for (int X = location.getBlockX() - Radius; X <= location.getBlockX()
				+ Radius; X++) {
			for (int Y = location.getBlockY() - Radius; Y <= location
					.getBlockY() + Radius; Y++) {
				for (int Z = location.getBlockZ() - Radius; Z <= location
						.getBlockZ() + Radius; Z++) {
					Block block = location.getWorld().getBlockAt(X, Y, Z);
					if (!block.isEmpty()) {
						Blocks.add(block);
					}
				}
			}
		}

		return Blocks;
	}
	public static boolean compareLocation(Location l, Location l2) {
		return (l.getX() == l2.getX())
				&& (l.getY() == l2.getY())
				&& (l.getZ() == l2.getZ());

	}

	
	public static boolean isClose(Location from, Location to) {
		return from.distanceSquared(to) < 0.6;
	}
}

