package com.daan.pws.npc;

import java.lang.reflect.Field;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.PacketPlayOutAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutRelEntityMoveLook;
import net.minecraft.util.com.mojang.authlib.GameProfile;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PacketGenerator {

	@SuppressWarnings("deprecation")
	public static PacketPlayOutNamedEntitySpawn getSpawnPacket(int entityId, String name, Location location, int itemInHand) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (byte) 0);
		d.a(1, (short) 0);
		d.a(8, (byte) 0);
		PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn();
		setField(spawn, "a", entityId);
		GameProfile profile = new GameProfile(Bukkit.getOfflinePlayer(name).getUniqueId(), name);
		setField(spawn, "b", profile);
		setField(spawn, "c", (int) location.getX() * 32);
		setField(spawn, "d", (int) location.getY() * 32);
		setField(spawn, "e", (int) location.getZ() * 32);
		setField(spawn, "f", (byte) ((location.getYaw() * 256f) / 360f));
		setField(spawn, "g", (byte) ((location.getPitch() * 256f) / 360f));
		setField(spawn, "h", itemInHand);
		setField(spawn, "i", d);
		return spawn;
	}

	public static PacketPlayOutRelEntityMoveLook getMoveAndLookPacket(int entityId, Location location) {
		return getMoveAndLookPacket(entityId, location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
	}

	public static PacketPlayOutRelEntityMoveLook getMoveAndLookPacket(int entityId, double x, double y, double z, float pitch, float yaw) {
		PacketPlayOutRelEntityMoveLook packet = new PacketPlayOutRelEntityMoveLook();
		setField(packet, "a", entityId);
		setField(packet, "b", (byte) x);
		setField(packet, "c", (byte) y);
		setField(packet, "d", (byte) z);
		setField(packet, "e", getCompressedAngle(yaw));
		setField(packet, "f", getCompressedAngle(pitch));
		return packet;
	}

	public static PacketPlayOutEntityHeadRotation getHeadRotationPacket(int entityId, float yaw) {
		PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
		setField(packet, "a", entityId);
		setField(packet, "b", getCompressedAngle(yaw));
		return packet;
	}

	public static PacketPlayOutEntityMetadata getInvisiblePacket(int entityId, boolean invisible) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (byte) (invisible ? 32 : 0));
		d.a(1, (byte) 0);
		d.a(8, (byte) 0);
		return new PacketPlayOutEntityMetadata(entityId, d, true);
	}

	public static PacketPlayOutEntityMetadata getCrouchPacket(int entityId, boolean crouching) {
		DataWatcher d = new DataWatcher(null);
		d.a(0, (byte) (crouching ? 2 : 0));
		d.a(1, (short) 0);
		d.a(8, (byte) 0);
		return new PacketPlayOutEntityMetadata(entityId, d, true);
	}

	public static PacketPlayOutAnimation getDamagePacket(int entityId) {
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		setField(packet, "a", entityId);
		setField(packet, "b", 5);
		return packet;
	}

	public static PacketPlayOutEntityDestroy getDestroyPacket(int entityId) {
		return new PacketPlayOutEntityDestroy(entityId);
	}

	private static byte getCompressedAngle(float value) {
		return (byte) ((value * 256.0F) / 360.0F);
	}

	private static final void setField(Object targetedClass, String fieldName, Object value) {
		try {
			Field f = targetedClass.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(targetedClass, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
