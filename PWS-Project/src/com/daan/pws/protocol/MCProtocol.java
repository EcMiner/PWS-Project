package com.daan.pws.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_7_R4.Packet;

public class MCProtocol {

	private static final Map<PacketType, List<PacketListener>> packetListeners = new HashMap<PacketType, List<PacketListener>>();

	public MCProtocol() {
	}

	public final void handlePacket(Packet packet, PacketEvent event) {
		PacketType type = PacketType.getTypeFromPacketClass(packet.getClass());
		if (packetListeners.containsKey(type)) {
			for (PacketListener listener : packetListeners.get(type)) {
				listener.onPacketEvent(event);
			}
		}
	}

	public final void addPacketListener(PacketType packetType, PacketListener listener) {
		if (!packetListeners.containsKey(packetType)) {
			packetListeners.put(packetType, new ArrayList<PacketListener>());
		}
		packetListeners.get(packetType).add(listener);
	}

}
