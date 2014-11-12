package com.daan.pws.protocol.wrappers;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class Packet24SpawnEntityLiving extends AbstractPacket {

	public Packet24SpawnEntityLiving(Packet nmsPacket) {
		super(nmsPacket);
		PacketPlayOutSpawnEntityLiving ent;
	}

	public int getEntityId() {
		return getField("a");
	}

	public void setEntityId(int value) {
		setField("a", value);
	}

	public Entity getEntity() {
		int id = getEntityId();
		for (World w : Bukkit.getWorlds()) {
			Location l = new Location(w, getX(), getY(), getZ());
			for (Entity e : l.getChunk().getEntities()) {
				if (e.getEntityId() == id) {
					return e;
				}
			}
		}
		return null;
	}

	public int getEntityType() {
		return getField("b");
	}

	public void setEntityType(int value) {
		setField("b", value);
	}

	public DataWatcher getDataWatcher() {
		return getField("l");
	}

	public void setCustomName(String name) {
		getDataWatcher().watch(10, name);
		getDataWatcher().watch(2, name);
	}

	public String getCustomName() {
		return getDataWatcher().getString(10);
	}

	public double getX() {
		return (int) getField("c") / 32d;
	}

	public double getY() {
		return (int) getField("d") / 32d;
	}

	public double getZ() {
		return (int) getField("e") / 32d;
	}

}
