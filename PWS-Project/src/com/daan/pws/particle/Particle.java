package com.daan.pws.particle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import com.daan.pws.protocol.PacketBuilder;

public enum Particle {

	HUGE_EXPLOSION("hugeexplosion"), LARGE_EXPLODE("largeexplode"), FIREWORKS_SPARK("fireworksSpark"), BUBBLE("bubble"), SUSPEND("suspend"), DEPTH_SUSPEND("depthSuspend"), TOWN_AURA("townaura"), CRIT("crit"), MAGIC_CRIT("magicCrit"), SMOKE("smoke"), MOB_SPELL("mobSpell"), MOB_SPELL_AMBIENT("mobSpellAmbient"), SPELL("spell"), INSTANT_SPELL(
			"instantSpell"), WITCH_MAGIC("witchMagic"), NOTE("note"), PORTAL("portal"), ENCHANTMENT_TABLE("enchantmenttable"), EXPLODE("explode"), FLAME("flame"), LAVA("lava"), FOOTSTEP("footstep"), SPLASH("splash"), WAKE("wake"), LARGE_SMOKE("largesmoke"), CLOUD("cloud"), RED_DUST("reddust"), SNOWBALL_POOF("snowballpoof"), DRIP_WATER("dripWater"), DRIP_LAVA(
			"dripLava"), SNOW_SHOVEL("snowshovel"), SLIME("slime"), HEART("heart"), ANGRY_VILLAGER("angryVillager"), HAPPY_VILLAGER("happyVillager");

	private String name;

	private Particle(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	private static final Map<String, Particle> LOOK_UP = new HashMap<String, Particle>();

	static {
		for (Particle particle : values()) {
			LOOK_UP.put(particle.getName().toLowerCase(), particle);
		}
	}

	public static Particle fromName(String name) {
		return LOOK_UP.get(name.toLowerCase());
	}

	private static final int maxDistance = Bukkit.getViewDistance() * 32;

	
	
	@SuppressWarnings("deprecation")
	public void display(float x, float y, float z, float speed) {
		List<Player> players = new ArrayList<Player>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (distanceSquared(x, y, z, p.getLocation()) <= maxDistance * maxDistance) {
				players.add(p);
			}
		}
		display(x, y, z, speed, players);
	}

	public void display(float x, float y, float z, float speed, Player... players) {
		display(x, y, z, speed, 1, players);
	}

	public void display(float x, float y, float z, float speed, List<Player> players) {
		display(x, y, z, speed, 1, players);
	}

	public void display(float x, float y, float z, float speed, int amount, Player... players) {
		display(x, y, z, 0, 0, 0, speed, amount, players);
	}

	public void display(float x, float y, float z, float speed, int amount, List<Player> players) {
		display(x, y, z, 0, 0, 0, speed, amount, players);
	}

	public void display(float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount, Player... players) {
		display(x, y, z, xOffset, yOffset, zOffset, speed, amount, Arrays.asList(players));
	}

	public void display(float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int amount, List<Player> players) {
		for (Player p : players) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(PacketBuilder.buildPacketPlayOutWorldParticles(getName(), x, y, z, xOffset, yOffset, zOffset, speed, amount));
		}
	}

	private double distanceSquared(float x, float y, float z, Location loc) {
		return (NumberConversions.square(x - loc.getX()) + NumberConversions.square(y - loc.getY()) + NumberConversions.square(z - loc.getZ()));
	}

}
