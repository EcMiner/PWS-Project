package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.Packet;

public class PacketWindowClick extends AbstractPacket {

	public PacketWindowClick(Packet nmsPacket) {
		super(nmsPacket);
	}

	public short getTransactionID() {
		return getField("d");
	}

}
