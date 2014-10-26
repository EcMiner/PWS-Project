package com.daan.pws.protocol;

import net.minecraft.server.v1_7_R4.Packet;

public class PacketEvent {

	private Packet packet;
	private PacketType packetType;
	private boolean cancelled;

	public PacketEvent(Packet packet, PacketType packetType) {
		this.packet = packet;
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

}
