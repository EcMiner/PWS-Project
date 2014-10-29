package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.Packet;

public class Packet4UpdateTime extends AbstractPacket {

	public Packet4UpdateTime(Packet nmsPacket) {
		super(nmsPacket);
	}

	public long getWorldAge() {
		return getField("a");
	}

	public void setWorldAge(long value) {
		setField("a", value);
	}

	public long getTimeOfDay() {
		return getField("b");
	}

	public void setTimeOfDay(long value) {
		setField("b", value);
	}

}
