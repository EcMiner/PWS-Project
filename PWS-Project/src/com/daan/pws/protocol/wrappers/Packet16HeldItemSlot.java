package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_6_R3.Packet;

public class Packet16HeldItemSlot extends AbstractPacket {

	public Packet16HeldItemSlot(Packet nmsPacket) {
		super(nmsPacket);
	}

	public int getSlot() {
		return getField("itemInHandIndex");
	}

	public void setSlot(int value) {
		setField("itemInHandIndex", value);
	}

}
