package com.daan.pws.grenades;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.particle.ParticleEffects;
import com.daan.pws.weapon.Grenade;

public class Smoke extends Grenade {

	public Smoke() {
		super("Smoke Grenade", "http://panisme.nl/csgo/textures/smoke.png", 245, 1, GrenadeExplosionTrackerType.NO_VELOCITY, 300);
	}

	public static List<Location> smokeLocations = new ArrayList<Location>();

	@Override
	public void onExplode(SpoutPlayer player, GrenadeEntity grenade) {
		Item e = grenade.getGrenadeEntity();
		final Location center = e.getLocation();
		final List<Location> locs = new ArrayList<Location>();
		int radius = 4;
		// Een for loop die een cilinder met een hoogte van 8 maakt.
		for (int y = -radius; y <= radius; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z < radius; z++) {
					if ((x * x) + (z * z) <= radius * radius) {
						Location loc = new Location(e.getWorld(), e.getLocation().getBlockX() + x, e.getLocation().getBlockY() + y, e.getLocation().getBlockZ() + z);
						if (loc.getBlock().getType() == Material.AIR || loc.getBlock().getType() == Material.FIRE) {
							if (loc.getBlock().getType() == Material.FIRE) {
								loc.getBlock().setType(Material.AIR);
							}
							// Voeg elk punt in de cilinder toe aan een list die we later gebruiken.
							locs.add(loc);
							smokeLocations.add(loc);
						}
					}
				}
			}
		}
		// Dit is een timer die ervoor gaat zorgen dat het rook effect ook echt ontstaat.
		// We maken dit effect om voor elke locatie in de cilinder (die we net in een list hebben opgeslagen)
		// een CLOUD particle (rook deeltje) naar alle spelers te sturen
		new BukkitRunnable() {

			public List<String> farAway = new ArrayList<String>();

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if ((player.getLocation().distanceSquared(center) > 320)) {
						// Jammer genoeg zien spelers de rook deeltjes niet meer als ze
						// ongever 20 blokjes van de rook deeltjes vandaan zijn
						// Ik heb hier gemaakt dat er neppe blokken op het scherm van spelers
						// komen als de spelers te ver zijn om de rook deeltjes te kunnen zien.
						if (!farAway.contains(player.getName())) {
							for (Location loc : locs) {
								player.sendBlockChange(loc, Material.WOOL, (byte) 0);
							}
							farAway.add(player.getName());
						}
					} else {
						// Als ze de de rook deeltjes weer kunnen zien, verwijder ik de neppe blokjes.
						if (farAway.add(player.getName())) {
							for (Location loc : locs) {
								player.sendBlockChange(loc, 0, Byte.parseByte("0"));
							}
							farAway.remove(player.getName());
						}
					}
				}
				for (Location loc : locs) {
					// Hier sturen we de rook deeltjes naar alle spelers.
					ParticleEffects.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), loc, 0.5f, 0.5f, 0.5f, 0f, 5);
				}
			}

		}.runTaskTimer(Main.getInstance(), 0, 1);
	}
}
