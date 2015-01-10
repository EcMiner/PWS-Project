package com.daan.pws.weapon;

public abstract class Weapon {

	private final String name, URL;
	private final float movementSpeed;

	public Weapon(String name, String URL, float movementSpeed) {
		this.name = name;
		this.URL = URL;
		this.movementSpeed = movementSpeed;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public String getName() {
		return name;
	}

	public String getURL() {
		return URL;
	}

}
