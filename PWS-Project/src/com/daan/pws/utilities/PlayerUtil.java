package com.daan.pws.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.daan.pws.Main;

public class PlayerUtil implements Listener {

	private static final Map<String, Vector> velocities = new HashMap<String, Vector>();
	private static final List<String> frozen = new ArrayList<String>();

	public PlayerUtil() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), ConnectionSide.CLIENT_SIDE, 11) {

			@Override
			public void onPacketReceiving(PacketEvent event) {
				StructureModifier<Double> doubles = event.getPacket().getDoubles();
				double x = doubles.read(0);
				double y = doubles.read(1);
				double z = doubles.read(2);
				Vector velocity = new Vector(x, y, z).subtract(event.getPlayer().getLocation().toVector());
				velocities.put(event.getPlayer().getName(), velocity);
			}

		});
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		velocities.remove(e.getPlayer().getName());
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (frozen.contains(p.getName())) {
			if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
				p.teleport(new Location(p.getWorld(), e.getFrom().getBlockX() + .5, e.getFrom().getY(), e.getFrom().getBlockZ() + .5, p.getEyeLocation().getYaw(), p.getEyeLocation().getPitch()));
			}
		}
	}

	public static final Vector getVelocity(Player player) {
		if (!player.isOnline() || !velocities.containsKey(player.getName())) {
			return new Vector(0, 0, 0);
		}
		return velocities.get(player.getName());
	}

	public static void freeze(Player player) {
		frozen.add(player.getName());
	}

	public static void unfreeze(Player player) {
		frozen.remove(player.getName());
	}

	public static boolean isFrozen(Player player) {
		return frozen.contains(player.getName());
	}

}
