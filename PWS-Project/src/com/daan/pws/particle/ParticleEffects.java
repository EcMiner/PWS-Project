package com.daan.pws.particle;

import net.minecraft.server.v1_6_R3.Packet63WorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public enum ParticleEffects {

	HUGE_EXPLOSION("hugeexplosion"), LARGE_EXPLODE("largeexplode"), FIREWORKS_SPARK("fireworksSpark"), BUBBLE("bubble"), SUSPEND("suspend"), DEPTH_SUSPEND("depthSuspend"), TOWN_AURA("townaura"), CRIT("crit"), MAGIC_CRIT("magicCrit"), MOB_SPELL("mobSpell"), MOB_SPELL_AMBIENT("mobSpellAmbient"), SPELL("spell"), INSTANT_SPELL("instantSpell"), WITCH_MAGIC(
			"witchMagic"), NOTE("note"), PORTAL("portal"), ENCHANTMENT_TABLE("enchantmenttable"), EXPLODE("explode"), FLAME("flame"), LAVA("lava"), FOOTSTEP("footstep"), SPLASH("splash"), LARGE_SMOKE("largesmoke"), CLOUD("cloud"), RED_DUST("reddust"), SNOWBALL_POOF("snowballpoof"), DRIP_WATER("dripWater"), DRIP_LAVA("dripLava"), SNOW_SHOVEL(
			"snowshovel"), SLIME("slime"), HEART("heart"), ANGRY_VILLAGER("angryVillager"), HAPPY_VILLAGER("happerVillager"), ICONCRACK("iconcrack_"), TILECRACK("tilecrack_");

	private String particleName;

	ParticleEffects(String particleName) {
		this.particleName = particleName;
	}

	public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
		try {
			Packet63WorldParticles packet = new Packet63WorldParticles();
			ReflectionUtilities.setValue(packet, "a", particleName);
			ReflectionUtilities.setValue(packet, "b", (float) location.getX());
			ReflectionUtilities.setValue(packet, "c", (float) location.getY());
			ReflectionUtilities.setValue(packet, "d", (float) location.getZ());
			ReflectionUtilities.setValue(packet, "e", offsetX);
			ReflectionUtilities.setValue(packet, "f", offsetY);
			ReflectionUtilities.setValue(packet, "g", offsetZ);
			ReflectionUtilities.setValue(packet, "h", speed);
			ReflectionUtilities.setValue(packet, "i", count);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendToPlayers(Player[] players, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {
		try {
			Packet63WorldParticles packet = new Packet63WorldParticles();
			ReflectionUtilities.setValue(packet, "a", particleName);
			ReflectionUtilities.setValue(packet, "b", (float) location.getX());
			ReflectionUtilities.setValue(packet, "c", (float) location.getY());
			ReflectionUtilities.setValue(packet, "d", (float) location.getZ());
			ReflectionUtilities.setValue(packet, "e", offsetX);
			ReflectionUtilities.setValue(packet, "f", offsetY);
			ReflectionUtilities.setValue(packet, "g", offsetZ);
			ReflectionUtilities.setValue(packet, "h", speed);
			ReflectionUtilities.setValue(packet, "i", count);
			for (Player player : players) {
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}