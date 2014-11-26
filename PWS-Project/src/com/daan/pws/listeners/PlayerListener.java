package com.daan.pws.listeners;

import net.minecraft.server.v1_6_R3.Packet63WorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import com.daan.pws.Main;
import com.daan.pws.protocol.PacketBuilder;

public class PlayerListener implements Listener {

	private Main plugin;

	public PlayerListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		plugin.injection.injectPlayer(p);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() != null && e.getItem().getType() == Material.BLAZE_ROD) {
		} else if (e.getItem() != null && e.getItem().getType() == Material.MUSHROOM_SOUP) {
			long health = Math.round(((Damageable) p).getHealth());
			Damageable dmg = (Damageable) p;
			if (health < dmg.getMaxHealth()) {
				p.getItemInHand().setType(Material.BOWL);
				p.getItemInHand().setItemMeta(null);
				p.setHealth(health + 7d >= 20d ? 20d : health + 7d);

				p.updateInventory();
			}
		} else if (e.getItem() != null && e.getItem().getType() == Material.STICK) {
			createWhateverItIs(p);
		}
	}

	@SuppressWarnings("deprecation")
	public void createHelix(Player player) {
		Location loc = player.getLocation();
		int radius = 5;
		for (double xx = 0; xx <= 50; xx += 0.05) {
			double y = radius * Math.sin(xx);
			Packet63WorldParticles packet = PacketBuilder.buildPacketPlayOutWorldParticles("fireworksSpark", (float) (loc.getX() + xx), (float) (loc.getY()), (float) (loc.getZ() + y), 0, 0, 0, 0, 1);
			for (Player online : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void createWhateverItIs(Player player) {
		Location loc = player.getLocation();
		for (double i = 0; i < 6.5; i += 0.05) {
			double x = 5 * Math.cos(i);
			double z = 5 * Math.sin(i);
			Vector v = new Vector(loc.getX() + x, loc.getY(), loc.getZ() + z);
			Vector v1 = v.subtract(loc.toVector()).normalize().multiply(0.1);
			for (double j = 0; i < 3.25; i += 0.5) {
				double y = Math.sin(j);
				v.add(new Vector(j == 0 ? 0 : v1.getX(), j == 0 ? 0 : y, j == 0 ? 0 : v1.getZ()));
				Packet63WorldParticles packet = PacketBuilder.buildPacketPlayOutWorldParticles("fireworksSpark", (float) (v.getX()), (float) (v.getY()), (float) (v.getZ()), 0, 0, 0, 0, 1);
				for (Player online : Bukkit.getOnlinePlayers()) {
					((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}
	}

}
