package com.daan.pws.listeners;

import net.minecraft.server.v1_7_R4.PacketPlayInChat;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.daan.pws.Main;
import com.daan.pws.protocol.PacketReceiveEvent;

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

	@EventHandler
	public void onPacketReceive(PacketReceiveEvent e) {
		if (e.getPacket() instanceof PacketPlayInChat) {
			// new Packet3Chat((PacketPlayInChat) e.getPacket()).setMessage("Hello, I am gay!");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() != null && e.getItem().getType() == Material.BLAZE_ROD) {
			p.getWorld().playEffect(p.getLocation(), Effect.CRIT, 10);
		}
	}

}
