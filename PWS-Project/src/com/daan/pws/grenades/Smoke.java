package com.daan.pws.grenades;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.weapon.Grenade;

public class Smoke extends Grenade {

	public Smoke(String name, String URL, float movementSpeed, int maxAmount, GrenadeExplosionTrackerType trackingType, int price) {
		super(name, URL, movementSpeed, maxAmount, trackingType, price);
	}

	@Override
	public void onExplode(SpoutPlayer player, GrenadeEntity grenade) {

	}

}
