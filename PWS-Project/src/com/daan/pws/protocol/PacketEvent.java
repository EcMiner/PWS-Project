package com.daan.pws.protocol;

import net.minecraft.server.v1_7_R4.Packet;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

public class PacketEvent {

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
