package com.daan.pws;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.daan.pws.listeners.BlockListener;
import com.daan.pws.listeners.PlayerListener;
import com.daan.pws.listeners.WorldListener;
import com.daan.pws.protocol.MCProtocol;
import com.daan.pws.protocol.PacketType;
import com.daan.pws.protocol.injection.PlayerInjection;

public class Main extends JavaPlugin {

	private static Main instance;
	public PlayerInjection injection;
	public MCProtocol protocol;

	@Override
	public void onEnable() {
		instance = this;

		PacketType.init();

		registerListeners();

		this.injection = new PlayerInjection(this);
		this.protocol = new MCProtocol();

		/*protocol.addPacketListener(PacketType.Play.Server.NAMED_ENTITY_SPAWN, new PacketListener() {

			@Override
			public void onPacketEvent(PacketEvent event) {
				System.out.println("36:" + event.getPacket().toString());
			}

		});*/
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
