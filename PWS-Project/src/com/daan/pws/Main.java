package com.daan.pws;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.daan.pws.entities.NPCEntity;
import com.daan.pws.entities.StatusZombie;
import com.daan.pws.listeners.BlockListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.protocol.MCProtocol;
import com.daan.pws.protocol.PacketType;
import com.daan.pws.protocol.injection.PlayerInjection;
import com.daan.pws.utilities.EntityUtil;

public class Main extends JavaPlugin {

	private static Main instance;
	public PlayerInjection injection;
	public MCProtocol protocol;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		instance = this;

		EntityUtil.registerEntity("Zombie", 54, StatusZombie.class);
		EntityUtil.registerEntity("Wolf", EntityType.WOLF.getTypeId(), NPCEntity.class);

		PacketType.init();

		registerListeners();

		this.injection = new PlayerInjection(this);
		this.protocol = new MCProtocol();
	}

	@Override
	public void onDisable() {
		injection.disable();
	}

	public static Main getInstance() {
		return instance;
	}

	private void registerListeners() {
		PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(new BlockListener(this), this);
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new WorldListener(this), this);
	}

}
