package com.daan.pws.match.map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class CuboidRegion {

	private final Location min;
	private final Location max;

	public CuboidRegion(Location loc1, Location loc2) {
		if (loc1.getWorld().getName() != loc2.getWorld().getName()) {
			throw new IllegalArgumentException("The two locations are not in the same world!");
		}
		min = new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
		max = new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
	}

	public Location getMax() {
		return max;
	}

	public Location getMin() {
		return min;
	}

	public boolean isInRegion(Entity e) {
		return isInRegion(e.getLocation());
	}

	public boolean isInRegion(Location loc) {
		return (loc.getX() >= min.getX() && loc.getX() <= max.getX()) && (loc.getY() >= min.getY() && loc.getY() <= max.getY()) && (loc.getZ() >= min.getZ() && loc.getZ() <= max.getZ());
	}

}
