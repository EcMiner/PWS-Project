package com.daan.pws.weapon;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.entities.GrenadeEntity;

public abstract class Grenade extends Weapon {

	private final int maxAmount, price;
	private final GrenadeItem grenadeItem;
	private final GrenadeExplosionTrackerType trackingType;
	private int maxLivingTicks = -1;

	public Grenade(String name, String URL, float movementSpeed, int maxAmount, GrenadeExplosionTrackerType trackingType, int price) {
		super(name, URL, movementSpeed);
		this.price = price;
		this.maxAmount = maxAmount;
		this.grenadeItem = new GrenadeItem("Flashbang", URL, maxAmount > 1);
		this.trackingType = trackingType;
	}

	public GrenadeExplosionTrackerType getTrackingType() {
		return trackingType;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public GrenadeItem getGrenadeItem() {
		return grenadeItem;
	}

	public void setMaxLivingTicks(int maxLivingTicks) {
		this.maxLivingTicks = maxLivingTicks;
	}

	public int getMaxLivingTicks() {
		return maxLivingTicks;
	}

	public int getPrice() {
		return price;
	}

	public abstract void onExplode(SpoutPlayer player, GrenadeEntity grenade);

	public static enum GrenadeExplosionTrackerType {

		NO_VELOCITY, TIME, HIT;

	}

}
