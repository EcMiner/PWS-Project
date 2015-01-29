package com.daan.pws.weapon;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.entities.GrenadeEntity;

// Je kan hier zien dat de Grenade class abstract is, dit moet als je 
// abstracte methods will maken
public abstract class Grenade extends Weapon {

	private final int maxAmount, price;
	private final GrenadeItem grenadeItem;
	private final GrenadeExplosionTrackerType trackingType;
	private int maxLivingTicks = -1;

	// Informatie die moet worden gegeven door sub-classes.
	public Grenade(String name, String URL, float movementSpeed, int maxAmount, 
			GrenadeExplosionTrackerType trackingType, int price) {
		super(name, URL, movementSpeed);
		this.price = price;
		this.maxAmount = maxAmount;
		this.grenadeItem = new GrenadeItem(name, URL, maxAmount > 1);
		this.trackingType = trackingType;
	}
	
	// Dit is de abstract method, deze moet uitgebreid / aangepast worden door
	// De sub-class, anders krijg je een error.
	public abstract void onExplode(SpoutPlayer player, GrenadeEntity grenade);
	
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

	public static enum GrenadeExplosionTrackerType {

		// Verschillende type trackers voor wanneer een granaat ontploft
		// NO_VELOCITY -> Als de granaat stil ligt, dan ontploft de granaat.
		// TIME -> Na een bepaalde tijd ontploft de granaat.
		// HIT -> Als de granaat een muur of de grond raakt, dan ontploft hij.
		// HIT_GROUND -> Als de granaat de grond raakt, dan ontploft hij.
		NO_VELOCITY, TIME, HIT, HIT_GROUND;

	}

}
