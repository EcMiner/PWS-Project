package com.daan.pws.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;

import com.daan.pws.Main;

public class WorldListener implements Listener {

	private Main plugin;

	public WorldListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onWeatherChangeEvent(WeatherChangeEvent evt) {
		evt.setCancelled(true);
	}

	@EventHandler
	public void onThunderChangeEvent(ThunderChangeEvent evt) {
		evt.setCancelled(true);
	}

	@EventHandler
	public void onPortalCreateEvent(PortalCreateEvent evt) {
		evt.setCancelled(true);
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		if ((event.getBlock().getTypeId() != 2) || (event.getBlock().getTypeId() != 3)) {
			event.setCancelled(true);
		}
	}

}
