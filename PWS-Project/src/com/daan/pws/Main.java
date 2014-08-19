package com.daan.pws;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.daan.pws.listeners.*;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		registerListeners();
	}

	@Override
	public void onDisable() {
	}

	private void registerListeners() {
		/*
		 * Hier registreren we de listeners, zodat als er een Event gebeurt, die
		 * hier gehandeld kunnen worden §
		 */
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BlockListener(this), this);
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
	}

}
