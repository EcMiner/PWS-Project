package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.Packet;

public class Packet6SpawnPosition extends AbstractPacket {

	public Packet6SpawnPosition(Packet nmsPacket) {
		super(nmsPacket);
	}

	public int getX() {
		return getField("x");
	}

	public void setX(int value) {
		setField("x", value);
	}

	public int getY() {
		return getField("y");
	}

	public void setY(int value) {
		setField("y", value);
	}

	public int getZ() {
		return getField("z");
	}

	public void setZ(int value) {
		setField("z", value);
	}

}
