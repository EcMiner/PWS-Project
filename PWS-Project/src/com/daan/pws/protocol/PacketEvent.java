package com.daan.pws.protocol;

import net.minecraft.server.v1_7_R4.Packet;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	private Packet packet;
	private PacketType packetType;
	private Player player;
	private boolean cancelled;

	public PacketEvent(Packet packet, Player player, PacketType packetType) {
		this.packet = packet;
		this.player = player;
		this.packetType = packetType;
	}

	public Packet getPacket() {
		return packet;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public Player getPlayer() {
		Validate.notNull(player, "A player is not valid for this packet");
		return player;
	}

}
