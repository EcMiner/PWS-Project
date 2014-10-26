package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;

public class Packet20NamedEntitySpawn extends AbstractPacket {

	public Packet20NamedEntitySpawn(PacketPlayOutNamedEntitySpawn nmsPacket) {
		super(nmsPacket);
	}

	public String getProfileName() {
		return (String) getField(getField("b"), "name");
	}

	public void setProfileName(String name) {
		setField(getField("b"), "name", name);
	}

}
