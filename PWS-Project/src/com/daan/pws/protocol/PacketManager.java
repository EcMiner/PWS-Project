package com.daan.pws.protocol;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R4.Packet;

public class PacketManager {

	private static final Map<String, PacketType> packetTypes = new HashMap<String, PacketType>();

	protected static void addPacketType(PacketType type) {
		packetTypes.put(type.getPacketClass().getCanonicalName(), type);
	}

	public static PacketType getPacketType(Class<? extends Packet> packetClass) {
		return packetTypes.get(packetClass.getCanonicalName());
	}

}
