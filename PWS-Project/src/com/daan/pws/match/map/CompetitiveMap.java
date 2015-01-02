package com.daan.pws.match.map;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class CompetitiveMap {

	private final String name;
	private final int id;
	private boolean inUse = false;

	private CuboidRegion counterTerroristsBuyZone, terroristsBuyZone, aBombSite, bBombSite;
	private List<Location> counterTerroristsSpawns = new ArrayList<Location>(), terroristsSpawns = new ArrayList<Location>();

	protected CompetitiveMap(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setCounterTerroristsBuyZone(CuboidRegion counterTerroristsBuyZone) {
		this.counterTerroristsBuyZone = counterTerroristsBuyZone;
	}

	public CuboidRegion getCounterTerroristsBuyZone() {
		return counterTerroristsBuyZone;
	}

	public void setTerroristsBuyZone(CuboidRegion terroristsBuyZone) {
		this.terroristsBuyZone = terroristsBuyZone;
	}

	public CuboidRegion getTerroristsBuyZone() {
		return terroristsBuyZone;
	}

	public CuboidRegion getABombSite() {
		return aBombSite;
	}

	public void setABombSite(CuboidRegion aBombSite) {
		this.aBombSite = aBombSite;
	}

	public CuboidRegion getBBombSite() {
		return bBombSite;
	}

	public void setBBombSite(CuboidRegion bBombSite) {
		this.bBombSite = bBombSite;
	}

	public void addCounterTerroristsSpawn(Location loc) {
		counterTerroristsSpawns.add(loc);
	}

	public void addTerroristsSpawn(Location loc) {
		terroristsSpawns.add(loc);
	}

	public List<Location> getCounterTerroristsSpawns() {
		return counterTerroristsSpawns;
	}

	public List<Location> getTerroristsSpawns() {
		return terroristsSpawns;
	}

	public boolean isValid() {
		System.out.println("ctSpawns: " + (counterTerroristsSpawns.size() >= 5));
		System.out.println("tSpawns: " + (terroristsSpawns.size() >= 5));
		System.out.println("tBuyZone: " + (terroristsBuyZone != null));
		System.out.println("ctBuyZone: " + (counterTerroristsBuyZone != null));
		System.out.println("aBombSite: " + (aBombSite != null));
		System.out.println("bBombSite: " + (bBombSite != null));
		System.out.println("inUse: " + (!inUse));
		return counterTerroristsSpawns.size() >= 5 && terroristsSpawns.size() >= 5 && terroristsBuyZone != null && counterTerroristsBuyZone != null && aBombSite != null && bBombSite != null && !inUse;
	}

}
