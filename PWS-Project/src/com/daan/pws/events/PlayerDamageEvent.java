package com.daan.pws.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.Weapon;

public class PlayerDamageEvent extends Event {

	public static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	private final CompetitivePlayer shooter;
	private final CompetitivePlayer damaged;
	private int damage;
	private final Weapon weapon;

	public PlayerDamageEvent(CompetitivePlayer shooter, CompetitivePlayer damaged, int damage, Weapon weapon) {
		this.shooter = shooter;
		this.damaged = damaged;
		this.damage = damage;
		this.weapon = weapon;
	}

	public CompetitivePlayer getShooter() {
		return shooter;
	}

	public CompetitivePlayer getDamaged() {
		return damaged;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
