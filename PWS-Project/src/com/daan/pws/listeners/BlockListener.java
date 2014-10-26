package com.daan.pws.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.daan.pws.Main;

public class BlockListener implements Listener {

	private Main plugin;

	public BlockListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent evt) {
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent evt) {
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent evt) {
		if (evt.getEntityType() == EntityType.ITEM_FRAME) {
		} else if (evt.getEntityType() == EntityType.PAINTING) {
		}
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent evt) {
		if (evt.getRightClicked().getType() == EntityType.ITEM_FRAME) {
		}
	}

}
