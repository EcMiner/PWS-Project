package com.daan.pws.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.*;
import org.bukkit.event.world.*;

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
	}

}
