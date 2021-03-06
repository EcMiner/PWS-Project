package com.daan.pws.grenades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.grenades.Molotov.FireFadeEffect;
import com.daan.pws.weapon.Grenade;

public class Incendiary extends Grenade {

	// De Incendiary granaat is eigenlijk hetzelfde als een Molotov, maar een Molotov kan allen gekocht worden door Terrorists, en de Incendiary alleen
	// Door Counter-Terrorists. Verder is de Incendiary 200$ duurder, de Counter-Terrorists betalen dus 200$ meer als ze een vuur granaat willen kopen.
	public Incendiary() {
		super("Incendiary Grenade", "http://panisme.nl/csgo/textures/incendiary.png", 245, 1, GrenadeExplosionTrackerType.HIT_GROUND, 600);
		setMaxLivingTicks(60);
	}

	@Override
	public void onExplode(SpoutPlayer player, GrenadeEntity grenade) {
		final Item e = grenade.getGrenadeEntity();

		int radius = 4;

		final List<Location> locs = new ArrayList<Location>();
		final Location center = e.getLocation();
		for (int y = -2; y <= 2; y++) {
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					if ((x * x) + (z * z) <= radius * radius) {
						Location l = new Location(e.getWorld(), e.getLocation().getBlockX() + x, e.getLocation().getBlockY() + y, e.getLocation().getBlockZ() + z);
						if (l.getBlock().getType() == Material.AIR && l.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
							locs.add(l);
						}
					}
				}
			}
		}

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

}
