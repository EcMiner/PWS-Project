package com.daan.pws.protocol.wrappers;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.Packet;

public class AbstractPacket {

	private Packet nmsPacket;

	public AbstractPacket(Packet nmsPacket) {
		this.nmsPacket = nmsPacket;
	}

	protected final void setField(String fieldName, Object value) {
		try {
			Field f = nmsPacket.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(nmsPacket, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final Object getField(String fieldName) {
		try {
			Field f = nmsPacket.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(nmsPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected final void setField(Object targetedClass, String fieldName, Object value) {
		try {
			Field f = targetedClass.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(targetedClass, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final Object getField(Object targetedClass, String fieldName) {
		try {
			Field f = targetedClass.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(targetedClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Packet getNmsPacket() {
		return nmsPacket;
	}

}
