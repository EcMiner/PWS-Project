package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.Packet;

public class Packet8UpdateHealth extends AbstractPacket {

	public Packet8UpdateHealth(Packet nmsPacket) {
		super(nmsPacket);
	}

	public float getHealth() {
		return getField("a");
	}

	public void setHealth(float value) {
		setField("a", value);
	}

	public int getFood() {
		return getField("b");
	}

	public void setFood(int value) {
		setField("b", value);
	}

	public float getFoodSaturation() {
		return getField("c");
	}

	public void setFoodSaturation(float value) {
		setField("c", value);
	}

}
