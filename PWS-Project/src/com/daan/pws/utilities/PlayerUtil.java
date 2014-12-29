package com.daan.pws.utilities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

	public static final Vector getVelocity(Player player) {
		if (!player.isOnline() || !velocities.containsKey(player.getName())) {
			return new Vector(0, 0, 0);
		}
		return velocities.get(player.getName());
	}

}
