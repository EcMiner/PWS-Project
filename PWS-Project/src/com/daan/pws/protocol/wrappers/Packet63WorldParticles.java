package com.daan.pws.protocol.wrappers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

public class Packet63WorldParticles extends AbstractPacket {

	public Packet63WorldParticles(PacketPlayOutWorldParticles nmsPacket) {
		super(nmsPacket);
	}

	public Particle getParticle() {
		return Particle.fromName((String) getField("a"));
	}

	public void setParticle(Particle particle) {
		setField("a", particle);
	}

	public int getX() {
		return getField("b");
	}

	public void setX(int value) {
		setField("b", value);
	}

	public int getY() {
		return getField("c");
	}

	public void setY(int value) {
		setField("c", value);
	}

	public int getZ() {
		return getField("d");
	}

	public void setZ(int value) {
		setField("d", value);
	}

	public int getOffsetX() {
		return getField("e");
	}

	public void setOffsetX(int value) {
		setField("e", value);
	}

	public int getOffsetY() {
		return getField("f");
	}

	public void setOffsetY(int value) {
		setField("f", value);
	}

	public int getOffsetZ() {
		return getField("g");
	}

	public void setOffsetZ(int value) {
		setField("g", value);
	}

	public int getSpeed() {
		return getField("h");
	}

	public void setSpeed(int value) {
		setField("h", value);
	}

	public int getAmount() {
		return getField("i");
	}

	public void setAmount(int value) {
		setField("i", value);
	}

	public static enum Particle {

		EXPLOSION_NORMAL, EXPLOSION_LARGE, EXPLOSION_HUGE, FIREWORKS_SPARK, WATER_BUBBLE, WATER_SPLASH, WATER_WAKE, SUSPENDED, SUSPENDED_DEPTH, CRIT, CRIT_MAGIC, SMOKE_NORMAL, SMOKE_LARGE, SPELL, SPELL_INSTANT, SPELL_MOB, SPELL_MOB_AMBIENT, SPELL_WITCH, DRIP_WATER, DRIP_LAVA, VILLAGER_ANGRY, VILLAGER_HAPPY, TOWN_AURA, NOTE, PORTAL, ENCHANTMENT_TABLE, FLAME, LAVA, FOOTSTEP, CLOUD, REDSTONE, SNOWBALL, SNOW_SHOVEL, SLIME, HEART, BARRIER, ICON_CRACK, BLOCK_CRACK, BLOCK_DUST, WATER_DROP, ITEM_TAKE, MOB_APPEARANCE;

		private String name;

		private Particle() {
			this.name = name().toLowerCase();
		}

		public String getName() {
			return name;
		}

		private static final Map<String, Particle> allParticles = new HashMap<String, Particle>();

		static {
			for (Particle particle : values()) {
				allParticles.put(particle.name, particle);
			}
		}

		public static Particle fromName(String name) {
			return allParticles.get(name.toLowerCase());
		}

	}

}
