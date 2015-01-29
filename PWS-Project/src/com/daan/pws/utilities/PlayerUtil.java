package com.daan.pws.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_6_R3.EntityLiving;
import net.minecraft.server.v1_6_R3.MobEffect;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.daan.pws.Main;
import com.daan.pws.listeners.RightClickListener;

public class PlayerUtil implements Listener {

	private static final Map<String, Vector> velocities = new HashMap<String, Vector>();
	private static final Map<String, GenericTexture> zoomTexture = new HashMap<String, GenericTexture>();
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

	public static void zoomIn(SpoutPlayer player, int zoomfactor) {
		CraftPlayer cp = (CraftPlayer) player;
		player.removePotionEffect(PotionEffectType.SLOW);
		cp.getHandle().addEffect(new MobEffect(2, 1278263, zoomfactor));
	}

	public static void zoomIn(SpoutPlayer player, int zoomfactor, String zoomUrl) {
		if (zoomUrl != null) {
			CraftPlayer cp = (CraftPlayer) player;
			cp.getHandle().addEffect(new MobEffect(2, 1278263, zoomfactor));

			GenericTexture texture = new GenericTexture(zoomUrl);
			texture.setAnchor(WidgetAnchor.SCALE).setX(0).setY(0).setPriority(RenderPriority.Low).setHeight(player.getMainScreen().getHeight()).setWidth(player.getMainScreen().getWidth());
			player.getMainScreen().attachWidget(Main.getInstance(), texture);
			zoomTexture.put(player.getName(), texture);
		}
	}

	public static void zoomOut(SpoutPlayer player) {
		if (zoomTexture.containsKey(player.getName())) {
			player.removePotionEffect(PotionEffectType.SLOW);
			player.getMainScreen().removeWidget(zoomTexture.get(player.getName()));
			zoomTexture.remove(player.getName());
		}
		RightClickListener.zoomedInTimes.remove(player.getName());
	}

	public static boolean isZoomedIn(SpoutPlayer player) {
		return zoomTexture.containsKey(player.getName());
	}

	public static void addPotionEffectNoParticles(Player player, int type, int duration, int level) {
		CraftPlayer cp = (CraftPlayer) player;
		cp.getHandle().addEffect(new MobEffect(type, duration, level));

		try {
			Field field = EntityLiving.class.getDeclaredField("effects");
			field.setAccessible(true);

			HashMap<?, ?> effects = (HashMap<?, ?>) field.get(cp.getHandle());
			effects.remove(type);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void removePotionEffectSafe(Player player, int type, PotionEffectType pType) {
		CraftPlayer cp = (CraftPlayer) player;

		player.addPotionEffect(new PotionEffect(pType, 0, 0));

		for (PotionEffect eff : player.getActivePotionEffects()) {
			if (eff.getType() == pType) {
				player.removePotionEffect(eff.getType());
			}
		}
		player.removePotionEffect(pType);

		try {
			Field field = EntityLiving.class.getDeclaredField("effects");
			field.setAccessible(true);

			HashMap<?, ?> effects = (HashMap<?, ?>) field.get(cp.getHandle());
			effects.remove(type);

			cp.getHandle().getDataWatcher().watch(8, (byte) 0);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
