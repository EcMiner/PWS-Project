package com.daan.pws;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.daan.pws.commands.LobbyCommand;
import com.daan.pws.commands.MapCommand;
import com.daan.pws.guis.listeners.BuyGUIListener;
import com.daan.pws.listeners.BuyButtonListener;
import com.daan.pws.listeners.LeftClickListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.ReloadListener;
import com.daan.pws.listeners.RightClickListener;
import com.daan.pws.listeners.SelectionListener;
import com.daan.pws.listeners.UseButtonListener;
import com.daan.pws.listeners.WeaponDropListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.match.listeners.BlockListener;
import com.daan.pws.match.listeners.BombListener;
import com.daan.pws.match.listeners.DamageListener;
import com.daan.pws.match.map.CompetitiveMapManager;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.WeaponManager;

public class Main extends JavaPlugin {

	private static Main instance;
	public WeaponManager gunManager;

	@Override
	public void onEnable() {
		instance = this;

		registerListeners();

		this.gunManager = new WeaponManager();
		new CompetitiveMapManager();

		loadCommands();
	}

	@Override
	public void onDisable() {
	}

	public static Main getInstance() {
		return instance;
	}

	public static void registerCommands(CommandExecutor exec, String... commands) {
		for (String command : commands) {
			instance.getCommand(command).setExecutor(exec);
		}
	}

	private void loadCommands() {
		new MapCommand();
		new LobbyCommand();
	}

	private void registerListeners() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BombListener(), this);
		new LeftClickListener();
		new ReloadListener();
		new WeaponDropListener();
		new RightClickListener();
		new BuyGUIListener();
		new BuyButtonListener();
		new UseButtonListener();
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
		pluginManager.registerEvents(new PlayerUtil(), this);
		pluginManager.registerEvents(new SelectionListener(), this);
		pluginManager.registerEvents(new BlockListener(), this);
		pluginManager.registerEvents(new com.daan.pws.match.listeners.PlayerListener(), this);
		pluginManager.registerEvents(new DamageListener(), this);

	}

}
