package com.daan.pws.protocol.wrappers;

import java.lang.reflect.Field;

import net.minecraft.server.v1_6_R3.Packet;

public class AbstractPacket {

	protected final Packet nmsPacket;

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

	@SuppressWarnings("unchecked")
	protected final <T> T getField(String fieldName) {
		try {
			Field f = nmsPacket.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return (T) f.get(nmsPacket);
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

	@SuppressWarnings("unchecked")
	protected final <T> T getField(Object targetedClass, String fieldName) {
		try {
			Field f = targetedClass.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return (T) f.get(targetedClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Packet getNmsPacket() {
		return nmsPacket;
	}
}