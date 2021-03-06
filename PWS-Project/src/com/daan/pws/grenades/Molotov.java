package com.daan.pws.grenades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.weapon.Grenade;

public class Molotov extends Grenade {

	public Molotov() {
		super("Molotov", "http://panisme.nl/csgo/textures/molotov.png", 245, 1, GrenadeExplosionTrackerType.HIT_GROUND, 400);
		setMaxLivingTicks(60);
	}

	@Override
	public void onExplode(SpoutPlayer player, GrenadeEntity grenade) {
		final Item e = grenade.getGrenadeEntity();

		int radius = 4;

		final List<Location> locs = new ArrayList<Location>();
		final Location center = e.getLocation();

		// Calculatie voor een cilinder.
		for (int y = -2; y <= 2; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if ((x * x) + (z * z) <= radius * radius) {
						// We checken hier of elk blokje in de cilinder lucht is, als dit zo is voegen we hem toe aan de locs List, als dat niet zo is, gaan we verder
						// Met de loop zonder de locatie toe te voegen aan de list.
						Location l = new Location(e.getWorld(), e.getLocation().getBlockX() + x, e.getLocation().getBlockY() + y, e.getLocation().getBlockZ() + z);
						if (l.getBlock().getType() == Material.AIR) {
							locs.add(l);
						}
					}
				}
			}
		}

		// Hiermee sorteren we de locs List op hoever elk blokje van het midden van de cilinder af is, hoe dichterbij hij bij het midden is, eerder hij in de list voorkomt.
		Comparator<Location> comparator = new Comparator<Location>() {

			@Override
			public int compare(Location o1, Location o2) {
				int result = Double.compare(o1.distanceSquared(center), o2.distanceSquared(center));
				return result == 1 ? -1 : (result == -1 ? 1 : 0);
			}
		};

		Collections.sort(locs, comparator);
		if (locs.size() > 0) {
			new BukkitRunnable() {

				int index = locs.size() - 1;

				@Override
				public void run() {
					// Hier zetten ik alle blokjes naar vuur, ik doe dit in een timer omdat ik niet wil dat alle blokjes meteen naar vuur worden verandert omdat dat
					// Er raar uitziet, daarom doe ik steeds vijf blokjes per 1 tick (50ms).
					for (int i = 0; i < 5; i++) {
						if (index >= 0) {
							if (!Smoke.smokeLocations.contains(locs.get(index))) {
								locs.get(index).getBlock().setType(Material.FIRE);
							}
						} else {
							new FireFadeEffect(e.getLocation(), locs);
							cancel();
						}
						--index;
					}
				}

			}.runTaskTimer(Main.getInstance(), 0, 1);
		}
	}

	// De FireFadeEffect class zorgt ervoor dat het vuur gedooft wordt, maar 1 blokje per 1 tick (50ms)
	public static class FireFadeEffect {
		public FireFadeEffect(final Location center, final List<Location> blocks) {
			// Hiermee sorteren we weer de locs List op hoever elk blokje van het midden van de cilinder af is, maar deze keer is het: hoe verder af hij bij het midden is, hoe eerder hij in de list voorkomt.
			Comparator<Location> comparator = new Comparator<Location>() {

				@Override
				public int compare(Location o1, Location o2) {
					return Double.compare(o1.distanceSquared(center), o2.distanceSquared(center));
				}
			};

			Collections.sort(blocks, comparator);

			new BukkitRunnable() {

				int index = blocks.size() - 1;

				@Override
				public void run() {
					if (index >= 0) {
						blocks.get(index).getBlock().setType(Material.AIR);
						index--;
					} else
						cancel();
				}

			}.runTaskTimer(Main.getInstance(), 140, 1);
		}

	}

}
