package com.daan.pws.weapon;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Gun {

	private String name, URL;
	private GunItem gunItem;
	private int totalAmmo, bulletsInRound, roundsPerMinute, price;
	private DamagePattern damagePattern;
	private boolean automatic;
	private WeaponType weaponType;
	private double reloadTime;

	public Gun(String name, String URL, int totalAmmo, int bulletsInRound, int roundsPerMinute, boolean automatic, int price, WeaponType weaponType, double reloadTime) {
		this.name = name;
		this.URL = URL;
		this.gunItem = new GunItem(name, URL);
		Validate.isTrue(totalAmmo % bulletsInRound == 0, "The total ammo must be dividable by the bulletsInRound");
		this.totalAmmo = totalAmmo;
		this.bulletsInRound = bulletsInRound;
		this.roundsPerMinute = roundsPerMinute;
		this.automatic = automatic;
		this.weaponType = weaponType;
		this.reloadTime = reloadTime;
	}

	public String getName() {
		return name;
	}

	public String getURL() {
		return URL;
	}

	public GunItem getGunItem() {
		return gunItem;
	}

	public int getTotalAmmo() {
		return totalAmmo;
	}

	public int getBulletsInRound() {
		return bulletsInRound;
	}

	public int getRoundsPerMinute() {
		return roundsPerMinute;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public int getPrice() {
		return price;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public double getReloadTime() {
		return reloadTime;
	}

	public DamagePattern getDamagePattern() {
		return damagePattern;
	}

	public void setDamagePattern(DamagePattern damagePattern) {
		this.damagePattern = damagePattern;
	}

	public final void shootBulllet(SpoutPlayer player) {
		Location loc = player.getEyeLocation().clone();
		Vector toAdd = loc.getDirection().multiply(.5);
		for (int i = 0; i < 100; i++) {
			loc.add(toAdd);
			if (!loc.getBlock().getType().isSolid()) {
				for (Entity e : loc.getChunk().getEntities()) {
					if (e instanceof LivingEntity) {
						LivingEntity ent = (LivingEntity) e;
						if (ent.getLocation().distanceSquared(loc) <= 2) {
							if (damagePattern != null) {
								// TODO Execute damage pattern.
							}
						}
					}
				}
			} else {
				break;
			}
		}
	}

}