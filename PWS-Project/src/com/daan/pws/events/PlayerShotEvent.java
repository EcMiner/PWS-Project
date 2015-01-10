package com.daan.pws.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;
import com.daan.pws.weapon.Gun;

public class PlayerShotEvent extends Event implements Cancellable {

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
	private final Gun gun;
	private final PlayerHeight height;
	private boolean cancelled = false;

	public PlayerShotEvent(CompetitivePlayer shooter, CompetitivePlayer damaged, int damage, PlayerHeight height, Gun weapon) {
		this.shooter = shooter;
		this.damaged = damaged;
		this.damage = damage;
		this.gun = weapon;
		this.height = height;
	}

	public CompetitivePlayer getShooter() {
		return shooter;
	}

	public CompetitivePlayer getDamaged() {
		return damaged;
	}

	public Gun getGun() {
		return gun;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public PlayerHeight getHeight() {
		return height;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}
