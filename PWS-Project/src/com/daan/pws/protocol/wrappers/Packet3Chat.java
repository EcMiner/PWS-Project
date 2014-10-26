package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.PacketPlayInChat;

public class Packet3Chat extends AbstractPacket {

	public Packet3Chat(PacketPlayInChat nmsPacket) {
		super(nmsPacket);
	}

	public String getMessage() {
		return (String) getField("message");
	}

	public void setMessage(String message) {
		setField("message", message);
	}

}
