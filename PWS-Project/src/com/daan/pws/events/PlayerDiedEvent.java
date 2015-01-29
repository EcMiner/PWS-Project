package com.daan.pws.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.match.enums.DeathType;

public class PlayerDiedEvent extends Event {

	public static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	private final CompetitivePlayer player;
	private final CompetitivePlayer killer;
	private final boolean headshot;
	private final DeathType type;

	public PlayerDiedEvent(CompetitivePlayer player, CompetitivePlayer killer, boolean headshot, DeathType type) {
		this.player = player;
		this.killer = killer;
		this.headshot = headshot;
		this.type = type;
	}

	public CompetitivePlayer getPlayer() {
		return player;
	}

	public CompetitivePlayer getKiller() {
		return killer;
	}

	public boolean isHeadshot() {
		return headshot;
	}

	public DeathType getType() {
		return type;
	}

}
