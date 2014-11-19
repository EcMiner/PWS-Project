package com.daan.pws.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daan.pws.Main;

import net.minecraft.server.v1_7_R4.Packet;

public class MCProtocol {

	private static final Map<String, List<PacketListener>> packetListeners = new HashMap<String, List<PacketListener>>();

	public MCProtocol() {
	}

	public final void handlePacket(Packet packet, PacketEvent event) {
		PacketType type = PacketManager.getPacketType(packet.getClass());

		Main.getInstance().getServer().getPluginManager().callEvent(event);

		if (packetListeners.containsKey(type.getPacketName())) {
			for (PacketListener listener : packetListeners.get(type.getPacketName())) {
				listener.onPacketEvent(event);
			}
		}
	}

	public final void addPacketListener(PacketType packetType, PacketListener listener) {
		if (!packetListeners.containsKey(packetType.getPacketName())) {
			packetListeners.put(packetType.getPacketName(), new ArrayList<PacketListener>());
		}
		packetListeners.get(packetType.getPacketName()).add(listener);
	}

}
