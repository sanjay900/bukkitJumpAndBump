package com.sanjay900.jnb.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class RandomSpawn {

	// *------------------------------------------------------------------------------------------------------------*
	// | The following chooseSpawn method contains code made by NuclearW                                            |
	// | based on his SpawnArea plugin:                                                                             |
	// | http://forums.bukkit.org/threads/tp-spawnarea-v0-1-spawns-targetPlayers-in-a-set-area-randomly-1060.20408/ |
	// *------------------------------------------------------------------------------------------------------------*

	public Location chooseSpawn(World world){
		// I don't like this method
		List<Integer> blacklist = new ArrayList<Integer>();


		blacklist = Arrays.asList(new Integer[]{8,9,10,11,18,51,81});			
		Location min = world.getSpawnLocation().add(-10, 0, -10);	
		Location max = world.getSpawnLocation().add(10, 0, 10);
		double xmin = min.getX();
		double xmax = max.getX();
		double zmin = min.getZ();
		double zmax = max.getZ();


		double xrand = 0;
		double zrand = 0;
		double y = -1;


		double xcenter = xmin + (xmax - xmin)/2;
		double zcenter = zmin + (zmax - zmin)/2;

		do {

			double r = Math.random() * (xmax - xcenter);
			double phi = Math.random() * 2 * Math.PI;

			xrand = xcenter + Math.cos(phi) * r;
			zrand = zcenter + Math.sin(phi) * r;

			y = getValidHighestY(world, xrand, zrand, blacklist);

		} while (y == -1);


		Location location = new Location(world, xrand, y, zrand);

		return location;
	}

	private double getValidHighestY(World world, double x, double z, List<Integer> blacklist) {

		world.getChunkAt(new Location(world, x, 0, z)).load();

		double y = 0;
		int blockid = 0;

		if(world.getEnvironment().equals(Environment.NETHER)){
			int blockYid = world.getBlockTypeIdAt((int) x, (int) y, (int) z);
			int blockY2id = world.getBlockTypeIdAt((int) x, (int) (y+1), (int) z);
			while(y < 128 && !(blockYid == 0 && blockY2id == 0)){				
				y++;
				blockYid = blockY2id;
				blockY2id = world.getBlockTypeIdAt((int) x, (int) (y+1), (int) z);
			}
			if(y == 127) return -1;
		}else{
			y = 257;
			while(y >= 0 && blockid == 0){
				y--;
				blockid = world.getBlockTypeIdAt((int) x, (int) y, (int) z);
			}
			if(y == 0) return -1;
		}

		if (blacklist.contains(blockid)) return -1;
		if (blacklist.contains(81) && world.getBlockTypeIdAt((int) x, (int) (y+1), (int) z) == 81) return -1; // Check for cacti

		return y;
	}
}