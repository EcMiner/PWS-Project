package com.daan.pws.match.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Openable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.daan.pws.Main;
import com.daan.pws.match.Competitive;

public class BlockListener implements Listener {

	public BlockListener() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getInstance(), ConnectionSide.SERVER_SIDE, 55) {

			@Override
			public void onPacketSending(PacketEvent event) {
				Player digger = (Player) ProtocolLibrary.getProtocolManager().getEntityFromID(event.getPlayer().getWorld(), event.getPacket().getIntegers().getValues().get(0));
				if (Competitive.isInMatch(digger)) {
					event.setCancelled(true);
				}
			}

		});
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent evt) {
		if (Competitive.isInMatch(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent evt) {
		if (Competitive.isInMatch(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent evt) {
		if (Competitive.isInMatch(evt.getPlayer())) {
			evt.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			if (evt.getEntityType() == EntityType.ITEM_FRAME || evt.getEntityType() == EntityType.PAINTING) {
				if (Competitive.isInMatch((Player) evt.getDamager())) {
					evt.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
		if (evt.getRightClicked().getType() == EntityType.ITEM_FRAME) {
			if (Competitive.isInMatch(evt.getPlayer())) {
				evt.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState().getData() instanceof Openable) {
				if (Competitive.isInMatch(e.getPlayer())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
