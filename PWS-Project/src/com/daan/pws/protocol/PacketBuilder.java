package com.daan.pws.protocol;

import java.lang.reflect.Field;

import net.minecraft.server.v1_6_R3.Packet63WorldParticles;

public class PacketBuilder {

	public static Packet63WorldParticles buildPacketPlayOutWorldParticles(String particleName, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount) {
		Packet63WorldParticles packet = new Packet63WorldParticles();
		setField(packet, "a", particleName);
		setField(packet, "b", x);
		setField(packet, "c", y);
		setField(packet, "d", z);
		setField(packet, "e", xOffset);
		setField(packet, "f", yOffset);
		setField(packet, "g", zOffset);
		setField(packet, "h", speed);
		setField(packet, "i", amount);
		return packet;
	}

	private static final void setField(Object obj, String field, Object value) {
		try {
			Class<?> cl = obj.getClass();
			Field f = cl.getDeclaredField(field);
			f.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
