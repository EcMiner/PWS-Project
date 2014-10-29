package com.daan.pws.protocol.wrappers;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Packet;

public class Packet5EntityEquipment extends AbstractPacket {

	public Packet5EntityEquipment(Packet nmsPacket) {
		super(nmsPacket);
	}

	public int getEntityId() {
		return getField("a");
	}

	public void setEntityId(int value) {
		setField("a", value);
	}

	public int getSlot() {
		return getField("b");
	}

	public void setSlot(int value) {
		setField("b", value);
	}

	public org.bukkit.inventory.ItemStack getItem() {
		return CraftItemStack.asBukkitCopy((ItemStack) getField("c"));
	}

	public void setItem(org.bukkit.inventory.ItemStack value) {
		setField("c", CraftItemStack.asNMSCopy(value));
	}

}
