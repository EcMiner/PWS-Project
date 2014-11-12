package com.daan.pws.npc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R4.Packet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NPC {

	private static Map<Integer, NPC> npcs = new HashMap<Integer, NPC>();

	public static NPC getNpc(int entityId) {
		return npcs.get(entityId);
	}

	public static boolean isNpc(int entityId) {
		return npcs.containsKey(entityId);
	}

	public static Collection<NPC> getNpcs() {
		return npcs.values();
	}

	private String name;
	private Location location;
	private int entityId;
	private Material itemInHand;
	private boolean invisible = false;
	private boolean crouching = false;
	private double maxHealth = 20d;
	private double health = 20d;

	public NPC(String name, Location location, Material itemInHand) {
		this.name = name;
		this.location = location;
		this.entityId = Integer.MAX_VALUE - npcs.size();
		this.itemInHand = itemInHand;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public int getEntityId() {
		return entityId;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public void toggleInvisibility() {
		setInvisible(invisible ? false : true);
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
		sendPacketToAll(PacketGenerator.getInvisiblePacket(entityId, invisible));
	}

	public void toggleCrouching() {
		setCrouching(crouching ? false : true);
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
		sendPacketToAll(PacketGenerator.getCrouchPacket(entityId, crouching));
	}

	public void walkToLocation(Location location, float pitch, float yaw) {
		this.location = location;
		this.location.setPitch(pitch);
		this.location.setYaw(yaw);
		sendPacketToAll(PacketGenerator.getMoveAndLookPacket(entityId, this.location));
		sendPacketToAll(PacketGenerator.getHeadRotationPacket(entityId, yaw));
	}

	public void walkToLocation(double addX, double addY, double addZ, float pitch, float yaw) {
		this.location.add(addX, addY, addZ);
		this.location.setPitch(pitch);
		this.location.setYaw(yaw);
		sendPacketToAll(PacketGenerator.getMoveAndLookPacket(entityId, addX, addY, addZ, pitch, yaw));
		sendPacketToAll(PacketGenerator.getHeadRotationPacket(entityId, yaw));
	}

	@SuppressWarnings("deprecation")
	public void spawn() {
		sendPacketToAll(PacketGenerator.getSpawnPacket(entityId, name, location, itemInHand.getId()));
	}

	public void destroy() {
		sendPacketToAll(PacketGenerator.getDestroyPacket(entityId));
	}

	private void sendPacketToAll(Packet packet) {
		for (Player p : location.getWorld().getPlayers()) {
			sendPacket(p, packet);
		}
	}

	private void sendPacket(Player p, Packet packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
